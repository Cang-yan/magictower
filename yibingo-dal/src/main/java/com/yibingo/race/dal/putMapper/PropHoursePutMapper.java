package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.PropHourse;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 道具仓库PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class PropHoursePutMapper{

    private String propId;
    private String userId;
    private Integer count;

    public static PropHourse convertToEntity(PropHoursePutMapper putMapper){
        PropHourse entity = new PropHourse();
        entity.setPropId(putMapper.getPropId());
        entity.setUserId(putMapper.getUserId());
        entity.setCount(putMapper.getCount());
        return entity;
    }



}