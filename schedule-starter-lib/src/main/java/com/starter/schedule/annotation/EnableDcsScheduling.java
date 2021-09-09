package com.starter.schedule.annotation;

import com.starter.schedule.DoJoinPoint;
import com.starter.schedule.config.DcsSchedulingConfiguration;
import com.starter.schedule.task.CronTaskRegister;
import com.starter.schedule.task.SchedulingConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置在Application上用于决定当前应用是否开启分布式的任务调度
 * @author frankq
 * @date 2021/9/8
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DcsSchedulingConfiguration.class})
@ImportAutoConfiguration({SchedulingConfig.class, CronTaskRegister.class, DoJoinPoint.class})
@ComponentScan("com.starter.schedule.*")
public @interface EnableDcsScheduling {
}
