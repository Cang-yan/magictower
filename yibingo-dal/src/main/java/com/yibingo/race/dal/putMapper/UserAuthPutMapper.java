package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.UserAuth;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 用户权限值PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class UserAuthPutMapper{

    private Integer auth;
    private Integer role;

    public static UserAuth convertToEntity(UserAuthPutMapper putMapper){
        UserAuth entity = new UserAuth();
        entity.setAuth(putMapper.getAuth());
        entity.setRole(putMapper.getRole());
        return entity;
    }



}