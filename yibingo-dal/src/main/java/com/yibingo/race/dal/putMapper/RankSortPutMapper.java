package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.RankSort;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 品质分级,默认概率写进枚举PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class RankSortPutMapper{

    private String name;
    private Integer qualityCode;

    public static RankSort convertToEntity(RankSortPutMapper putMapper){
        RankSort entity = new RankSort();
        entity.setName(putMapper.getName());
        entity.setQualityCode(putMapper.getQualityCode());
        return entity;
    }



}