package com.starter.schedule.domain;

/**
 * 执行指令
 *
 * @author frankq
 * @date 2021/9/8
 */
public class Instruct {

    /**
     * 机器IP
     */
    private String ip;
    /**
     * 任务服务ID；工程名称En
     */
    private String schedulerServerId;
    /**
     * 类对象名称
     */
    private String beanName;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 任务执行
     */
    private String cron;
    /**
     * Constants.InstructStatus 0关闭、1启动、2更新
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
