package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.BenefitUser;

import lombok.Data;


import java.util.*;


/**
 * 用户获得的权益表DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BenefitUserDetailMapper{


    public static Map<String,Object> buildMap(BenefitUser entity){
        Map<String,Object> map = new HashMap<>();
        map.put("benefitUserId",entity.getId());
        map.put("userId",entity.getUserId());
        map.put("benefitPrebuyCount",entity.getBenefitPrebuyCount());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<BenefitUser> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (BenefitUser entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}