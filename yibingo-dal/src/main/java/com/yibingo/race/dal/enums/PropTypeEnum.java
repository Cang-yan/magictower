package com.yibingo.race.dal.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Objects;

/**
 * @author Li XingHan
 * @description 道具类型枚举
 * @date 2022-07-01 11:35
 */
@Getter
public enum PropTypeEnum {


    //TODO 道具类型待修改
    NONE(0,"无效"),
    HERO(1, "英雄"),
    EQUIP(2, "装备");

    @EnumValue
    private final Integer key;
    @JsonValue
    private final String type;


    PropTypeEnum(int key, String type) {
        this.key = key;
        this.type = type;
    }

    public static Integer getKeyByType(String type){
        Integer key = 0;
        PropTypeEnum[] values = PropTypeEnum.values();
        for(PropTypeEnum propTypeEnum: values){
            if(Objects.equals(type, propTypeEnum.getType())){
                key = propTypeEnum.getKey();
            }
        }
        return key;
    }

    public static String getTypeByKey(Integer key){
        String type = "无效";
        PropTypeEnum[] values = PropTypeEnum.values();
        for(PropTypeEnum propTypeEnum: values){
            if(Objects.equals(key, propTypeEnum.getKey())){
                type = propTypeEnum.getType();
            }
        }
        return type;
    }
}
