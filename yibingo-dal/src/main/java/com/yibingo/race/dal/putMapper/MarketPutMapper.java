package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.Market;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 商城PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class MarketPutMapper{

    private String relationId;
    private Double price;
    private Integer isForSell;
    private Date sellTime;
    private String familyId;
    private Integer nftType;
    private Integer nowCount;
    private Integer limitPurchase;
    private Integer version;

    public static Market convertToEntity(MarketPutMapper putMapper){
        Market entity = new Market();
        entity.setRelationId(putMapper.getRelationId());
        entity.setPrice(putMapper.getPrice());
        entity.setIsForSell(putMapper.getIsForSell());
        entity.setSellTime(putMapper.getSellTime());
        entity.setFamilyId(putMapper.getFamilyId());
        entity.setNftType(putMapper.getNftType());
        entity.setNowCount(putMapper.getNowCount());
        entity.setLimitPurchase(putMapper.getLimitPurchase());
        entity.setVersion(putMapper.getVersion());
        return entity;
    }



}