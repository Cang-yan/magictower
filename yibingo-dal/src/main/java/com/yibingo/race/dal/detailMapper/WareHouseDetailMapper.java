package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.WareHouse;

import lombok.Data;


import java.util.*;


/**
 * 藏品仓库DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class WareHouseDetailMapper{


    public static Map<String,Object> buildMap(WareHouse entity){
        Map<String,Object> map = new HashMap<>();
        map.put("wareHouseId",entity.getId());
        map.put("uuTag",entity.getUuTag());
        map.put("metaNftId",entity.getMetaNftId());
        map.put("familyId",entity.getFamilyId());
        map.put("userStatus",entity.getUserStatus());
        map.put("nftStatus",entity.getNftStatus());
        map.put("isReserve",entity.getIsReserve());
        map.put("wareHouseType",entity.getType());
        map.put("professionType",entity.getProfessionType());
        map.put("userId",entity.getUserId());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<WareHouse> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (WareHouse entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}