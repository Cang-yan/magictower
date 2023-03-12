package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.PieceHouse;

import lombok.Data;


import java.util.*;


/**
 * 碎片仓库DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:38
 */
@Data
public class PieceHouseDetailMapper{


    public static Map<String,Object> buildMap(PieceHouse entity){
        Map<String,Object> map = new HashMap<>();
        map.put("pieceHouseId",entity.getId());
        map.put("pieceId",entity.getPieceId());
        map.put("count",entity.getCount());
        map.put("userId",entity.getUserId());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<PieceHouse> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (PieceHouse entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}