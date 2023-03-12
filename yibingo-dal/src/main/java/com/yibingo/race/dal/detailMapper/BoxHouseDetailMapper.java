package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.BoxHouse;

import lombok.Data;


import java.util.*;


/**
 * 盲盒仓库DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BoxHouseDetailMapper{


    public static Map<String,Object> buildMap(BoxHouse entity){
        Map<String,Object> map = new HashMap<>();
        map.put("boxHouseId",entity.getId());
        map.put("blindBoxId",entity.getBlindBoxId());
        map.put("count",entity.getCount());
        map.put("userId",entity.getUserId());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<BoxHouse> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (BoxHouse entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}