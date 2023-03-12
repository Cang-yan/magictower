package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.ControlHouse;

import lombok.Data;


import java.util.*;


/**
 * 管理员仓库DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class ControlHouseDetailMapper{


    public static Map<String,Object> buildMap(ControlHouse entity){
        Map<String,Object> map = new HashMap<>();
        map.put("controlHouseId",entity.getId());
        map.put("wareHouseId",entity.getWareHouseId());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<ControlHouse> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ControlHouse entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}