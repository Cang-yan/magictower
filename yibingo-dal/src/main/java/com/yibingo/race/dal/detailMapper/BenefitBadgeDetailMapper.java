package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.BenefitBadge;

import lombok.Data;


import java.util.*;


/**
 * 徽章权益表DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BenefitBadgeDetailMapper{


    public static Map<String,Object> buildMap(BenefitBadge entity){
        Map<String,Object> map = new HashMap<>();
        map.put("benefitBadgeId",entity.getId());
        map.put("badgeNftId",entity.getBadgeNftId());
        map.put("benefitPrebuyCount",entity.getBenefitPrebuyCount());
        map.put("isAll",entity.getIsAll());
        map.put("familyId",entity.getFamilyId());
        map.put("familyName",entity.getFamilyName());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<BenefitBadge> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (BenefitBadge entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}