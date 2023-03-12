package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.BoxUncoverRecords;

import lombok.Data;


import java.util.*;


/**
 * 盲盒抽取记录DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BoxUncoverRecordsDetailMapper{


    public static Map<String,Object> buildMap(BoxUncoverRecords entity){
        Map<String,Object> map = new HashMap<>();
        map.put("boxUncoverRecordsId",entity.getId());
        map.put("blindBoxId",entity.getBlindBoxId());
        map.put("metaNftId",entity.getMetaNftId());
        map.put("count",entity.getCount());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<BoxUncoverRecords> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (BoxUncoverRecords entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}