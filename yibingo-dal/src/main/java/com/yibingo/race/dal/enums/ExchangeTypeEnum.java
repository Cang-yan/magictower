package com.yibingo.race.dal.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ExchangeTypeEnum {

    BUY(0, "购买"),
    Donation(1, "藏品转赠"),
    SOLD(2, "售出"),
    AirDrop(3,"空投的"),
    BadgeBuy(4,"徽章购买"),
    BadgeDonation(5,"徽章转增"),
    BadgeSold(6,"徽章空投")
    ;

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String type;


    ExchangeTypeEnum(Integer key, String type) {
        this.key = key;
        this.type = type;
    }
}
