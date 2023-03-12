package com.yibingo.race.dal.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Objects;

/**
 * @author Li XingHan
 * @description 藏品类型枚举
 * @date 2022-06-27 8:46
 */
@Getter
public enum NftTypeEnum {

    NONE(0,"无效"),
    HERO(1, "英雄"),
    EQUIP(2, "装备"),
    BADGE(3,"徽章"),
    BlindBox(4,"盲盒")
    ;

    @EnumValue
    private final Integer key;
    @JsonValue
    private final String type;


    NftTypeEnum(int key, String type) {
        this.key = key;
        this.type = type;
    }

    public static Integer getKeyByType(String type){
        Integer key = 0;
        NftTypeEnum[] values = NftTypeEnum.values();
        for(NftTypeEnum nftTypeEnum: values){
            if(Objects.equals(type, nftTypeEnum.getType())){
                key = nftTypeEnum.getKey();
            }
        }
        return key;
    }

    public static String getTypeByKey(Integer key){
        String type = "无效";
        NftTypeEnum[] values = NftTypeEnum.values();
        for(NftTypeEnum nftTypeEnum: values){
            if(Objects.equals(key, nftTypeEnum.getKey())){
                type = nftTypeEnum.getType();
            }
        }
        return type;
    }

    public static NftTypeEnum getEnumByKey(Integer key){
        NftTypeEnum[] values = NftTypeEnum.values();
        for(NftTypeEnum nftTypeEnum: values){
            if(Objects.equals(key, nftTypeEnum.getKey())){
                return nftTypeEnum;
            }
        }
        return NftTypeEnum.NONE;
    }
}
