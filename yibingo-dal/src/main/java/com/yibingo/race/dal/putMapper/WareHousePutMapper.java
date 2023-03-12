package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.WareHouse;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 藏品仓库PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class WareHousePutMapper{

    private Integer uuTag;
    private String metaNftId;
    private String familyId;
    private Integer userStatus;
    private Integer nftStatus;
    private Integer isReserve;
    private Integer type;
    private Integer professionType;
    private String userId;

    public static WareHouse convertToEntity(WareHousePutMapper putMapper){
        WareHouse entity = new WareHouse();
        entity.setUuTag(putMapper.getUuTag());
        entity.setMetaNftId(putMapper.getMetaNftId());
        entity.setFamilyId(putMapper.getFamilyId());
        entity.setUserStatus(putMapper.getUserStatus());
        entity.setNftStatus(putMapper.getNftStatus());
        entity.setIsReserve(putMapper.getIsReserve());
        entity.setType(putMapper.getType());
        entity.setProfessionType(putMapper.getProfessionType());
        entity.setUserId(putMapper.getUserId());
        return entity;
    }



}