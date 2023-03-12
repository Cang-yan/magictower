package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.LotteryRecords;

import lombok.Data;


import java.util.*;


/**
 * 抽奖记录;DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:38
 */
@Data
public class LotteryRecordsDetailMapper{


    public static Map<String,Object> buildMap(LotteryRecords entity){
        Map<String,Object> map = new HashMap<>();
        map.put("lotteryRecordsId",entity.getId());
        map.put("lotteryId",entity.getLotteryId());
        map.put("userId",entity.getUserId());
        map.put("redeemCodeId",entity.getRedeemCodeId());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<LotteryRecords> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (LotteryRecords entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}