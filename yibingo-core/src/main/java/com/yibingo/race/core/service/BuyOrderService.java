package com.yibingo.race.core.service;

import com.yibingo.race.common.utils.RedisUtils;
import com.yibingo.race.core.service.base.BuyOrderBaseService;
import com.yibingo.race.dal.enums.RedisPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 订单服务
 * @author: Yang Xin
 * @time: 2022/7/7 22:09
 */
@Service
public class BuyOrderService {

    @Autowired
    private BuyOrderBaseService buyOrderBaseService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 在redis中取出该人已经购买的数量
     * @param userId
     * @param marketId
     * @return
     */
    public long getAleadyPurchaseByUserIdGoodsId(String userId, String marketId) {
        return (long) redisUtils.get(RedisPrefix.OrderKey_getAleadyPurchaseByUidGid.getPrefix() + "-" + userId + "-" + marketId);
    }

}
