package com.yibingo.race.dal.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Objects;

/**
 * 藏品仓库中nft_status
 */
@Getter
public enum NftStatusEnum {

    NOT_SELL(0, "未卖"),
    DID_SELL(1, "已卖"),
    LOCKED(2, "已锁定");

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String status;


    NftStatusEnum(int key, String status) {
        this.key = key;
        this.status = status;
    }

    public static String getStatusByKey(Integer key){
        String status = "无效";
        NftStatusEnum[] values = NftStatusEnum.values();
        for(NftStatusEnum nftStatusEnum: values){
            if(Objects.equals(key, nftStatusEnum.getKey())){
                status = nftStatusEnum.getStatus();
            }
        }
        return status;
    }
}
