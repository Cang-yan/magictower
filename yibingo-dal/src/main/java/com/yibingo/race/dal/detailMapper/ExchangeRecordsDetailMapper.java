package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.ExchangeRecords;

import lombok.Data;


import java.util.*;


/**
 * 藏品交易记录DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class ExchangeRecordsDetailMapper{


    public static Map<String,Object> buildMap(ExchangeRecords entity){
        Map<String,Object> map = new HashMap<>();
        map.put("exchangeRecordsId",entity.getId());
        map.put("metaNftId",entity.getMetaNftId());
        map.put("uuTag",entity.getUuTag());
        map.put("hostUserId",entity.getHostUserId());
        map.put("guestUserId",entity.getGuestUserId());
        map.put("price",entity.getPrice());
        map.put("exchangeRecordsType",entity.getType());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<ExchangeRecords> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ExchangeRecords entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}