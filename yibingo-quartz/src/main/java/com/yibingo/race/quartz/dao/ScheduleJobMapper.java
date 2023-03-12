package com.yibingo.race.quartz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yibingo.race.quartz.entity.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheduleJobMapper extends BaseMapper<ScheduleJob> {
}