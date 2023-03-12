package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.PropHourse;

import lombok.Data;


import java.util.*;


/**
 * 道具仓库DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class PropHourseDetailMapper{


    public static Map<String,Object> buildMap(PropHourse entity){
        Map<String,Object> map = new HashMap<>();
        map.put("propHourseId",entity.getId());
        map.put("propId",entity.getPropId());
        map.put("userId",entity.getUserId());
        map.put("count",entity.getCount());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<PropHourse> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (PropHourse entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}