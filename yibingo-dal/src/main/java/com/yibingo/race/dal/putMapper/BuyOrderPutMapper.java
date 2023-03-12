package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.BuyOrder;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 购买订单PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BuyOrderPutMapper{

    private Double price;
    private String marketId;
    private Integer count;
    private Integer status;
    private String userId;

    public static BuyOrder convertToEntity(BuyOrderPutMapper putMapper){
        BuyOrder entity = new BuyOrder();
        entity.setPrice(putMapper.getPrice());
        entity.setMarketId(putMapper.getMarketId());
        entity.setCount(putMapper.getCount());
        entity.setStatus(putMapper.getStatus());
        entity.setUserId(putMapper.getUserId());
        return entity;
    }



}