package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.UserInvite;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 用户邀请码PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class UserInvitePutMapper{

    private String userId;
    private String code;

    public static UserInvite convertToEntity(UserInvitePutMapper putMapper){
        UserInvite entity = new UserInvite();
        entity.setUserId(putMapper.getUserId());
        entity.setCode(putMapper.getCode());
        return entity;
    }



}