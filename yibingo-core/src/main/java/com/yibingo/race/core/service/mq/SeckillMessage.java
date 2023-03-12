package com.yibingo.race.core.service.mq;


import com.yibingo.race.dal.entity.User;
import lombok.Data;

import java.util.List;

/**
 * Created by jiangyunxiong on 2018/5/29.
 *
 * 消息体
 */
@Data
public class SeckillMessage {

    private String userId;
    private String goodsId;
    //购买数量
    private Integer purchaseNum;

    private String buyOrderId;

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public long getGoodsId() {
//        return goodsId;
//    }
//
//    public void setGoodsId(long goodsId) {
//        this.goodsId = goodsId;
//    }
}
