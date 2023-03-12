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
 * 商城
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-09-06 16:57:39
 */
@Data
@TableName(value = "market",autoResultMap = true )
public class Market implements Serializable{
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
    * 关联id，图片id或者盲盒id
    */
    private String relationId;
    /**
    * 价格
    */
    private Double price;
    /**
    * 是否出售，0不卖 1卖
    */
    private Integer isForSell;
    /**
    * 开售时间
    */
    private Date sellTime;
    /**
    * 系列Id
    */
    private String familyId;
    /**
    * 藏品类型
    */
    private Integer nftType;
    /**
    * 现在还有的库存
    */
    private Integer nowCount;
    /**
    * 限购数量
    */
    private Integer limitPurchase;
    /**
    * 版本号，用来设置乐观锁
    */
    private Integer version;

}
