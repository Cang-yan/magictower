package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.ExchangeRank;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 交易排行榜PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class ExchangeRankPutMapper{

    private String userId;
    private Double totalMoney;

    public static ExchangeRank convertToEntity(ExchangeRankPutMapper putMapper){
        ExchangeRank entity = new ExchangeRank();
        entity.setUserId(putMapper.getUserId());
        entity.setTotalMoney(putMapper.getTotalMoney());
        return entity;
    }



}