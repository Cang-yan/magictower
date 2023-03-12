package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.BadgeNft;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 徽章藏品PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BadgeNftPutMapper{

    private String url;
    private String nickname;
    private String introduce;
    private String contractAddress;
    private String contractAgreement;
    private String familyId;
    private String familyName;
    private String authorId;
    private String stockId;

    public static BadgeNft convertToEntity(BadgeNftPutMapper putMapper){
        BadgeNft entity = new BadgeNft();
        entity.setUrl(putMapper.getUrl());
        entity.setNickname(putMapper.getNickname());
        entity.setIntroduce(putMapper.getIntroduce());
        entity.setContractAddress(putMapper.getContractAddress());
        entity.setContractAgreement(putMapper.getContractAgreement());
        entity.setFamilyId(putMapper.getFamilyId());
        entity.setFamilyName(putMapper.getFamilyName());
        entity.setAuthorId(putMapper.getAuthorId());
        entity.setStockId(putMapper.getStockId());
        return entity;
    }



}