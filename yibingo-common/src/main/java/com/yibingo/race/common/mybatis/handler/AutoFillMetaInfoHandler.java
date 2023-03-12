package com.yibingo.race.common.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author smalljop
 * mybatis 自动填充插件
 * @link https://baomidou.com/guide/typehandler.html
 */
@Slf4j
@Component
public class AutoFillMetaInfoHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime" , new Date(), metaObject);
        this.setFieldValByName("updateTime" , new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime" , new Date(), metaObject);
    }
}
