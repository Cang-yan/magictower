package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.ExchangeRecords;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 藏品交易记录PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class ExchangeRecordsPutMapper{

    private String metaNftId;
    private Integer uuTag;
    private String hostUserId;
    private String guestUserId;
    private Double price;
    private Integer type;

    public static ExchangeRecords convertToEntity(ExchangeRecordsPutMapper putMapper){
        ExchangeRecords entity = new ExchangeRecords();
        entity.setMetaNftId(putMapper.getMetaNftId());
        entity.setUuTag(putMapper.getUuTag());
        entity.setHostUserId(putMapper.getHostUserId());
        entity.setGuestUserId(putMapper.getGuestUserId());
        entity.setPrice(putMapper.getPrice());
        entity.setType(putMapper.getType());
        return entity;
    }



}