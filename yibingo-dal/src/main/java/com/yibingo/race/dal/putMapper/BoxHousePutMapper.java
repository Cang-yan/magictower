package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.BoxHouse;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 盲盒仓库PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BoxHousePutMapper{

    private String blindBoxId;
    private Integer count;
    private String userId;

    public static BoxHouse convertToEntity(BoxHousePutMapper putMapper){
        BoxHouse entity = new BoxHouse();
        entity.setBlindBoxId(putMapper.getBlindBoxId());
        entity.setCount(putMapper.getCount());
        entity.setUserId(putMapper.getUserId());
        return entity;
    }



}