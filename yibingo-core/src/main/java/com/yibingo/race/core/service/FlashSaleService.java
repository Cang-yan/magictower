package com.yibingo.race.core.service;

import cn.hutool.core.util.IdUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.yibingo.race.common.exception.BaseException;
import com.yibingo.race.common.utils.RedisUtils;
import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.base.*;
import com.yibingo.race.core.service.mq.MQSender;
import com.yibingo.race.core.service.mq.SeckillMessage;
import com.yibingo.race.dal.detailMapper.BuyOrderDetailMapper;
import com.yibingo.race.dal.entity.BenefitBadge;
import com.yibingo.race.dal.entity.BuyOrder;
import com.yibingo.race.dal.entity.Market;
import com.yibingo.race.dal.entity.WareHouse;
import com.yibingo.race.dal.enums.BuyOrderStatusEnum;
import com.yibingo.race.dal.enums.RedisPrefix;
import com.yibingo.race.dal.filterMapper.BenefitBadgeFilterMapper;
import com.yibingo.race.dal.filterMapper.MarketFilterMapper;
import com.yibingo.race.dal.filterMapper.WareHouseFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @description: !!!!!秒杀
 * @author: Yang Xin
 * @time: 2022/6/28 16:05
 */
@Service
public class FlashSaleService {

    @Autowired
    private MarketAndShopService marketAndShopService;

    @Autowired
    private BenefitBadgeBaseService benefitBadgeBaseService;

    @Autowired
    private BuyOrderBaseService buyOrderBaseService;

    @Autowired
    private BuyOrderService buyOrderService;

    @Autowired
    private WareHouseBaseService wareHouseBaseService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MarketBaseService marketBaseServicel;

    @Autowired
    private MQSender sender;

    //基于令牌桶算法的限流实现类
    RateLimiter rateLimiter = RateLimiter.create(100);

    //做标记，判断该商品是否被处理过了
    private HashMap<String, Boolean> localOverMap = new HashMap<>();

    /**
     * 取某人的限购数量
     *
     * @param userId
     * @return
     */
    public Map<String, Object> getLimitPurchaseNum(String userId, String marketId) {
        Integer limitPurchase = Integer.parseInt(redisUtils.get(RedisPrefix.BenefitKey_getUserBenefit.getPrefix() + "-" + userId).toString());

        long nowStock = (long) redisUtils.get(RedisPrefix.GoodsKey_getGoodsStock.getPrefix() + "-" + marketId);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("limitPurchase" , limitPurchase);
        resultMap.put("nowStock" , nowStock);

        return resultMap;

    }

    //手动修改添加某人的限购数量，给这些人增加指定的数量
    public void changeLimitPurchaseNum(List<String> userIdList, Integer addNum) {
        for (String userId : userIdList) {
            redisUtils.incr(RedisPrefix.BenefitKey_getUserBenefit.getPrefix() + "-" + userId, addNum);
        }
    }

    //TODO 定时任务系列：商品到时设定开售   提前把用户优先购数量存到redis里  对已经付款的订单发放商品




    /*
     * 流程
     * 秒杀写订单（未支付状态）  -》 订单，也就是秒杀结果，分为几个状态可查询，排队中，成功 失败  已支付 超时未支付（锁单） 已发放-》 成功就是订单支付页面
     * -》 定时查询查询订单状态 已支付的发放奖品
     *
     * */

    /**
     * 减掉优先购次数
     *
     * @param userId
     * @param marketId
     * @param puchaseNum
     * @param limitPurchase
     */
    //提前选好数量，默认是1， 把每人限购传进来
    public String secendPriorKill(String userId, String marketId, Integer puchaseNum, Integer limitPurchase) {

        if (puchaseNum > limitPurchase) {
            throw new BaseException("您的限购份数已用尽" , 10083);
        }

        //进行限流
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            throw new BaseException("访问高峰期，请稍等！" , 50104);
        }

        //手动初始化
        if(localOverMap.isEmpty()) afterPropertiesSet();
        //内存标记，减少redis访问
        //如果该商品变成true了，则已经被处理过卖完了
        if (localOverMap.get(marketId)) {
            throw new BaseException("商品已抢光，请下次再来" , 10085);
        }

        //预减库存
        Integer allowPurchase = preDecKillMarketStock(marketId, puchaseNum);

        //减掉该人的优先购份数
        redisUtils.decr(RedisPrefix.BenefitKey_getUserBenefit.getPrefix() + "-" + userId, allowPurchase);

        //生成一系列buyOrderId
        String buyOrderId = IdUtil.simpleUUID();


        //入队
        SeckillMessage message = new SeckillMessage();
        message.setUserId(userId);
        message.setGoodsId(marketId);
        message.setPurchaseNum(allowPurchase);
        message.setBuyOrderId(buyOrderId);
        sender.sendSeckillMessage(message);

