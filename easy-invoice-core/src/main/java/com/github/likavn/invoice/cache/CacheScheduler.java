package com.github.likavn.invoice.cache;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author likavn
 * @date 2023/12/26
 **/
public enum CacheScheduler {
    /**
     * 当前实例
     */
    INSTANCE;

    private AtomicInteger cacheTaskNumber = new AtomicInteger(1);
    private ScheduledExecutorService scheduler;

    CacheScheduler() {
        create();
    }

    private void create() {
        this.shutdown();
        this.scheduler = new ScheduledThreadPoolExecutor(10, r -> new Thread(r, String.format("JustAuth-Task-%s", cacheTaskNumber.getAndIncrement())));
    }

    public void shutdown() {
        if (null != scheduler) {
            this.scheduler.shutdown();
        }
    }

    public void schedule(Runnable task, long delay) {
        this.scheduler.scheduleAtFixedRate(task, delay, delay, TimeUnit.MILLISECONDS);
    }
}
