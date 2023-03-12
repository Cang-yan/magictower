package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.Lottery;

import lombok.Data;


import java.util.*;


/**
 * 抽奖;DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:38
 */
@Data
public class LotteryDetailMapper{


    public static Map<String,Object> buildMap(Lottery entity){
        Map<String,Object> map = new HashMap<>();
        map.put("lotteryId",entity.getId());
        map.put("title",entity.getTitle());
        map.put("limitRule",entity.getLimitRule());
        map.put("coreNum",entity.getCoreNum());
        map.put("isRunned",entity.getIsRunned());
        map.put("runTime",entity.getRunTime());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<Lottery> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Lottery entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}