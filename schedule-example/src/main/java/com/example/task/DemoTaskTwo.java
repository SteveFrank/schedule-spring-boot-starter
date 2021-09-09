package com.example.task;

import com.starter.schedule.annotation.DcsScheduled;
import org.springframework.stereotype.Component;

/**
 * @author frankq
 * @date 2021/9/9
 */
@Component("demoTaskTwo")
public class DemoTaskTwo {

    @DcsScheduled(cron = "0 * * * * *", desc = "02定时任务执行测试：taskMethod01")
    public void taskMethod01() {
        System.out.println("02定时任务执行测试：taskMethod01");
    }

    @DcsScheduled(cron = "*/10 * * * * *", desc = "02定时任务执行测试：taskMethod02")
    public void taskMethod02() {
        System.out.println("02定时任务执行测试：taskMethod02");
    }

}
