package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.InvatitionRank;

import lombok.Data;


import java.util.*;


/**
 * 邀新排行榜DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class InvatitionRankDetailMapper{


    public static Map<String,Object> buildMap(InvatitionRank entity){
        Map<String,Object> map = new HashMap<>();
        map.put("invatitionRankId",entity.getId());
        map.put("userId",entity.getUserId());
        map.put("count",entity.getCount());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<InvatitionRank> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (InvatitionRank entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}