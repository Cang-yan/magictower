package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.PieceComRecords;

import lombok.Data;


import java.util.*;


/**
 * 合成记录DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class PieceComRecordsDetailMapper{


    public static Map<String,Object> buildMap(PieceComRecords entity){
        Map<String,Object> map = new HashMap<>();
        map.put("pieceComRecordsId",entity.getId());
        map.put("pieceRuleId",entity.getPieceRuleId());
        map.put("userId",entity.getUserId());
        map.put("metaNftId",entity.getMetaNftId());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<PieceComRecords> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (PieceComRecords entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}