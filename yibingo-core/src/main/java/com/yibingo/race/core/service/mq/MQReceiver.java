package com.yibingo.race.core.service.mq;


import com.yibingo.race.common.utils.RedisUtils;
import com.yibingo.race.core.service.FlashSaleService;
import com.yibingo.race.core.service.MarketAndShopService;
import com.yibingo.race.core.service.base.BuyOrderBaseService;
import com.yibingo.race.core.service.base.MarketBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangyunxiong on 2018/5/29.
 */
@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);


    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MarketBaseService marketBaseService;

    @Autowired
    private BuyOrderBaseService buyOrderBaseService;

    @Autowired
    private FlashSaleService flashSaleService;

    @Autowired
    private MarketAndShopService marketAndShopService;

   /* //优先购
    @RabbitListener(queues = MQConfig.PRIOR_SECKILL_QUEUE)
    public void receivePrior(String message) {
        log.info("receive message:" + message);
        SeckillMessage m = RedisUtils.stringToBean(message, SeckillMessage.class);
        String userId = m.getUserId();
        String goodsId = m.getGoodsId();
        //允许购买的数量,取库存，限购数量，想买的数量 三者的最小值
        //想买的数量
        Integer purchaseNum = m.getPurchaseNum();
        //库存和限购的取小值
        Integer remainPurchase = marketAndShopService.getAllowPurchase(userId, goodsId);
        //允许购买的数量
        Integer allowPurchase = purchaseNum <= remainPurchase ? m.getPurchaseNum() : remainPurchase;

        if (allowPurchase <=0) return;


        //成功购买的操作
        //减库存 下订单 写入秒杀订单

        flashSaleService.flashSell(userId, goodsId, allowPurchase);
    }*/

    //普通购买
    @RabbitListener(queues = MQConfig.SECKILL_QUEUE)
    public void receive(String message) {
        log.info("receive message:" + message);
        SeckillMessage m = RedisUtils.stringToBean(message, SeckillMessage.class);
        String userId = m.getUserId();
        String goodsId = m.getGoodsId();
        String buyOrderId = m.getBuyOrderId();

        //想买的数量
        Integer purchaseNum = m.getPurchaseNum();
        //查数据库目前的库存
        Integer remainPurchase = marketBaseService.getById(goodsId).getNowCount();

        Integer allowPurchase = purchaseNum <= remainPurchase ? m.getPurchaseNum() : remainPurchase;

        if (allowPurchase <= 0) return;
        //成功购买的操作
        //减库存 下订单 写入秒杀订单
        flashSaleService.flashSell(userId, goodsId, buyOrderId, allowPurchase);

    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info(" topic  queue1 message:" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" topic  queue2 message:" + message);
    }
}
