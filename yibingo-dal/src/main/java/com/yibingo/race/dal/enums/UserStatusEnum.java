package com.yibingo.race.dal.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Objects;

/**
 * 藏品仓库中user_status
 */
@Getter
public enum UserStatusEnum {
    GOT(0, "已获得"),
    EXCHANGED(1, "已转赠"),
    AIR_DROP(2, "空投"),
    COMPOUND(3, "合成"),
    CONSIGNMENT(4, "寄售"),
    MEANINGLESS(9, "无意义");

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String status;


    UserStatusEnum(int key, String status) {
        this.key = key;
        this.status = status;
    }

    public static String getStatusByKey(Integer key){
        String status = MEANINGLESS.status;
        UserStatusEnum[] values = UserStatusEnum.values();
        for(UserStatusEnum userStatusEnum: values){
            if(Objects.equals(key, userStatusEnum.getKey())){
                status = userStatusEnum.getStatus();
            }
        }
        return status;
    }
}
