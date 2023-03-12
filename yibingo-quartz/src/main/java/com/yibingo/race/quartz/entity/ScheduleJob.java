package com.yibingo.race.quartz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
    * 定时任务
    */
@Data
@TableName(value = "schedule_job")
public class ScheduleJob implements Serializable { // 必须序列化

    // 必须要有
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    /**
     * 任务id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * spring bean名称
     */
    @TableField(value = "bean_name")
    private String beanName;

    /**
     * 参数
     */
    @TableField(value = "params")
    private String params;

    /**
     * cron表达式
     */
    @TableField(value = "cron_expression")
    private String cronExpression;

    /**
     * 任务状态  0：正常  1：暂停
     */
    @TableField(value = "`status`")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "job_name")
    private String jobName;

    private static final long serialVersionUID = 1L; // 必须要有
}