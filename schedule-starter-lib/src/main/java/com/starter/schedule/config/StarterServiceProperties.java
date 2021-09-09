package com.starter.schedule.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 获取基础配置
 * @author frankq
 * @date 2021/9/8
 */
@ConfigurationProperties("middleware.schedule")
public class StarterServiceProperties {

    /**
     * zookeeper服务地址
     */
    private String zkAddress;
    /**
     * 任务服务ID 工程名称 英文
     */
    private String schedulerServerId;
    /**
     * 任务服务名称 工程名称 中文
     */
    private String schedulerServerName;

    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
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
}
