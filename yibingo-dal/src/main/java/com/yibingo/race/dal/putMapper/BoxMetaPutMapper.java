package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.BoxMeta;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 盲盒藏品绑定关系PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class BoxMetaPutMapper{

    private String blindBoxId;
    private String metaNftId;
    private String rankName;
    private Double metaNftPrice;
    private Double rankPossibility;
    private Double realPossibility;
    private String stockId;

    public static BoxMeta convertToEntity(BoxMetaPutMapper putMapper){
        BoxMeta entity = new BoxMeta();
        entity.setBlindBoxId(putMapper.getBlindBoxId());
        entity.setMetaNftId(putMapper.getMetaNftId());
        entity.setRankName(putMapper.getRankName());
        entity.setMetaNftPrice(putMapper.getMetaNftPrice());
        entity.setRankPossibility(putMapper.getRankPossibility());
        entity.setRealPossibility(putMapper.getRealPossibility());
        entity.setStockId(putMapper.getStockId());
        return entity;
    }



}