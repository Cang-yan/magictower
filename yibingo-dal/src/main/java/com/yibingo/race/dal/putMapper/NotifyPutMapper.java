package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.Notify;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 公告通知PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class NotifyPutMapper{

    private String title;
    private String content;

    public static Notify convertToEntity(NotifyPutMapper putMapper){
        Notify entity = new Notify();
        entity.setTitle(putMapper.getTitle());
        entity.setContent(putMapper.getContent());
        return entity;
    }



}