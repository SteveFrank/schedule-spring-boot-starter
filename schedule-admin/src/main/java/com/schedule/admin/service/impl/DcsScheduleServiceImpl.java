package com.schedule.admin.service.impl;

import com.schedule.admin.service.DcsScheduleService;
import com.starter.schedule.domain.DataCollect;
import com.starter.schedule.domain.DcsScheduleInfo;
import com.starter.schedule.domain.DcsServerNode;
import com.starter.schedule.domain.Instruct;
import com.starter.schedule.export.DcsScheduleResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author frankq
 * @date 2021/9/9
 */
@Service("dcsScheduleService")
public class DcsScheduleServiceImpl implements DcsScheduleService {

    @Value("${middleware.schedule.zkAddress}")
    private String zkAddress;

    private DcsScheduleResource dcsScheduleResource = null;

    @Override
    public List<String> queryPathRootServerList() throws Exception {
        return getDcsScheduleResource().queryPathRootServerList();
    }

    @Override
    public List<DcsScheduleInfo> queryDcsScheduleInfoList(String schedulerServerId) throws Exception {
        return getDcsScheduleResource().queryDcsScheduleInfoList(schedulerServerId);
    }

    @Override
    public void pushInstruct(Instruct instruct) throws Exception {
        getDcsScheduleResource().pushInstruct(instruct);
    }

    @Override
    public DataCollect queryDataCollect() throws Exception {
        return getDcsScheduleResource().queryDataCollect();
    }

    @Override
    public List<DcsServerNode> queryDcsServerNodeList() throws Exception {
        return getDcsScheduleResource().queryDcsServerNodeList();
    }

    private DcsScheduleResource getDcsScheduleResource() {
        if (null == dcsScheduleResource) {
            dcsScheduleResource = new DcsScheduleResource(zkAddress);
        }
        return dcsScheduleResource;
    }

}
