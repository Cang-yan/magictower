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
 * 兑换码;
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:38
 */
@Data
@TableName(value = "redeem_code",autoResultMap = true )
public class RedeemCode implements Serializable{
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
    * 抽奖奖品id
    */
    private String lotteryPrizeId;
    /**
    * 兑换码
    */
    private String text;
    /**
    * 物品id  藏品 徽章 盲盒的id
    */
    private String goodsId;
    /**
    * 类型枚举，标志物品类型 等
    */
    private Integer type;

}
