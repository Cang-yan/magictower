package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.ExchangeBoxRecords;

import lombok.Data;


import java.util.*;


/**
 * 盲盒交易记录DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class ExchangeBoxRecordsDetailMapper{


    public static Map<String,Object> buildMap(ExchangeBoxRecords entity){
        Map<String,Object> map = new HashMap<>();
        map.put("exchangeBoxRecordsId",entity.getId());
        map.put("blindBoxId",entity.getBlindBoxId());
        map.put("hostUserId",entity.getHostUserId());
        map.put("guestUserId",entity.getGuestUserId());
        map.put("price",entity.getPrice());
        map.put("exchangeBoxRecordsType",entity.getType());
        map.put("count",entity.getCount());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<ExchangeBoxRecords> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ExchangeBoxRecords entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}