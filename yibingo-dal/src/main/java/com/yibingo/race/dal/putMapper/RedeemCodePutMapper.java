package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.RedeemCode;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 兑换码;PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:38
 */
@Data
public class RedeemCodePutMapper{

    private String lotteryPrizeId;
    private String text;
    private String goodsId;
    private Integer type;

    public static RedeemCode convertToEntity(RedeemCodePutMapper putMapper){
        RedeemCode entity = new RedeemCode();
        entity.setLotteryPrizeId(putMapper.getLotteryPrizeId());
        entity.setText(putMapper.getText());
        entity.setGoodsId(putMapper.getGoodsId());
        entity.setType(putMapper.getType());
        return entity;
    }



}