package com.mmall.concurrency.example.concurrent;

import com.mmall.concurrency.annoations.NotThreadSafe;
import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * CopyOnWriteArrayList 线程安全
 */
@Slf4j
@ThreadSafe
public class CopyOnWriteArrayListExample {
    /**
     * 请求总数
     */
    public static int clientTotal = 5000;
    /**
     * 同时并发执行的线程数
     */
    public static int threadTotal = 200;

    public static List<Integer> list = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws Exception{
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //定义信号量
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i< clientTotal; i++) {
            final int count2 = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update(count2);
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception:", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("size:{}", list.size());
    }

    private static void update(int i) {
       list.add(i);
    }
}
