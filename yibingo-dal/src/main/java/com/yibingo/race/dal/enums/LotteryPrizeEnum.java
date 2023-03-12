package com.yibingo.race.dal.enums;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/9/6 16:32
 */
public enum LotteryPrizeEnum {
    Real(0,"实体的"),
    HERO(1, "英雄"),
    EQUIP(2, "装备"),
    BADGE(3,"徽章"),
    BlindBox(4,"盲盒")
    ;

    private Integer key;
    private String notes;

    LotteryPrizeEnum(Integer key, String notes) {
        this.key = key;
        this.notes = notes;
    }

    public Integer getKey() {
        return key;
    }

    public String getNotes() {
        return notes;
    }
}
