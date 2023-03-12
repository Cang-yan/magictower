package com.yibingo.race.quartz.schedule;



import com.yibingo.race.quartz.entity.ScheduleJob;
import com.yibingo.race.quartz.service.ScheduleJobService;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Describe: 定时任务启动处理类
 * Author: 就眠仪式
 * CreateTime: 2019/10/23
 * */
public class ScheduleStarted {

    @Resource
    private Scheduler scheduler ;

    @Resource
    private ScheduleJobService scheduleJobService;

    @PostConstruct
    public void init (){
        List<ScheduleJob> scheduleJobList = scheduleJobService.list();
        for (ScheduleJob scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleHandler.getCronTrigger(scheduler,Long.parseLong(scheduleJob.getId())) ;
            if (cronTrigger == null){
                ScheduleHandler.createJob(scheduler,scheduleJob);
            } else {
                ScheduleHandler.updateJob(scheduler,scheduleJob);
            }
        }
    }
}
