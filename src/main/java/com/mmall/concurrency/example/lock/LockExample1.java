package com.mmall.concurrency.example.lock;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock和synchronized都是可重入的.
 * ReentrantLock和synchronized都是独占锁,只允许线程互斥的访问临界区
 * synchronized 在 jvm 实现
 *
 * 公平锁和非公平锁该如何选择
 * 大部分情况下我们使用非公平锁，因为其性能比公平锁好很多。但是公平锁能够避免线程饥饿，某些情况下也很有用。
 *
 * synchronized 是非公平锁  无法设置公平锁
 * ReentrantLock 默认是非公平锁，但是可以设置为公平锁
 *
 *
 * ReentrantLock  更好的解决死锁  tryLock()
 *
 * ReentrantLock  结合Condition实现等待通知机制
 */
@Slf4j
@ThreadSafe
public class LockExample1 {

    /**
     * 请求总数
     */
    public static int clientTotal = 5000;
    /**
     * 同时并发执行的线程数
     */
    public static int threadTotal = 200;
    public static int count = 0;

    private final static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws Exception{
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //定义信号量
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i< clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception:", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}", count);

    }

    private  static void add() {
        lock.lock();
        try {
            count++; //先增加再返回
        } finally {
            lock.unlock();
        }
    }
}
