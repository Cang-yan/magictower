package com.yibingo.race.quartz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * quartz配置类
 */
@Configuration
public class QuartzConfig {

    /**
     * 此种注入方式，在springboot2.6中获取不到，
     */
    @Resource
    private DataSource dataSource;

    /**
     * Describe: 定时任务工厂
     * Param: DataSource
     * Return: ScheduleFactoryBean
     * */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        Properties prop = new Properties();
        // QlScheduler这个值可以自定义
        prop.put("org.quartz.scheduler.instanceName", "QlScheduler");
        // 设置为AUTO时使用，默认的实现org.quartz.scheduler.SimpleInstanceGenerator是基于主机名称和时间戳生成。
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        // 线程池相关配置
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "20");
        prop.put("org.quartz.threadPool.threadPriority", "5");
        // JobStoreTX在每次执行任务后都使用commit或者rollback来提交更改。
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        // 集群配置：如果有多个调度器实体的话则必须设置为true，如果项目部署了多个，就设置为true
        prop.put("org.quartz.jobStore.isClustered", "false");
        // 集群配置：检查集群下的其他调度器实体的时间间隔
        prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        // 设置一个频度(毫秒)，用于实例报告给集群中的其他实例
        prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "5000");
        // 触发器触发失败后再次触犯的时间间隔
        prop.put("org.quartz.jobStore.misfireThreshold", "12000");

        // 数据库表前缀 重点这个和建表SQL是对应的关系，如果改了需要改动建表语句

        prop.put("org.quartz.jobStore.tablePrefix", "schedule_");
        // 从 LOCKS 表查询一行并对这行记录加锁的 SQL 语句
        prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS UPDLOCK WHERE LOCK_NAME = ?");

        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // 数据源
        factory.setDataSource(dataSource);
        // 上面的配置
        factory.setQuartzProperties(prop);
        // 可自定义
        factory.setSchedulerName("QlScheduler");
        // 项目启动后30秒后启动开始执行定时任务
        factory.setStartupDelay(30);
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        factory.setOverwriteExistingJobs(true);
        // 自动启动
        factory.setAutoStartup(true);
        return factory;
    }
}
