package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.Prop;

import lombok.Data;


import java.util.*;


/**
 * 道具DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class PropDetailMapper{


    public static Map<String,Object> buildMap(Prop entity){
        Map<String,Object> map = new HashMap<>();
        map.put("propId",entity.getId());
        map.put("url",entity.getUrl());
        map.put("name",entity.getName());
        map.put("propType",entity.getType());
        map.put("introduce",entity.getIntroduce());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<Prop> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Prop entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}