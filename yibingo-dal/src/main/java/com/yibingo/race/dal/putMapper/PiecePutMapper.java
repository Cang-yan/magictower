package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.Piece;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 碎片PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class PiecePutMapper{

    private String metaNftId;

    public static Piece convertToEntity(PiecePutMapper putMapper){
        Piece entity = new Piece();
        entity.setMetaNftId(putMapper.getMetaNftId());
        return entity;
    }



}