package com.yibingo.race.dal.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Objects;

/**
 * @author Li XingHan
 * @description 仓库藏品状态枚举
 * @date 2022-07-01 9:58
 */
@Getter
public enum WareStatusEnum {

    OBTAINED(0, "已获得"),
    UNOPENED(1, "未拆"),
    DONATED(2, "已转赠"),
    AIRDROP(3, "空投"),
    COMPOUND(4, "合成");

    @EnumValue
    private final Integer key;
    @JsonValue
    private final String status;

    WareStatusEnum(int key, String status) {
        this.key = key;
        this.status = status;
    }

    public static Integer getKeyByType(String type) {
        Integer key = 0;
        WareStatusEnum[] values = WareStatusEnum.values();
        for (WareStatusEnum wareStatusEnum : values) {
            if (Objects.equals(type, wareStatusEnum.getStatus())) {
                key = wareStatusEnum.getKey();
            }
        }
        return key;
    }

    public static String getTypeByKey(Integer key) {
        String type = "无效";
        WareStatusEnum[] values = WareStatusEnum.values();
        for (WareStatusEnum wareStatusEnum : values) {
            if (Objects.equals(key, wareStatusEnum.getKey())) {
                type = wareStatusEnum.getStatus();
            }
        }
        return type;
    }
}
