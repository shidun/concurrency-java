package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

@Slf4j
@ThreadSafe
public class AutomicExample5 {

    /**
     * 指定更新某个类的字段 该字段必须是 volatile 修饰 非 static
     */
    private static AtomicIntegerFieldUpdater<AutomicExample5> updater =
            AtomicIntegerFieldUpdater.newUpdater(AutomicExample5.class, "count");

    @Getter
    public volatile int count = 100;

//    private static AutomicExample5 example5 = new AutomicExample5();

    public static void main(String[] args) {

        AutomicExample5 example5 = new AutomicExample5();
        if (updater.compareAndSet(example5, 100, 120)) {
            log.info("update success1, {}", example5.getCount());
        }
        if (updater.compareAndSet(example5, 100, 120)) {
            log.info("update success2, {}", example5.getCount());
        } else {
            log.info("update failed, {}", example5.getCount());
        }
    }
}
