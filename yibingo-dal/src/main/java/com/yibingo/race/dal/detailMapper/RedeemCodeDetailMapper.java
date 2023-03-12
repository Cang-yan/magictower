package com.yibingo.race.dal.detailMapper;

import com.yibingo.race.dal.entity.RedeemCode;

import lombok.Data;


import java.util.*;


/**
 * 兑换码;DetailMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:38
 */
@Data
public class RedeemCodeDetailMapper{


    public static Map<String,Object> buildMap(RedeemCode entity){
        Map<String,Object> map = new HashMap<>();
        map.put("redeemCodeId",entity.getId());
        map.put("lotteryPrizeId",entity.getLotteryPrizeId());
        map.put("text",entity.getText());
        map.put("goodsId",entity.getGoodsId());
        map.put("redeemCodeType",entity.getType());
        return map;
    }


    public static List<Map<String,Object>> buildMapList(List<RedeemCode> entities){
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (RedeemCode entity : entities){
            mapList.add(buildMap(entity));
        }
        return mapList;
    }
}