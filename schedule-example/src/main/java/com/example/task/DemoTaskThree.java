package com.example.task;

import com.starter.schedule.annotation.DcsScheduled;
import org.springframework.stereotype.Component;

/**
 * @author frankq
 * @date 2021/9/9
 */
@Component("demoTaskThree")
public class DemoTaskThree {

    @DcsScheduled(cron = "0 0 9,13 * * *", desc = "03定时任务执行测试：taskMethod01", autoStartup = false)
    public void taskMethod01() {
        System.out.println("03定时任务执行测试：taskMethod01");
    }

    @DcsScheduled(cron = "0 0/30 8-10 * * *", desc = "03定时任务执行测试：taskMethod02", autoStartup = false)
    public void taskMethod02() {
        System.out.println("03定时任务执行测试：taskMethod02");
    }

}
