package com.yibingo.race.quartz.task;

public interface BaseTaskService {
    /**
     * 任 务 实 现
     * */
    void run(String params) throws Exception;
}
