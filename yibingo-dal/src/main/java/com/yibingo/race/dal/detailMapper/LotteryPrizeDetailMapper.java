package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.LotteryPrize;

import lombok.Data;


import java.util.*;


/**
 * 抽奖奖品;DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:38
 */
@Data
public class LotteryPrizeDetailMapper{


    public static Map<String,Object> buildMap(LotteryPrize entity){
        Map<String,Object> map = new HashMap<>();
        map.put("lotteryPrizeId",entity.getId());
        map.put("lotteryId",entity.getLotteryId());
        map.put("text",entity.getText());
        map.put("grade",entity.getGrade());
        map.put("possibility",entity.getPossibility());
        map.put("stockCount",entity.getStockCount());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<LotteryPrize> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (LotteryPrize entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}