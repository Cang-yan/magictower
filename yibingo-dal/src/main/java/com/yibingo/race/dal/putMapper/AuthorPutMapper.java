package com.yibingo.race.dal.putMapper;

import com.yibingo.race.dal.entity.Author;


import lombok.Data;
import java.util.Map;
import java.util.Date;

/**
 * 作品作者PutMapper
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
public class AuthorPutMapper{

    private String name;
    private String introduce;
    private String head;

    public static Author convertToEntity(AuthorPutMapper putMapper){
        Author entity = new Author();
        entity.setName(putMapper.getName());
        entity.setIntroduce(putMapper.getIntroduce());
        entity.setHead(putMapper.getHead());
        return entity;
    }



}