        return buyOrderId;
    }

    /**
     * 前三级缓存的
     * 普通购买
     *
     * @param userId
     * @param marketId
     * @param purchaseNum
     * @return
     */
    public String secondKill(String userId, String marketId, Integer purchaseNum) {

        //进行限流
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            throw new BaseException("访问高峰期，请稍等！" , 50104);
        }

        //手动初始化
        if(localOverMap.isEmpty()) afterPropertiesSet();
        //内存标记，减少redis访问
        //如果该商品变成true了，则已经被处理过卖完了
        if (localOverMap.isEmpty()||localOverMap.get(marketId)) {
            throw new BaseException("商品已抢光，请下次再来" , 10085);
        }

        //预减库存
        Integer allowPurchase = preDecKillMarketStock(marketId, purchaseNum);

        //todo 生成一个订单id，随着message传入 message处理中的id要指定为这个 同时返回给前端
        String buyOrderId = IdUtil.simpleUUID();
        BuyOrder buyOrder = new BuyOrder();
        buyOrder.setId(buyOrderId);
        buyOrder.setStatus(BuyOrderStatusEnum.InLine.getKey());
        buyOrder.setMarketId(marketId);
        buyOrder.setUserId(userId);
        buyOrder.setCount(allowPurchase);
        buyOrderBaseService.save(buyOrder);

        //入队
        SeckillMessage message = new SeckillMessage();
        message.setUserId(userId);
        message.setGoodsId(marketId);
        message.setPurchaseNum(allowPurchase);
        message.setBuyOrderId(buyOrderId);
        sender.sendSeckillMessage(message);

        return buyOrderId;
    }

    /**
     * 系统初始化,将商品信息加载到redis和本地内存
     */
    public void afterPropertiesSet() {
        //把要开售的商品加载到redis
        MarketFilterMapper marketFilterMapper = new MarketFilterMapper();
        marketFilterMapper.isForSell = 1;
        List<Market> marketList = MarketFilterService.getListByFilter(marketBaseServicel, marketFilterMapper);
        if (marketList.isEmpty()) {
            return;
        }
        marketList= marketList.stream().filter(item -> item.getNowCount()!=0).collect(Collectors.toList());
        for (Market market : marketList) {
            redisUtils.set(RedisPrefix.GoodsKey_getGoodsStock.getPrefix() + "-" + market.getId(), market.getNowCount(), RedisPrefix.GoodsKey_getGoodsStock.getExpireSeconds());
            //初始化商品都是没有处理过的
            localOverMap.put(market.getId(), false);
        }
    }

    /**
     * 更新用户权益表，并要存在redis里
     * <p>
     * 这里的数据设定时间
     *
     * @param familyId
     */
    //用户权益表每当秒杀前n小时更新，也就是说，更新完以后的权益变化是没有用的
    public void afterBenefitUserSet(String familyId) {
        //拿出权益全局生效的徽章id
        BenefitBadgeFilterMapper benefitBadgeFilterMapper = new BenefitBadgeFilterMapper();
        benefitBadgeFilterMapper.isAll = 1;
        List<BenefitBadge> benefitAllBadgeList = BenefitBadgeFilterService.getListByFilter(benefitBadgeBaseService, benefitBadgeFilterMapper);
        List<String> benefitBadgeIdList = benefitAllBadgeList.stream().map(BenefitBadge::getBadgeNftId).collect(Collectors.toList());
        //拿出权益部分生效的徽章id
        BenefitBadgeFilterMapper benefitBadgeFilterMapper1 = new BenefitBadgeFilterMapper();
        benefitBadgeFilterMapper1.isAll = 0;
        benefitBadgeFilterMapper1.familyId = familyId;
        List<BenefitBadge> benefitSomeBadgeList = BenefitBadgeFilterService.getListByFilter(benefitBadgeBaseService, benefitBadgeFilterMapper1);
        benefitBadgeIdList.addAll(
                benefitSomeBadgeList.stream().map(BenefitBadge::getBadgeNftId).collect(Collectors.toList())
        );
        //取拥有这些徽章的用户
        for (BenefitBadge benefitBadge : benefitAllBadgeList) {
            WareHouseFilterMapper wareHouseFilterMapper = new WareHouseFilterMapper();
            wareHouseFilterMapper.metaNftId = benefitBadge.getBadgeNftId();
            List<WareHouse> wareHouseList = WareHouseFilterService.getListByFilter(wareHouseBaseService, wareHouseFilterMapper);
            for (WareHouse wareHouse : wareHouseList) {
                //TODO 检查过期时间
                if (redisUtils.hasKey(RedisPrefix.BenefitKey_getUserBenefit + "-" + wareHouse.getUserId())) {
                    redisUtils.incr(RedisPrefix.BenefitKey_getUserBenefit + "-" + wareHouse.getUserId(), benefitBadge.getBenefitPrebuyCount());
                } else {
                    redisUtils.set(RedisPrefix.BenefitKey_getUserBenefit + "-" + wareHouse.getUserId(), benefitBadge.getBenefitPrebuyCount(), RedisPrefix.BenefitKey_getUserBenefit.getExpireSeconds());
                }
            }

        }

        //TODO 内测单独优先购到底是啥情况

    }




    /**
     * 秒杀流程
     * 内存-》redis->消息队列->数据库
     * <p>
     * 购买
     * 这个是消息队列 调 数据库的 秒杀 方法
     *
     * @param userId
     * @param marketId
     * @param buyOrderId
     * @param count
     * @return
     */
    //保证这三个操作，减库存 下订单 写入秒杀订单是一个事务  普通购买
    @Transactional
    public BuyOrder flashSell(String userId, String marketId, String buyOrderId, Integer count) {
        Market market = marketBaseServicel.getById(marketId);
        //减库存
        BuyOrder buyOrder = new BuyOrder();
        buyOrder.setCount(count);
        //buyOrder.setMarketId(market.getId());
        buyOrder.setPrice(market.getPrice() * count);
        //buyOrder.setUserId(userId);
        buyOrder.setId(buyOrderId);


        boolean success = marketAndShopService.decreaseStack(market, count);
        if (success) {
            //下订单
            //指定 成功未支付
            buyOrder.setStatus(BuyOrderStatusEnum.WaitPay.getKey());
            buyOrderBaseService.saveOrUpdate(buyOrder);

        } else {

            //失败，返还优先购次数
            redisUtils.incr(RedisPrefix.BenefitKey_getUserBenefit.getPrefix() + "-" + userId, count);
            //没成功意味着抢完了
            setGoodsOver(market.getId());
            buyOrder.setStatus(BuyOrderStatusEnum.Failed.getKey());
            buyOrderBaseService.saveOrUpdate(buyOrder);

        }
        return buyOrder;
    }

    /**
     * 查询秒杀结果
     * 入队等待处理，成功，失败 三种状态
     *
     * @param buyOrderId
     * @return
     */
    public Map<String, Object> getSeckillResult(String buyOrderId) {
        Map<String, Object> map = new HashMap<>();

        BuyOrder buyOrder = buyOrderBaseService.getById(buyOrderId);

        //数据库查不到说明没有入队
        if (buyOrder == null||buyOrder.getStatus().equals(BuyOrderStatusEnum.InLine.getKey())) {
            map.put("buyOrderId" , buyOrderId);
            return Result.restResult(map, 10084, "正在排队中，请稍后" ).map();

        }

        if (buyOrder.getStatus().equals(BuyOrderStatusEnum.Failed.getKey())) {
            throw new BaseException("商品已抢光，请下次再来" , 10085);
        }

        //把数据库查到的玩意返回出去
        map = BuyOrderDetailMapper.buildMap(buyOrder);
        return Result.success(map).map();

//        BuyOrderFilterMapper buyOrderFilterMapper = new BuyOrderFilterMapper();
//        buyOrderFilterMapper.userId = userId;
//        buyOrderFilterMapper.marketId = marketId;
//        List<BuyOrder> buyOrderList = BuyOrderFilterService.getListByFilter(buyOrderBaseService, buyOrderFilterMapper);

        //TODO 支付成功回调接口，发放藏品，更新购买订单，购买记录要出现


    }

    //todo 预见库存有问题

    /**
     * 预减库存
     * 当并发量过高时，两个用户请求时间差不超过半秒，
     * 假如还剩4个，A要5个  B要2个
     * 则A秒杀失败，B得2个
     *
     * @param marketId
     * @param purchaseNum
     * @return
     */
    private Integer preDecKillMarketStock(String marketId, Integer purchaseNum) {
        Integer allowPurchase = purchaseNum;
        //预减库存
        long firstReduceStock = redisUtils.decr(RedisPrefix.GoodsKey_getGoodsStock.getPrefix() + "-" + marketId, purchaseNum);//10
        if (firstReduceStock <= 0) {
            //去数据库中核对新库存
            afterPropertiesSet();

            long secondReduceStock = redisUtils.decr(RedisPrefix.GoodsKey_getGoodsStock.getPrefix() + "-" + marketId, purchaseNum);

            if (secondReduceStock < 0) {
                localOverMap.put(marketId, true);
                throw new BaseException("商品已抢光，请下次再来" , 10085);

            }
          /*  long nowStock = (long) redisUtils.get(RedisPrefix.GoodsKey_getGoodsStock.getPrefix() + "-" + marketId);
            if (nowStock <= 0) {
                localOverMap.put(marketId, true);
                throw new BaseException("商品已抢光，请下次再来" , 10085);
            }
            //到这儿其实只能说nowStock的库存必定少
            long secondReduceStock = redisUtils.decr(RedisPrefix.GoodsKey_getGoodsStock.getPrefix() + "-" + marketId, purchaseNum);
            //这里判断有问题
            if (secondReduceStock <= 0) {
                //意味着库存数量比要购买的数量少
                int nowStockInt = Math.toIntExact(nowStock);
                allowPurchase = nowStockInt;
                localOverMap.put(marketId, true);

            }*/
        }
        return allowPurchase;
    }


    private void setGoodsOver(String marketId) {
        redisUtils.set(RedisPrefix.SeckillKey_isGoodsOver.getPrefix() + "-" + marketId, true, RedisPrefix.SeckillKey_isGoodsOver.getExpireSeconds());
    }

    private boolean getGoodsOver(String marketId) {

        return redisUtils.hasKey(RedisPrefix.SeckillKey_isGoodsOver.getPrefix() + "-" + marketId);
    }


}
