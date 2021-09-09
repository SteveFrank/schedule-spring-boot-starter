package com.starter.schedule.domain;

/**
 * @author frankq
 * @date 2021/9/8
 */
public class DcsScheduleInfo {

    /**
     * 机器IP
     */
    private String ip;
    /**
     * 任务服务ID: 工程名称 英文
     */
    private String schedulerServerId;
    /**
     * 任务服务名称: 工程名称 中文
     */
    private String schedulerServerName;
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
     * 任务状态: 0关闭、1开启、2宕机
     */
    private Integer status;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSchedulerServerId() {
        return schedulerServerId;
    }

    public void setSchedulerServerId(String schedulerServerId) {
        this.schedulerServerId = schedulerServerId;
    }

    public String getSchedulerServerName() {
        return schedulerServerName;
    }

    public void setSchedulerServerName(String schedulerServerName) {
        this.schedulerServerName = schedulerServerName;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
