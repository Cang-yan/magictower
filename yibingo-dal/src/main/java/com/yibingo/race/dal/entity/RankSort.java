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
 * 品质分级,默认概率写进枚举
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
@TableName(value = "rank_sort",autoResultMap = true )
public class RankSort implements Serializable{
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
    * 品质名字
    */
    private String name;
    /**
    * 品质代号，好排序
    */
    private Integer qualityCode;

}
