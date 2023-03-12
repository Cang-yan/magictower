package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.Author;

import lombok.Data;


import java.util.*;


/**
 * 作品作者DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class AuthorDetailMapper{


    public static Map<String,Object> buildMap(Author entity){
        Map<String,Object> map = new HashMap<>();
        map.put("authorId",entity.getId());
        map.put("name",entity.getName());
        map.put("introduce",entity.getIntroduce());
        map.put("head",entity.getHead());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<Author> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Author entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}