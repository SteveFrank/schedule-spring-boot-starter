package com.starter.schedule.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author frankq
 * @date 2021/9/8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DcsScheduled {

    String desc() default "任务使用简介";

    /**
     * 任务表达式
     */
    String cron() default "";

    /**
     * 是否自动开启调用
     */
    boolean autoStartup() default true;

}
