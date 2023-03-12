package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.BenefitUser;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 用户获得的权益表PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BenefitUserPutMapper{

    private String userId;
    private Integer benefitPrebuyCount;

    public static BenefitUser convertToEntity(BenefitUserPutMapper putMapper){
        BenefitUser entity = new BenefitUser();
        entity.setUserId(putMapper.getUserId());
        entity.setBenefitPrebuyCount(putMapper.getBenefitPrebuyCount());
        return entity;
    }



}