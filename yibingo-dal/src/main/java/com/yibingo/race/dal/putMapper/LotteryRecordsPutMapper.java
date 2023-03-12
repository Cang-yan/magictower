package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.LotteryRecords;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 抽奖记录;PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:38
 */
@Data
public class LotteryRecordsPutMapper{

    private String lotteryId;
    private String userId;
    private String redeemCodeId;

    public static LotteryRecords convertToEntity(LotteryRecordsPutMapper putMapper){
        LotteryRecords entity = new LotteryRecords();
        entity.setLotteryId(putMapper.getLotteryId());
        entity.setUserId(putMapper.getUserId());
        entity.setRedeemCodeId(putMapper.getRedeemCodeId());
        return entity;
    }



}