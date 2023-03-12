package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.User;

import lombok.Data;


import java.util.*;


/**
 * 用户表DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class UserDetailMapper{


    public static Map<String,Object> buildMap(User entity){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",entity.getId());
        map.put("accountId",entity.getAccountId());
        map.put("password",entity.getPassword());
        map.put("name",entity.getName());
        map.put("notes",entity.getNotes());
        map.put("phone",entity.getPhone());
        map.put("realName",entity.getRealName());
        map.put("identity",entity.getIdentity());
        map.put("permission",entity.getPermission());
        map.put("head",entity.getHead());
        map.put("energy",entity.getEnergy());
        map.put("chainwallet",entity.getChainwallet());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<User> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (User entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}