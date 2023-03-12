package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.Prop;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 道具PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class PropPutMapper{

    private String url;
    private String name;
    private Integer type;
    private String introduce;

    public static Prop convertToEntity(PropPutMapper putMapper){
        Prop entity = new Prop();
        entity.setUrl(putMapper.getUrl());
        entity.setName(putMapper.getName());
        entity.setType(putMapper.getType());
        entity.setIntroduce(putMapper.getIntroduce());
        return entity;
    }



}