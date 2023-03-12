package com.yibingo.race.dal.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.yibingo.race.common.mybatis.handler.JacksonTypeHandler;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import java.util.Map;


/**
 * 用户表
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
@TableName(value = "user",autoResultMap = true )
public class User implements Serializable{
private static final long serialVersionUID=1L;

    /**
    * 主键
    */
    @TableId
    private String id;
    /**
    * 用户账号
    */
    private String accountId;
    /**
    * 创建时间
    */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
    * 更新时间
    */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
    * 密码
    */
    private String password;
    /**
    * 用户名
    */
    private String name;
    /**
    * 个性签名
    */
    private String notes;
    /**
    * 手机号
    */
    private String phone;
    /**
    * 真名
    */
    private String realName;
    /**
    * 身份证
    */
    private String identity;
    /**
    * 权限值，由枚举统一确定
    */
    private Integer permission;
    /**
    * 头像
    */
    private String head;
    /**
    * 耐力值
    */
    private Integer energy;
    /**
    * 区块链钱包
    */
    private String chainwallet;

}
