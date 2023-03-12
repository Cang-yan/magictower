package com.yibingo.race.core.service.task;

import com.yibingo.race.quartz.task.BaseTaskService;
import org.springframework.stereotype.Component;

/**
 * @description: 开奖 只运行一次的定时任务
 * @author: Yang Xin
 * @time: 2022/8/31 15:36
 */
@Component("lotteryOpenTask" )
public class LotteryOpenTaskImpl implements BaseTaskService {


    @Override
    public void run(String params) throws Exception {



    }
}
