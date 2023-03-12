package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.User;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 用户表PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class UserPutMapper{

    private String accountId;
    private String password;
    private String name;
    private String notes;
    private String phone;
    private String realName;
    private String identity;
    private Integer permission;
    private String head;
    private Integer energy;
    private String chainwallet;

    public static User convertToEntity(UserPutMapper putMapper){
        User entity = new User();
        entity.setAccountId(putMapper.getAccountId());
        entity.setPassword(putMapper.getPassword());
        entity.setName(putMapper.getName());
        entity.setNotes(putMapper.getNotes());
        entity.setPhone(putMapper.getPhone());
        entity.setRealName(putMapper.getRealName());
        entity.setIdentity(putMapper.getIdentity());
        entity.setPermission(putMapper.getPermission());
        entity.setHead(putMapper.getHead());
        entity.setEnergy(putMapper.getEnergy());
        entity.setChainwallet(putMapper.getChainwallet());
        return entity;
    }



}