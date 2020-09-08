package com.bestfeng.dydj.manager.queue.delayed;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author bsetfeng
 * @since 1.0
 **/
public class DelayedTask<T extends Runnable> implements Delayed {

    private final long delayedTime;

    @Getter
    private final T task; // 任务类，

    public DelayedTask(T task, long delayedTime, TimeUnit timeUnit) {
        this.delayedTime = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delayedTime, timeUnit);
        this.task = task;
    }


    @Override
    public long getDelay(@NotNull TimeUnit unit) {
        return unit.convert(this.delayedTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    @SuppressWarnings("all")
    public int compareTo(@NotNull Delayed o) {
        if (o instanceof DelayedTask) {
            DelayedTask other = (DelayedTask) o;
            return Long.compare(this.delayedTime, other.delayedTime);
        }
        throw new ClassCastException("不支持的延迟任务类型：" + o.getClass().toString());
    }

    @Override
    public int hashCode() {
        return task.hashCode();
    }

}
