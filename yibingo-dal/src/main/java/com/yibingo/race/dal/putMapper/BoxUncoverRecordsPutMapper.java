package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.BoxUncoverRecords;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 盲盒抽取记录PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BoxUncoverRecordsPutMapper{

    private String blindBoxId;
    private String metaNftId;
    private Integer count;

    public static BoxUncoverRecords convertToEntity(BoxUncoverRecordsPutMapper putMapper){
        BoxUncoverRecords entity = new BoxUncoverRecords();
        entity.setBlindBoxId(putMapper.getBlindBoxId());
        entity.setMetaNftId(putMapper.getMetaNftId());
        entity.setCount(putMapper.getCount());
        return entity;
    }



}