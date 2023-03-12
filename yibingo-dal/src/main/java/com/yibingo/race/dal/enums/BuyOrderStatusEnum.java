package com.yibingo.race.dal.enums;

public enum BuyOrderStatusEnum {
    InLine(-1,"排队中"),
    Failed(0,"藏品售罄，秒杀失败"),
    WaitPay(1,"秒杀成功，等待支付"),
    Success(2,"秒杀已完成"),
    Locked(3,"超时未支付，已取消订单"),

    WaitGive(4,"支付成功，等待发放"),

    ;


    private Integer key;
    private String type;

    BuyOrderStatusEnum(Integer key, String type) {
        this.key = key;
        this.type = type;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
