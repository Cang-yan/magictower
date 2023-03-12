package com.yibingo.race.quartz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.yibingo.race.common.utils.Result;
import com.yibingo.race.quartz.entity.ScheduleJob;
import com.yibingo.race.quartz.entity.ScheduleLog;
import com.yibingo.race.quartz.schedule.ScheduleHandler;
import com.yibingo.race.quartz.service.ScheduleJobService;
import com.yibingo.race.quartz.service.ScheduleLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.Scheduler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(
        value = "schedule",
        tags = "定时任务"
)
@RestController
@RequestMapping("job/sc")
public class ScheduleController {
    @Resource
    ScheduleJobService jobService;
    @Resource
    ScheduleLogService logService;
    @Resource
    Scheduler scheduler;


    /*
     * 查找定时任务
     *
     * */
    @ApiOperation(
            value = "查找定时任务",
            notes = "查找定时任务"
    )
    @RequestMapping(
            value = "/task_search",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String,Object> taskSearch() {
        List<ScheduleJob> jobList = jobService.list();

        return Result.success(jobList).map();
    }

    /**
     * 根据任务ID运行一个任务
     *
     * @param jobId 任务ID
     * @return 运行状态
     */
    @ApiOperation(
            value = "根据任务ID运行一个任务",
            notes = "根据任务ID运行一个任务"
    )
    @RequestMapping(
            value = "/task_run",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String,Object> taskRun(String jobId) {
        ScheduleJob job = jobService.getById(jobId);
        ScheduleHandler.run(scheduler, job);
        return Result.success(null,"成功").map();

    }

    /**
     * 保存任务
     *
     * @param job 任务实体
     * @return 保存结果
     */
    @ApiOperation(
            value = "新建定时任务",
            notes = "新建定时任务"
    )
    @RequestMapping(
            value = "/task_save",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String,Object> taskSave(ScheduleJob job) {
        job.setId(IdWorker.getIdStr());
        job.setCreateTime(LocalDateTime.now());
        //TODO 如不需要可注释掉
        job.setParams(job.getId());

        jobService.save(job);
        ScheduleHandler.createJob(scheduler, job);
        return Result.success(null,"成功").map();

    }

    /**
     * 更新任务
     *
     * @param job 任务数据
     * @return 更新结果
     */
    @ApiOperation(
            value = "更新任务",
            notes = "更新任务"
    )
    @RequestMapping(
            value = "/task_update",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String,Object> taskUpdate(ScheduleJob job) {
        jobService.updateById(job);
        ScheduleHandler.updateJob(scheduler, job);
        return Result.success(null,"成功").map();

    }

    /**
     * 停止定时任务
     *
     * @param jobId 任务Id
     * @return 执行结果
     */
    @ApiOperation(
            value = "停止定时任务",
            notes = "停止定时任务"
    )
    @RequestMapping(
            value = "/task_pause",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String,Object> taskPause(String jobId) {
        ScheduleJob scheduleJob = jobService.getById(jobId);
        ScheduleHandler.pauseJob(scheduler, Long.parseLong(jobId));
        scheduleJob.setStatus("1");
        jobService.updateById(scheduleJob);
        return Result.success(null,"成功").map();

    }

    /**
     * 恢复定时任务
     *
     * @param jobId 任务Id
     * @return 执行结果
     */
    @ApiOperation(
            value = "恢复定时任务",
            notes = "恢复定时任务"
    )
    @RequestMapping(
            value = "/task_resume",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String,Object> taskResume(String jobId) {
        ScheduleJob scheduleJob = jobService.getById(jobId);
        ScheduleHandler.resumeJob(scheduler, Long.parseLong(jobId));
        scheduleJob.setStatus("0");
        jobService.updateById(scheduleJob);
        return Result.success(null,"成功").map();

    }

    /**
     * 删除一个任务
     *
     * @param jobId 任务ID
     * @return 删除结果
     */
    @ApiOperation(
            value = "删除一个任务",
            notes = "删除一个任务"
    )
    @RequestMapping(
            value = "/task_del",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String,Object> taskDel(String jobId) {
        ScheduleHandler.deleteJob(scheduler, Long.parseLong(jobId));
        jobService.removeById(jobId);
        return Result.success(null,"成功").map();

    }

    @ApiOperation(
            value = "查询日志",
            notes = "查询日志"
    )
    @RequestMapping(
            value = "/logs_get",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String,Object> logsGet(){
        QueryWrapper<ScheduleLog> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("create_time" );

        return Result.success(
                logService.list(wrapper)
        ).map();

    }


    @ApiOperation(
            value = "删除指定数量的日志",
            notes = "删除指定数量的日志"
    )
    @RequestMapping(
            value = "/logs_del",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String,Object> logsDel(@RequestParam Integer num){
        QueryWrapper<ScheduleLog> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("create_time" );
        //取前这些个
        List<ScheduleLog> scheduleLogList = logService.list(wrapper).subList(0,num);
        logService.removeByIds(scheduleLogList.stream().map(ScheduleLog::getId).collect(Collectors.toList()));
        return Result.success().map();

    }


}
