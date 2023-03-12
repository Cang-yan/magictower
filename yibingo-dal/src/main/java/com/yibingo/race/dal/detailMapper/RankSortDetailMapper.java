package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.RankSort;

import lombok.Data;


import java.util.*;


/**
 * 品质分级,默认概率写进枚举DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class RankSortDetailMapper{


    public static Map<String,Object> buildMap(RankSort entity){
        Map<String,Object> map = new HashMap<>();
        map.put("rankSortId",entity.getId());
        map.put("name",entity.getName());
        map.put("qualityCode",entity.getQualityCode());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<RankSort> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (RankSort entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}