package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.BuyOrder;

import lombok.Data;


import java.util.*;


/**
 * 购买订单DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BuyOrderDetailMapper{


    public static Map<String,Object> buildMap(BuyOrder entity){
        Map<String,Object> map = new HashMap<>();
        map.put("buyOrderId",entity.getId());
        map.put("price",entity.getPrice());
        map.put("marketId",entity.getMarketId());
        map.put("count",entity.getCount());
        map.put("buyOrderStatus",entity.getStatus());
        map.put("userId",entity.getUserId());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<BuyOrder> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (BuyOrder entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}