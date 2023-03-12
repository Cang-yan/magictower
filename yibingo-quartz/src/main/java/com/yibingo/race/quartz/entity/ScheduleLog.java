package com.yibingo.race.quartz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
    * 定时任务日志
    */
@Data
@TableName(value = "schedule_log")
public class ScheduleLog implements Serializable {
    /**
     * 任务日志id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 任务id
     */
    @TableField(value = "job_id")
    private String jobId;

    @TableField(value = "job_name")
    private String jobName;

    /**
     * 参数
     */
    @TableField(value = "params")
    private String params;

    /**
     * 任务状态    0：成功    1：失败
     */
    @TableField(value = "`status`")
    private String status;

    /**
     * 失败信息
     */
    @TableField(value = "error")
    private String error;

    /**
     * 耗时(单位：毫秒)
     */
    @TableField(value = "times")
    private Integer times;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * spring bean名称
     */
    @TableField(value = "bean_name")
    private String beanName;

    private static final long serialVersionUID = 1L;
}