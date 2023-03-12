package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.Market;

import lombok.Data;


import java.util.*;


/**
 * 商城DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class MarketDetailMapper{


    public static Map<String,Object> buildMap(Market entity){
        Map<String,Object> map = new HashMap<>();
        map.put("marketId",entity.getId());
        map.put("relationId",entity.getRelationId());
        map.put("price",entity.getPrice());
        map.put("isForSell",entity.getIsForSell());
        map.put("sellTime",entity.getSellTime());
        map.put("familyId",entity.getFamilyId());
        map.put("nftType",entity.getNftType());
        map.put("nowCount",entity.getNowCount());
        map.put("limitPurchase",entity.getLimitPurchase());
        map.put("version",entity.getVersion());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<Market> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Market entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}