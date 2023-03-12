package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.MetaNft;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 藏品PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class MetaNftPutMapper{

    private String url;
    private String nickname;
    private String introduce;
    private String contractAddress;
    private String contractAgreement;
    private Integer type;
    private Integer professionType;
    private String familyId;
    private String familyName;
    private String rankId;
    private String authorId;
    private String stockId;

    public static MetaNft convertToEntity(MetaNftPutMapper putMapper){
        MetaNft entity = new MetaNft();
        entity.setUrl(putMapper.getUrl());
        entity.setNickname(putMapper.getNickname());
        entity.setIntroduce(putMapper.getIntroduce());
        entity.setContractAddress(putMapper.getContractAddress());
        entity.setContractAgreement(putMapper.getContractAgreement());
        entity.setType(putMapper.getType());
        entity.setProfessionType(putMapper.getProfessionType());
        entity.setFamilyId(putMapper.getFamilyId());
        entity.setFamilyName(putMapper.getFamilyName());
        entity.setRankId(putMapper.getRankId());
        entity.setAuthorId(putMapper.getAuthorId());
        entity.setStockId(putMapper.getStockId());
        return entity;
    }



}