package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.InvitationRecords;

import lombok.Data;


import java.util.*;


/**
 * 邀请记录DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class InvitationRecordsDetailMapper{


    public static Map<String,Object> buildMap(InvitationRecords entity){
        Map<String,Object> map = new HashMap<>();
        map.put("invitationRecordsId",entity.getId());
        map.put("hostId",entity.getHostId());
        map.put("invitedId",entity.getInvitedId());
        map.put("isIdentified",entity.getIsIdentified());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<InvitationRecords> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (InvitationRecords entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}