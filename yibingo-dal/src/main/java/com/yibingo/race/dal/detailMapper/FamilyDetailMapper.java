package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.Family;

import lombok.Data;


import java.util.*;


/**
 * 系列DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class FamilyDetailMapper{


    public static Map<String,Object> buildMap(Family entity){
        Map<String,Object> map = new HashMap<>();
        map.put("familyId",entity.getId());
        map.put("title",entity.getTitle());
        map.put("introduction",entity.getIntroduction());
        map.put("url",entity.getUrl());
        map.put("contractAddress",entity.getContractAddress());
        map.put("contractAgreement",entity.getContractAgreement());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<Family> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Family entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}