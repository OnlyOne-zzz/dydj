package com.bestfeng.dydj.manager.queue.delayed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Component
@Slf4j
public class DelayQueueManager implements CommandLineRunner {

    @Autowired
    private ExecutorService executorService;

    // 延时队列
    private DelayQueue<DelayedTask<?>> delayQueue = new DelayQueue<>();


    public DelayQueueManager() {
        executorService = Executors.newFixedThreadPool(16);
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        // 守护线程
        Thread daemonThread = new Thread(this::execute);
        daemonThread.setName("DelayQueueMonitor");
        daemonThread.start();
    }

    private void execute() {
        for (; ; ) {
            int taskNum = delayQueue.size();
            log.info("当前延时任务数量:{}", taskNum);
            try {
                // 从延时队列中获取任务
                DelayedTask<?> delayOrderTask = delayQueue.take();
                Runnable task = delayOrderTask.getTask();
                if (null == task) {
                    continue;
                }
                // 提交到线程池执行task
                executorService.execute(task);
            } catch (Exception e) {
                log.error("延迟队列管理器异常", e);
            }
        }
    }

    /**
     * 添加任务
     *
     * @param task
     * @param delayedTime 延时时间
     * @param unit        时间单位
     */
    public void put(Runnable task, long delayedTime, TimeUnit unit) {
        delayQueue.put(new DelayedTask<>(task, delayedTime, unit));
    }

    /**
     * 删除任务
     *
     * @param task
     * @return
     */
    public boolean removeTask(DelayedTask<?> task) {
        return delayQueue.remove(task);
    }


    @Override
    public void run(String... args) throws Exception {
        init();
    }
}
