package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.PieceHouse;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 碎片仓库PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:38
 */
@Data
public class PieceHousePutMapper{

    private String pieceId;
    private Integer count;
    private String userId;

    public static PieceHouse convertToEntity(PieceHousePutMapper putMapper){
        PieceHouse entity = new PieceHouse();
        entity.setPieceId(putMapper.getPieceId());
        entity.setCount(putMapper.getCount());
        entity.setUserId(putMapper.getUserId());
        return entity;
    }



}