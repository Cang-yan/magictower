package com.yibingo.race.dal.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 藏品仓库中藏品的预留状态
 */
@Getter
public enum NftReserveEnum {
    NOT_RESERVE(0, "不预留"),
    IS_RESERVE(1, "预留");


    @EnumValue
    private final Integer key;

    @JsonValue
    private final String status;


    NftReserveEnum(int key, String status) {
        this.key = key;
        this.status = status;
    }
}
