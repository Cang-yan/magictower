package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.Stock;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 库存PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class StockPutMapper{

    private Integer preCount;
    private Integer nowCount;
    private Integer reserveCount;

    public static Stock convertToEntity(StockPutMapper putMapper){
        Stock entity = new Stock();
        entity.setPreCount(putMapper.getPreCount());
        entity.setNowCount(putMapper.getNowCount());
        entity.setReserveCount(putMapper.getReserveCount());
        return entity;
    }



}