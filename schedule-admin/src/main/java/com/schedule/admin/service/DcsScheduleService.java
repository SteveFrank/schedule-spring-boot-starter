package com.schedule.admin.service;

import com.starter.schedule.domain.DataCollect;
import com.starter.schedule.domain.DcsScheduleInfo;
import com.starter.schedule.domain.DcsServerNode;
import com.starter.schedule.domain.Instruct;

import java.util.List;

/**
 * @author frankq
 * @date 2021/9/9
 */

public interface DcsScheduleService {

    List<String> queryPathRootServerList() throws Exception;

    List<DcsScheduleInfo> queryDcsScheduleInfoList(String schedulerServerId) throws Exception;

    void pushInstruct(Instruct instruct) throws Exception;

    DataCollect queryDataCollect() throws Exception;

    List<DcsServerNode> queryDcsServerNodeList() throws Exception;

}
