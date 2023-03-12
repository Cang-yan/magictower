package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.InvatitionRank;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 邀新排行榜PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class InvatitionRankPutMapper{

    private String userId;
    private Integer count;

    public static InvatitionRank convertToEntity(InvatitionRankPutMapper putMapper){
        InvatitionRank entity = new InvatitionRank();
        entity.setUserId(putMapper.getUserId());
        entity.setCount(putMapper.getCount());
        return entity;
    }



}