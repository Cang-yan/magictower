package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.BoxMeta;

import lombok.Data;


import java.util.*;


/**
 * 盲盒藏品绑定关系DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BoxMetaDetailMapper{


    public static Map<String,Object> buildMap(BoxMeta entity){
        Map<String,Object> map = new HashMap<>();
        map.put("boxMetaId",entity.getId());
        map.put("blindBoxId",entity.getBlindBoxId());
        map.put("metaNftId",entity.getMetaNftId());
        map.put("rankName",entity.getRankName());
        map.put("metaNftPrice",entity.getMetaNftPrice());
        map.put("rankPossibility",entity.getRankPossibility());
        map.put("realPossibility",entity.getRealPossibility());
        map.put("stockId",entity.getStockId());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<BoxMeta> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (BoxMeta entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}