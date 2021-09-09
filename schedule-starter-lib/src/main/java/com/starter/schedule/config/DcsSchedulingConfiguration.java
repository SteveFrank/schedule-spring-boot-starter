package com.starter.schedule.config;

import com.alibaba.fastjson.JSON;
import com.starter.schedule.annotation.DcsScheduled;
import com.starter.schedule.common.Constants;
import com.starter.schedule.domain.ExecOrder;
import com.starter.schedule.service.HeartbeatService;
import com.starter.schedule.service.ZkCuratorServer;
import com.starter.schedule.task.CronTaskRegister;
import com.starter.schedule.task.SchedulingRunnable;
import com.starter.schedule.util.StrUtil;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.starter.schedule.common.Constants.Global.*;

/**
 * @author frankq
 * @date 2021/9/8
 */
@Component
public class DcsSchedulingConfiguration implements ApplicationContextAware, BeanPostProcessor, ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG
            = LoggerFactory.getLogger(DcsSchedulingConfiguration.class);

    private static final Set<Class<?>> nonAnnotatedClasses
            = Collections.newSetFromMap(new ConcurrentHashMap<>(64));

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Constants.Global.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
        if (nonAnnotatedClasses.contains(targetClass)) {
            return bean;
        }
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        for (Method method : methods) {
            DcsScheduled dcsScheduled = AnnotationUtils.findAnnotation(method, DcsScheduled.class);
            if (null == dcsScheduled || 0 == method.getDeclaredAnnotations().length) {
                continue;
            }
            List<ExecOrder> execOrderList = Constants.execOrderMap.computeIfAbsent(beanName, k -> new ArrayList<>());
            ExecOrder execOrder = new ExecOrder();
            execOrder.setBean(bean);
            execOrder.setBeanName(beanName);
            execOrder.setMethodName(method.getName());
            execOrder.setDesc(dcsScheduled.desc());
            execOrder.setCron(dcsScheduled.cron());
            execOrder.setAutoStartup(dcsScheduled.autoStartup());
            execOrderList.add(execOrder);
            nonAnnotatedClasses.add(targetClass);
        }
        return bean;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
            // 1、初始化各项配置
            this.initConfig(applicationContext);
            // 2、初始化服务
            this.initServer(applicationContext);
            // 3、启动任务
            this.initTask(applicationContext);
            // 4、挂载节点
            this.initNode();
            // 5、心跳监听
            HeartbeatService.getInstance().startFlushScheduleStatus();
            LOG.info("middleware schedule init config server task node heartbeat done!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 1、初始化配置
     */
    private void initConfig(ApplicationContext applicationContext) {
        try {
            StarterServiceProperties properties = applicationContext
                    .getBean("middlware-schedule-starterAutoConfig", StarterAutoConfig.class)
                    .getProperties();
            Constants.Global.zkAddress = properties.getZkAddress();
            Constants.Global.schedulerServerId = properties.getSchedulerServerId();
            Constants.Global.schedulerServerName = properties.getSchedulerServerName();
            InetAddress id = InetAddress.getLocalHost();
            Constants.Global.ip = id.getHostAddress();
        } catch (UnknownHostException e) {
            LOG.error("middleware schedule init config error!", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 2、初始化服务启动
     */
    private void initServer(ApplicationContext applicationContext) {
        try {
            // 获取zk连接
            CuratorFramework client = ZkCuratorServer.getClient(Constants.Global.zkAddress);
            // 组装zk节点
            Constants.Global.path_root_server = StrUtil.joinStr(
                    Constants.Global.path_root, LINE, "server", LINE, schedulerServerId);
            Constants.Global.path_root_server_ip = StrUtil.joinStr(
                    Constants.Global.path_root_server, LINE, "ip", LINE, schedulerServerId);
            // 创建节点 & 递归删除本服务下的 IP 旧内容
            ZkCuratorServer.deletingChildrenIfNeeded(client, path_root_server_ip);
            ZkCuratorServer.createNode(client, path_root_server_ip);
            ZkCuratorServer.setData(client, path_root_server, schedulerServerName);
            // 添加节点 & 监听
            ZkCuratorServer.createNodeSimple(client, path_root_exec);
            ZkCuratorServer.addTreeCacheListener(applicationContext, client, path_root_exec);
        } catch (Exception e) {
            LOG.error("middleware schedule init server error！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 3、启动任务
     */
    private void initTask(ApplicationContext applicationContext) {
        CronTaskRegister cronTaskRegister = applicationContext.getBean("middlware-schedule-cronTaskRegister", CronTaskRegister.class);
        Set<String> beanNames = Constants.execOrderMap.keySet();
        for (String beanName : beanNames) {
            List<ExecOrder> execOrderList = Constants.execOrderMap.get(beanName);
            for (ExecOrder execOrder : execOrderList) {
                if (!execOrder.getAutoStartup()) {
                    continue;
                }
                SchedulingRunnable task = new SchedulingRunnable(execOrder.getBean(), execOrder.getBeanName(), execOrder.getMethodName());
                cronTaskRegister.addCronTask(task, execOrder.getCron());
            }
        }
    }

    /**
     * 4. 挂载节点
     */
    private void initNode() throws Exception {
        Set<String> beanNames = Constants.execOrderMap.keySet();
        for (String beanName : beanNames) {
            List<ExecOrder> execOrderList = Constants.execOrderMap.get(beanName);
            for (ExecOrder execOrder : execOrderList) {
                String path_root_server_ip_clazz = StrUtil.joinStr(path_root_server_ip, LINE, "clazz", LINE, execOrder.getBeanName());
                String path_root_server_ip_clazz_method = StrUtil.joinStr(path_root_server_ip_clazz, LINE, "method", LINE, execOrder.getMethodName());
                String path_root_server_ip_clazz_method_status = StrUtil.joinStr(path_root_server_ip_clazz, LINE, "method", LINE, execOrder.getMethodName(), "/status");
                //添加节点
                ZkCuratorServer.createNodeSimple(client, path_root_server_ip_clazz);
                ZkCuratorServer.createNodeSimple(client, path_root_server_ip_clazz_method);
                ZkCuratorServer.createNodeSimple(client, path_root_server_ip_clazz_method_status);
                //添加节点数据[临时]
                ZkCuratorServer.appendPersistentData(client, path_root_server_ip_clazz_method + "/value", JSON.toJSONString(execOrder));
                //添加节点数据[永久]
                ZkCuratorServer.setData(client, path_root_server_ip_clazz_method_status, execOrder.getAutoStartup() ? "1" : "0");
            }
        }
    }

}
