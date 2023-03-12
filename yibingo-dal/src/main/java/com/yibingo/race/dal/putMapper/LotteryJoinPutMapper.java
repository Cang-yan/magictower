package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.LotteryJoin;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 抽奖参与人员;PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:38
 */
@Data
public class LotteryJoinPutMapper{

    private String lotteryId;
    private String userId;

    public static LotteryJoin convertToEntity(LotteryJoinPutMapper putMapper){
        LotteryJoin entity = new LotteryJoin();
        entity.setLotteryId(putMapper.getLotteryId());
        entity.setUserId(putMapper.getUserId());
        return entity;
    }



}