package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.LotteryPrize;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 抽奖奖品;PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:38
 */
@Data
public class LotteryPrizePutMapper{

    private String lotteryId;
    private String text;
    private Integer grade;
    private Double possibility;
    private Integer stockCount;

    public static LotteryPrize convertToEntity(LotteryPrizePutMapper putMapper){
        LotteryPrize entity = new LotteryPrize();
        entity.setLotteryId(putMapper.getLotteryId());
        entity.setText(putMapper.getText());
        entity.setGrade(putMapper.getGrade());
        entity.setPossibility(putMapper.getPossibility());
        entity.setStockCount(putMapper.getStockCount());
        return entity;
    }



}