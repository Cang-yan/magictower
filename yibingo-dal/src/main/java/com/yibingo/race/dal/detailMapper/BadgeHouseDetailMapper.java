package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.BadgeHouse;

import lombok.Data;


import java.util.*;


/**
 * 徽章仓库DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BadgeHouseDetailMapper{


    public static Map<String,Object> buildMap(BadgeHouse entity){
        Map<String,Object> map = new HashMap<>();
        map.put("badgeHouseId",entity.getId());
        map.put("metaNftId",entity.getMetaNftId());
        map.put("userId",entity.getUserId());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<BadgeHouse> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (BadgeHouse entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}