package com.starter.schedule.domain;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author frankq
 * @date 2021/9/8
 */
public class ExecOrder {
    /**
     * 类对象
     */
    @JSONField(serialize = false)
    private Object bean;
    /**
     * 类对象名称
     */
    private String beanName;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 任务描述
     */
    private String desc;
    /**
     * 任务执行
     */
    private String cron;
    /**
     * 任务状态
     */
    private Boolean autoStartup;

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Boolean getAutoStartup() {
        return autoStartup;
    }

    public void setAutoStartup(Boolean autoStartup) {
        this.autoStartup = autoStartup;
    }
}
