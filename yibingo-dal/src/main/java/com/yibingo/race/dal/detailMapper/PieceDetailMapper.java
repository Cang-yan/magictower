package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.Piece;

import lombok.Data;


import java.util.*;


/**
 * 碎片DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class PieceDetailMapper{


    public static Map<String,Object> buildMap(Piece entity){
        Map<String,Object> map = new HashMap<>();
        map.put("pieceId",entity.getId());
        map.put("metaNftId",entity.getMetaNftId());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<Piece> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Piece entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}