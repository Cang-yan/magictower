package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.Lottery;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 抽奖;PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:38
 */
@Data
public class LotteryPutMapper{

    private String title;
    private Integer limitRule;
    private Integer coreNum;
    private Integer isRunned;
    private Date runTime;

    public static Lottery convertToEntity(LotteryPutMapper putMapper){
        Lottery entity = new Lottery();
        entity.setTitle(putMapper.getTitle());
        entity.setLimitRule(putMapper.getLimitRule());
        entity.setCoreNum(putMapper.getCoreNum());
        entity.setIsRunned(putMapper.getIsRunned());
        entity.setRunTime(putMapper.getRunTime());
        return entity;
    }



}