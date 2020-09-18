package com.bestfeng.dydj.manager.queue.delayed;

import com.bestfeng.dydj.delay.task.OrderUnPayDelayTask;
import com.bestfeng.dydj.mbg.mapper.NoteOrderMapper;
import com.bestfeng.dydj.mbg.model.NoteOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
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
    @Autowired
    private NoteOrderMapper orderMapper;
    @Autowired
    private OrderUnPayDelayTask task;

    // 延时队列
    private DelayQueue<DelayedTask<?>> delayQueue = new DelayQueue<>();

    /**
     * 初始化、
     */
    public void init() {
        // 守护线程
        Thread daemonThread = new Thread(this::execute);
        daemonThread.setName("DelayQueueMonitor");
        daemonThread.start();
        // 历史任务
        this.orderTask();
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
    public DelayedTask<?> put(Runnable task, long delayedTime, TimeUnit unit) {
        DelayedTask<?> delayedTask = new DelayedTask<>(task, delayedTime, unit);
        delayQueue.put(delayedTask);
        return delayedTask;
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

    /**
     * 查询 未支付 未处理 的订单任务
     * @return
     */
    private void orderTask(){
        List<NoteOrder> noteOrders = orderMapper.selectListNoteOrder();
        log.info("初始化 未支付订单处理任务 noteOrderList={}", noteOrders);
        Optional.ofNullable(noteOrders).ifPresent(o->{
            o.stream().forEach(order -> {
                task.setOrderNo(order.getOrderid());
                //订单创建时间
                Integer createTime =order.getCreatetime();
                Integer metaExpireTime = createTime + 5*60;
                //当前时间
                Integer curTime = (int)(System.currentTimeMillis()/1000L);
                //时间差
                Integer timeDiff = metaExpireTime-curTime;
                if(timeDiff>0){
                    this.put(task,timeDiff,TimeUnit.SECONDS);
                }else {
                    executorService.execute(task);
                    //已经超过5分钟的，增加任务时长为30s执行
                    this.put(task,30,TimeUnit.SECONDS);
                }
            });
        });

    }
}
