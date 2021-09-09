package com.starter.schedule.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 注册配置信息
 * @author frankq
 * @date 2021/9/8
 */
@Configuration("middlware-schedule-starterAutoConfig")
@EnableConfigurationProperties(StarterServiceProperties.class)
public class StarterAutoConfig {

    @Autowired
    private StarterServiceProperties properties;

    public StarterServiceProperties getProperties() {
        return properties;
    }

    public void setProperties(StarterServiceProperties properties) {
        this.properties = properties;
    }

}
