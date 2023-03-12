package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.ExchangeRank;

import lombok.Data;


import java.util.*;


/**
 * 交易排行榜DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class ExchangeRankDetailMapper{


    public static Map<String,Object> buildMap(ExchangeRank entity){
        Map<String,Object> map = new HashMap<>();
        map.put("exchangeRankId",entity.getId());
        map.put("userId",entity.getUserId());
        map.put("totalMoney",entity.getTotalMoney());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<ExchangeRank> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ExchangeRank entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}