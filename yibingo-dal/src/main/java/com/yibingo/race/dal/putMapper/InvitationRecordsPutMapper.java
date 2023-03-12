package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.InvitationRecords;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 邀请记录PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class InvitationRecordsPutMapper{

    private String hostId;
    private String invitedId;
    private Integer isIdentified;

    public static InvitationRecords convertToEntity(InvitationRecordsPutMapper putMapper){
        InvitationRecords entity = new InvitationRecords();
        entity.setHostId(putMapper.getHostId());
        entity.setInvitedId(putMapper.getInvitedId());
        entity.setIsIdentified(putMapper.getIsIdentified());
        return entity;
    }



}