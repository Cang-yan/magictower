package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.MetaNft;

import lombok.Data;


import java.util.*;


/**
 * 藏品DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class MetaNftDetailMapper{


    public static Map<String,Object> buildMap(MetaNft entity){
        Map<String,Object> map = new HashMap<>();
        map.put("metaNftId",entity.getId());
        map.put("url",entity.getUrl());
        map.put("nickname",entity.getNickname());
        map.put("introduce",entity.getIntroduce());
        map.put("contractAddress",entity.getContractAddress());
        map.put("contractAgreement",entity.getContractAgreement());
        map.put("metaNftType",entity.getType());
        map.put("professionType",entity.getProfessionType());
        map.put("familyId",entity.getFamilyId());
        map.put("familyName",entity.getFamilyName());
        map.put("rankId",entity.getRankId());
        map.put("authorId",entity.getAuthorId());
        map.put("stockId",entity.getStockId());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<MetaNft> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (MetaNft entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}