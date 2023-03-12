package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.Notify;

import lombok.Data;


import java.util.*;


/**
 * 公告通知DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class NotifyDetailMapper{


    public static Map<String,Object> buildMap(Notify entity){
        Map<String,Object> map = new HashMap<>();
        map.put("notifyId",entity.getId());
        map.put("title",entity.getTitle());
        map.put("content",entity.getContent());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<Notify> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Notify entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}