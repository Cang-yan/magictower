package com.yibingo.race.quartz.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Describe: 示例任务
 * Author: 就免仪式
 * CreateTime: 2019/10/23
 * 异常任务
 * */
@Slf4j
@Component("exceptionTask")
public class ExceptionTaskImpl implements BaseTaskService {

    //todo 日志里  status是0 才算运行成功
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
    /**
     * 任务实现
     * */
    @Override
    public void run(String params) throws Exception{
        log.info("当前时间::::" + FORMAT.format(new Date()));
        throw new Exception("发生异常");
    }
}
