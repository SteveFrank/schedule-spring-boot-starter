package com.starter.schedule.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author frankq
 * @date 2021/9/9
 */
public class SchedulingRunnable implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulingRunnable.class);

    /**
     * 类对象
     */
    private Object bean;
    /**
     * 类名称
     */
    private String beanName;
    /**
     * 方法名称
     */
    private String methodName;

    public SchedulingRunnable(Object bean, String beanName, String methodName) {
        this.bean = bean;
        this.beanName = beanName;
        this.methodName = methodName;
    }

    @Override
    public void run() {
        try {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            ReflectionUtils.makeAccessible(method);
            method.invoke(bean);
        } catch (Exception e) {
            LOG.error("middleware schedule error！", e);
        }
    }

    public String taskId() {
        return beanName + "_" + methodName;
    }

}
