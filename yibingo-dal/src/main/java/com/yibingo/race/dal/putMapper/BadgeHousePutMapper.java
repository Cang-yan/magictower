package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.BadgeHouse;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 徽章仓库PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BadgeHousePutMapper{

    private String metaNftId;
    private String userId;

    public static BadgeHouse convertToEntity(BadgeHousePutMapper putMapper){
        BadgeHouse entity = new BadgeHouse();
        entity.setMetaNftId(putMapper.getMetaNftId());
        entity.setUserId(putMapper.getUserId());
        return entity;
    }



}