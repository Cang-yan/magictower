package com.yibingo.race.dal.enums;

public enum RedisPrefix {
    //TODO 检查并设置过期时间
    SeckillKey_isGoodsOver("go", 172800L),//两天
    GoodsKey_getGoodsStock("gs",172800L),
    OrderKey_getAleadyPurchaseByUidGid("seckill",172800L),
    BenefitKey_getUserBenefit("ub",129600L)//36小时的有效期
    ;

//前缀
    private String prefix;

    private Long expireSeconds;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    RedisPrefix(String prefix, Long expireSeconds) {
        this.prefix = prefix;
        this.expireSeconds = expireSeconds;
    }

    public Long getExpireSeconds() {
        return expireSeconds;
    }


}
