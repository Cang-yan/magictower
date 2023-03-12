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
 * 徽章藏品
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
@TableName(value = "badge_nft",autoResultMap = true )
public class BadgeNft implements Serializable{
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
    * 图片url
    */
    private String url;
    /**
    * 名字
    */
    private String nickname;
    /**
    * 作品介绍
    */
    private String introduce;
    /**
    * 合约地址
    */
    private String contractAddress;
    /**
    * 合约协议
    */
    private String contractAgreement;
    /**
    * 系列
    */
    private String familyId;
    /**
    * 系列名字
    */
    private String familyName;
    /**
    * 作者id  默认平台root
    */
    private String authorId;
    /**
    * 库存id
    */
    private String stockId;

}
