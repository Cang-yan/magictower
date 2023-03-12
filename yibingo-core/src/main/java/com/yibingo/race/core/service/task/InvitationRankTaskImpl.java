package com.yibingo.race.core.service.task;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yibingo.race.core.service.base.InvatitionRankBaseService;
import com.yibingo.race.core.service.base.InvatitionRankFilterService;
import com.yibingo.race.core.service.base.InvitationRecordsBaseService;
import com.yibingo.race.core.service.base.InvitationRecordsFilterService;
import com.yibingo.race.dal.entity.InvatitionRank;
import com.yibingo.race.dal.entity.InvitationRecords;
import com.yibingo.race.dal.filterMapper.InvatitionRankFilterMapper;
import com.yibingo.race.dal.filterMapper.InvitationRecordsFilterMapper;
import com.yibingo.race.quartz.entity.ScheduleLog;
import com.yibingo.race.quartz.service.ScheduleLogService;
import com.yibingo.race.quartz.task.BaseTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 排行榜榜单
 * @author: Yang Xin
 * @time: 2022/7/12 22:13
 */
@Slf4j
@Component("invitationRankTask" )
public class InvitationRankTaskImpl implements BaseTaskService {

    /*
     * INSERT INTO `schedule_job` VALUES ('1547062104262909954', 'invitationRankTask', '1547062104262909954', '0 0 8 * * ?', NULL, NULL, '2022-07-13 11:35:23', '每天8点更新榜单');
     * */

    //每晚八点更新榜单
    @Autowired
    private ScheduleLogService scheduleLogService;

    @Autowired
    private InvitationRecordsBaseService invitationRecordsBaseService;

    @Autowired
    private InvatitionRankBaseService invatitionRankBaseService;

    /**
     * @param params 这个在创建定时任务的时候要指定成他自己的id就好了
     * @throws Exception
     */
    @Override
    public void run(String params) throws Exception {
        LocalDateTime lastRunTime = null;
        //拿上次任务执行的记录
        QueryWrapper<ScheduleLog> wrapper = new QueryWrapper<>();
        wrapper.eq("job_id" , params);
        wrapper.orderByDesc("create_time" );
        List<ScheduleLog> scheduleLogList = scheduleLogService.list(wrapper);
        Integer count = scheduleLogList.size();
        //数据库大于1000条要进行删除
        if (count > 1000) {
            wrapper = new QueryWrapper<>();
            wrapper.orderByAsc("create_time" );
            //取前这些个
            List<ScheduleLog> scheduleLogListToDelete = scheduleLogService.list(wrapper).subList(0, 900);
            scheduleLogService.removeByIds(scheduleLogList.stream().map(ScheduleLog::getId).collect(Collectors.toList()));
        }
        //判断最近一次成功执行的时间
        for (ScheduleLog scheduleLog : scheduleLogList) {
            if (scheduleLog.getStatus().equals("0" )) {
                lastRunTime = scheduleLog.getCreateTime();
                break;
            }
        }

        InvitationRecordsFilterMapper invitationRecordsFilterMapper = new InvitationRecordsFilterMapper();
        invitationRecordsFilterMapper.updateTimeTo = DateUtil.current();
        invitationRecordsFilterMapper.isIdentified = 1;
        log.info("从{},到{},时间内定时任务执行成功" , lastRunTime, invitationRecordsFilterMapper.createTimeTo);

        //如果存在上次成功执行的记录  这里有问题啊我去
        if (lastRunTime != null) {
            invitationRecordsFilterMapper.updateTimeFrom = lastRunTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        }

        //截取邀新记录中上次更新时间到现在所增加的邀新记录
        List<InvitationRecords> invitationRecordsList = InvitationRecordsFilterService.getListByFilter(invitationRecordsBaseService, invitationRecordsFilterMapper);
        if (invitationRecordsList.isEmpty()) return;

        List<String> userIdList = invitationRecordsList.stream().map(InvitationRecords::getHostId).distinct().collect(Collectors.toList());
        if (userIdList.isEmpty()) return;

        //获取这些人的排行榜信息
        InvatitionRankFilterMapper invatitionRankFilterMapper = new InvatitionRankFilterMapper();
        invatitionRankFilterMapper.userIdIn = userIdList;
        List<InvatitionRank> invatitionRankList = InvatitionRankFilterService.getListByFilter(invatitionRankBaseService, invatitionRankFilterMapper);

        List<InvatitionRank> saveOrUpdateList = new ArrayList<>();
        //取hostId  ,   统计人数
        for (String userId : userIdList) {
            //拿出这个人的邀新记录
            List<InvitationRecords> invitationRecords = invitationRecordsList.stream().filter(
                    item -> item.getHostId() != null && item.getHostId().equals(userId)
            ).collect(Collectors.toList());
            Integer peopelNum = invitationRecords.size();

            InvatitionRank invatitionRank = new InvatitionRank();
            //如果列表中是空的
            if (invatitionRankList.isEmpty()) {

                invatitionRank.setCount(peopelNum);
                invatitionRank.setUserId(userId);

            } else {
                //列表不空
                invatitionRank = invatitionRankList.stream().filter(
                        item -> item.getUserId() != null && item.getUserId().equals(userId)
                ).findFirst().orElse(null);
                //列表中没有此人
                if (invatitionRank == null) {
                    invatitionRank = new InvatitionRank();
                    invatitionRank.setCount(peopelNum);
                    invatitionRank.setUserId(userId);
                } else {//列表中有此人
                    invatitionRank.setCount(invatitionRank.getCount() + peopelNum);
                }

            }

            saveOrUpdateList.add(invatitionRank);
        }


        //更新到邀新排行榜里
        invatitionRankBaseService.saveOrUpdateBatch(saveOrUpdateList);

    }
}
