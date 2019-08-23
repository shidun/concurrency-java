package com.mmall.concurrency.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedExample1 {

    //修饰一个代码块  同步代码块只作用于当前对象  2个不同对象同时访问不会锁住
    public void test1(int j) {
//        for (int i = 0; i< 10; i++) {
//            log.info("test1 - {}", i);
//        }
        synchronized (this) {
            for (int i = 0; i< 10; i++) {
                log.info("test1 {} - {}", j, i);
            }
        }
    }

    /**
     * 如果子类继承该类  子类的test2方法是没有 synchronized
     * @param j
     */
    //修饰一个方法 同步方法只作用于当前对象  2个不同对象同时访问不会锁住
    public synchronized void test2(int j) {
        for (int i = 0; i< 10; i++) {
            log.info("test2 {} - {}", j, i);
        }
    }

    //线程的启动顺序是cpu决定的  下面2个启动顺序会随机
    public static void main(String[] args) {
        SynchronizedExample1 example1 = new SynchronizedExample1();
        SynchronizedExample1 example2 = new SynchronizedExample1();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            example1.test2(1);
        });
        executorService.execute(() -> {
            example2.test2(2);
        });
    }
}
