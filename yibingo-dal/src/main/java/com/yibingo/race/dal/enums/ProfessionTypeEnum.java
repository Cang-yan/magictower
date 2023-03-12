package com.yibingo.race.dal.enums;

import java.util.Objects;

public enum ProfessionTypeEnum {
    Master(0,"秘术法师"),
    Gunman(1,"火铳枪手"),
    knight(2,"蒸汽骑士")

    ;


    private Integer key;
     private String profession;

    ProfessionTypeEnum(Integer key, String profession) {
        this.key = key;
        this.profession = profession;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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
}
