package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.PieceComRecords;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 合成记录PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class PieceComRecordsPutMapper{

    private String pieceRuleId;
    private String userId;
    private String metaNftId;

    public static PieceComRecords convertToEntity(PieceComRecordsPutMapper putMapper){
        PieceComRecords entity = new PieceComRecords();
        entity.setPieceRuleId(putMapper.getPieceRuleId());
        entity.setUserId(putMapper.getUserId());
        entity.setMetaNftId(putMapper.getMetaNftId());
        return entity;
    }



}