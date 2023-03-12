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
 * 藏品仓库
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
@TableName(value = "ware_house",autoResultMap = true )
public class WareHouse implements Serializable{
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
    * 生成的编号
    */
    private Integer uuTag;
    /**
    * 图片id
    */
    private String metaNftId;
    /**
    * 系列id
    */
    private String familyId;
    /**
    * 状态 0已获得  1已转增  2 空投的  3合成的  4寄售 9无意义
    */
    private Integer userStatus;
    /**
    * 0未买  1是已买   2已锁定给管理员，查个人仓库的时候先查这个
    */
    private Integer nftStatus;
    /**
    * 0是不预留  1是预留
    */
    private Integer isReserve;
    /**
    * 藏品类型  枚举  武器道具徽章等
    */
    private Integer type;
    /**
    * 职业枚举
    */
    private Integer professionType;
    /**
    * 用户id
    */
    private String userId;

}
