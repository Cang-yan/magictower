package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.LotteryJoin;

import lombok.Data;


import java.util.*;


/**
 * 抽奖参与人员;DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:38
 */
@Data
public class LotteryJoinDetailMapper{


    public static Map<String,Object> buildMap(LotteryJoin entity){
        Map<String,Object> map = new HashMap<>();
        map.put("lotteryJoinId",entity.getId());
        map.put("lotteryId",entity.getLotteryId());
        map.put("userId",entity.getUserId());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<LotteryJoin> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (LotteryJoin entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}