package com.starter.schedule.task;

import java.util.concurrent.ScheduledFuture;

/**
 * @author frankq
 * @date 2021/9/9
 */
public class ScheduledTask {

    volatile ScheduledFuture<?> future;

    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future == null) {
            return;
        }
        future.cancel(true);
    }

    public boolean isCancelled() {
        ScheduledFuture<?> future = this.future;
        if (future == null) {
            return true;
        }
        return future.isCancelled();
    }

}
