package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.UserAuth;

import lombok.Data;


import java.util.*;


/**
 * 用户权限值DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class UserAuthDetailMapper{


    public static Map<String,Object> buildMap(UserAuth entity){
        Map<String,Object> map = new HashMap<>();
        map.put("userAuthId",entity.getId());
        map.put("auth",entity.getAuth());
        map.put("role",entity.getRole());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<UserAuth> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (UserAuth entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}