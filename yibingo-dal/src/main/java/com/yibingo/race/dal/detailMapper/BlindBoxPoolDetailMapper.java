package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.BlindBoxPool;

import lombok.Data;


import java.util.*;


/**
 * 盲盒抽奖池子DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BlindBoxPoolDetailMapper{


    public static Map<String,Object> buildMap(BlindBoxPool entity){
        Map<String,Object> map = new HashMap<>();
        map.put("blindBoxPoolId",entity.getId());
        map.put("url",entity.getUrl());
        map.put("nickname",entity.getNickname());
        map.put("introduce",entity.getIntroduce());
        map.put("contractAddress",entity.getContractAddress());
        map.put("contractAgreement",entity.getContractAgreement());
        map.put("familyId",entity.getFamilyId());
        map.put("familyName",entity.getFamilyName());
        map.put("authorId",entity.getAuthorId());
        map.put("stockId",entity.getStockId());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<BlindBoxPool> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (BlindBoxPool entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}