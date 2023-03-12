package com.yibingo.race.dal.enums;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/8/31 15:24
 */
public enum LotteryRuleEnum {
    Invitation(0, "邀请人数限制" );

    private Integer key;

    private String value;

    LotteryRuleEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
