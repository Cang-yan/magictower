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
 * 盲盒交易记录
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
@TableName(value = "exchange_box_records",autoResultMap = true )
public class ExchangeBoxRecords implements Serializable{
private static final long serialVersionUID=1L;

    /**
    * 主键
    */
    @TableId
    private String id;
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
    * 盲盒Id
    */
    private String blindBoxId;
    /**
    * 交易盲盒持有者id
    */
    private String hostUserId;
    /**
    * 转增人，购买人Id
    */
    private String guestUserId;
    /**
    * 价格,转增为0
    */
    private Double price;
    /**
    * 枚举  购买的，转增的，二级的 空投的
    */
    private Integer type;
    /**
    * 数量
    */
    private Integer count;

}
