package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.Family;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 系列PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class FamilyPutMapper{

    private String title;
    private String introduction;
    private String url;
    private String contractAddress;
    private String contractAgreement;

    public static Family convertToEntity(FamilyPutMapper putMapper){
        Family entity = new Family();
        entity.setTitle(putMapper.getTitle());
        entity.setIntroduction(putMapper.getIntroduction());
        entity.setUrl(putMapper.getUrl());
        entity.setContractAddress(putMapper.getContractAddress());
        entity.setContractAgreement(putMapper.getContractAgreement());
        return entity;
    }



}