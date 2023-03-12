package com.yibingo.race.dal.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Objects;

/**
 * 盲盒仓库中盲盒是否转赠is_exchange
 */
@Getter
public enum BoxExchangeStatusEnum {
    NOT_EXCHANGE(0, "未转赠"),
    IS_EXCHANGE(1, "已转赠");

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String status;


    BoxExchangeStatusEnum(int key, String status) {
        this.key = key;
        this.status = status;
    }

    public static String getStatusByKey(Integer key){
        String status = "无效";
        BoxExchangeStatusEnum[] values = BoxExchangeStatusEnum.values();
        for(BoxExchangeStatusEnum boxExchangeStatusEnum: values){
            if(Objects.equals(key, boxExchangeStatusEnum.getKey())){
                status = boxExchangeStatusEnum.getStatus();
            }
        }
        return status;
    }
}
