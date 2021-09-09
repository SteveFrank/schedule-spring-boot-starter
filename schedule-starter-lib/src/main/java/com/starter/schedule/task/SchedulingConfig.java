package com.starter.schedule.task;

import com.starter.schedule.common.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author frankq
 * @date 2021/9/9
 */
@Configuration("middlware-schedule-schedulingConfig")
public class SchedulingConfig {

    @Bean("middlware-schedule-taskScheduler")
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(Constants.Global.schedulePoolSize);
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setThreadNamePrefix("MiddlewareScheduleThreadPool-");
        return taskScheduler;
    }

}
