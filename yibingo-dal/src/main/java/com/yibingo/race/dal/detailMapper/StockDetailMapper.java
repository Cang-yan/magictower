package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.Stock;

import lombok.Data;


import java.util.*;


/**
 * 库存DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class StockDetailMapper{


    public static Map<String,Object> buildMap(Stock entity){
        Map<String,Object> map = new HashMap<>();
        map.put("stockId",entity.getId());
        map.put("preCount",entity.getPreCount());
        map.put("nowCount",entity.getNowCount());
        map.put("reserveCount",entity.getReserveCount());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<Stock> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Stock entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}