package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.UserInvite;

import lombok.Data;


import java.util.*;


/**
 * 用户邀请码DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class UserInviteDetailMapper{


    public static Map<String,Object> buildMap(UserInvite entity){
        Map<String,Object> map = new HashMap<>();
        map.put("userInviteId",entity.getId());
        map.put("userId",entity.getUserId());
        map.put("code",entity.getCode());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<UserInvite> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (UserInvite entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}