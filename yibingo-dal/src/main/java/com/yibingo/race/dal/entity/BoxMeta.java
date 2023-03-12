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
 * 盲盒藏品绑定关系
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
@TableName(value = "box_meta",autoResultMap = true )
public class BoxMeta implements Serializable{
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
    * 盲盒id
    */
    private String blindBoxId;
    /**
    * 藏品id
    */
    private String metaNftId;
    /**
    * 品质
    */
    private String rankName;
    /**
    * 藏品原价
    */
    private Double metaNftPrice;
    /**
    * 品质概率
    */
    private Double rankPossibility;
    /**
    * 实际概率
    */
    private Double realPossibility;
    /**
    * 库存id
    */
    private String stockId;

}
