package com.java4all.scalog.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The name thread factory
 *
 * @author wangzhongxiang
 */
public class NameThreadFactory implements ThreadFactory {
    private final ThreadGroup group;
    private final AtomicInteger index = new AtomicInteger(1);

    public NameThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = s !=null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(group, r, "Java4all-Thread-LogInfo-" + index.getAndIncrement());
        thread.setDaemon(true);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
