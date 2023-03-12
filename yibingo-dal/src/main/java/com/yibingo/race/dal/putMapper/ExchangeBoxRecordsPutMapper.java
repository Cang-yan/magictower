package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.ExchangeBoxRecords;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 盲盒交易记录PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class ExchangeBoxRecordsPutMapper{

    private String blindBoxId;
    private String hostUserId;
    private String guestUserId;
    private Double price;
    private Integer type;
    private Integer count;

    public static ExchangeBoxRecords convertToEntity(ExchangeBoxRecordsPutMapper putMapper){
        ExchangeBoxRecords entity = new ExchangeBoxRecords();
        entity.setBlindBoxId(putMapper.getBlindBoxId());
        entity.setHostUserId(putMapper.getHostUserId());
        entity.setGuestUserId(putMapper.getGuestUserId());
        entity.setPrice(putMapper.getPrice());
        entity.setType(putMapper.getType());
        entity.setCount(putMapper.getCount());
        return entity;
    }



}