package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.PieceComRule;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 碎片合成规则PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class PieceComRulePutMapper{

    private String pieceId;
    private Integer countNeed;
    private String metaNftId;

    public static PieceComRule convertToEntity(PieceComRulePutMapper putMapper){
        PieceComRule entity = new PieceComRule();
        entity.setPieceId(putMapper.getPieceId());
        entity.setCountNeed(putMapper.getCountNeed());
        entity.setMetaNftId(putMapper.getMetaNftId());
        return entity;
    }



}