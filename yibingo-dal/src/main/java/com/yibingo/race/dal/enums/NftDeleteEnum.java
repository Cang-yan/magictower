package com.yibingo.race.dal.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum NftDeleteEnum {

    notDelete(0, "存在"),
    isDelete(1, "已删除");

    @EnumValue
    private final Integer key;
    @JsonValue
    private final String status;


    NftDeleteEnum(int key, String status) {
        this.key = key;
        this.status = status;
    }
}
