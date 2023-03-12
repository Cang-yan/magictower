package com.yibingo.race.core.service;

import com.yibingo.race.common.exception.BaseException;
import com.yibingo.race.core.service.base.BuyOrderBaseService;
import com.yibingo.race.core.service.base.BuyOrderFilterService;
import com.yibingo.race.core.service.base.MarketBaseService;
import com.yibingo.race.dal.dao.MarketDao;
import com.yibingo.race.dal.entity.BuyOrder;
import com.yibingo.race.dal.entity.Market;
import com.yibingo.race.dal.filterMapper.BuyOrderFilterMapper;
import com.yibingo.race.dal.putMapper.MetaNftPutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 买买买和交易有关的
 * @author: Yang Xin
 * @time: 2022/6/28 15:42
 */
@Service
public class MarketAndShopService {

    //乐观锁冲突最大重试次数
    private static final int DEFAULT_MAX_RETRIES = 5;

    @Autowired
    private MarketBaseService marketBaseService;

    @Resource
    private MarketDao marketDao;

    @Autowired
    private BuyOrderBaseService buyOrderBaseService;

    //查询当前人对当前商品的购买能力  库存和限购取小值
    public Integer getAllowPurchase(String userId, String marketId) {
        Integer allowPurchase = 0;
        Market market = marketBaseService.getById(marketId);
        //一开始是库存
        allowPurchase = market.getNowCount();
        if (allowPurchase<=0) return 0;

        //判断限购数量
        BuyOrderFilterMapper buyOrderFilterMapper = new BuyOrderFilterMapper();
        buyOrderFilterMapper.userId = userId;
        buyOrderFilterMapper.marketId = market.getId();
        List<BuyOrder> buyOrderList = BuyOrderFilterService.getListByFilter(buyOrderBaseService, buyOrderFilterMapper);
        if (!buyOrderList.isEmpty()) {
            //已经买了的多少个
            Integer purchaseCount = 0;
            for (BuyOrder buyOrder : buyOrderList) {
                purchaseCount = purchaseCount + buyOrder.getCount();
            }
            //当剩余购买力小于库存的时候，就是剩余购买力了
            if (market.getLimitPurchase()-purchaseCount<allowPurchase){
                allowPurchase = market.getLimitPurchase()-purchaseCount;
            }
        }
        //当<=0就是没购买力了，否则就是这个数
        return allowPurchase;
    }


    //减库存
    public boolean decreaseStack(Market market, Integer count) {
        //重试次数
        int numAttempts = 0;
        int ret = 0;

        do {
            numAttempts++;
            try {
                //获取最新版本号，乐观锁对查不加锁，对修改加锁
                market.setVersion(marketBaseService.getById(market.getId()).getVersion());
                ret = marketDao.reduceStockByVersion(count,market.getVersion(),market.getId());
            } catch (Exception e) {
                e.printStackTrace();
                throw new BaseException(e.toString());
            }
            //当ret不为0意味着成功执行减库存操作
            if (ret != 0)
                break;
        } while (numAttempts < DEFAULT_MAX_RETRIES);

        return ret > 0;

    }


    //TODO 普通购买盲盒  删除库存  生成订单   写进盲盒购买记录





    //TODO 使用道具

    //上架商城


    // 选择藏品上架
    public void metaNftOnShop(MetaNftPutMapper metaNftPutMapper){
        ;
    }

    //盲盒上架

    //徽章上架
}
