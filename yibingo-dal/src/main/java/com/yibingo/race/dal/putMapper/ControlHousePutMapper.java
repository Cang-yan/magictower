package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.ControlHouse;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 管理员仓库PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class ControlHousePutMapper{

    private String wareHouseId;

    public static ControlHouse convertToEntity(ControlHousePutMapper putMapper){
        ControlHouse entity = new ControlHouse();
        entity.setWareHouseId(putMapper.getWareHouseId());
        return entity;
    }



}