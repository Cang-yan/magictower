package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.BenefitBadge;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 徽章权益表PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BenefitBadgePutMapper{

    private String badgeNftId;
    private Integer benefitPrebuyCount;
    private Integer isAll;
    private String familyId;
    private String familyName;

    public static BenefitBadge convertToEntity(BenefitBadgePutMapper putMapper){
        BenefitBadge entity = new BenefitBadge();
        entity.setBadgeNftId(putMapper.getBadgeNftId());
        entity.setBenefitPrebuyCount(putMapper.getBenefitPrebuyCount());
        entity.setIsAll(putMapper.getIsAll());
        entity.setFamilyId(putMapper.getFamilyId());
        entity.setFamilyName(putMapper.getFamilyName());
        return entity;
    }



}