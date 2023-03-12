package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.PieceComRule;

import lombok.Data;


import java.util.*;


/**
 * 碎片合成规则DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class PieceComRuleDetailMapper{


    public static Map<String,Object> buildMap(PieceComRule entity){
        Map<String,Object> map = new HashMap<>();
        map.put("pieceComRuleId",entity.getId());
        map.put("pieceId",entity.getPieceId());
        map.put("countNeed",entity.getCountNeed());
        map.put("metaNftId",entity.getMetaNftId());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<PieceComRule> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (PieceComRule entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}