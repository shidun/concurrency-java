package com.mmall.concurrency.example.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

/**
 * 结合Condition实现等待通知机制
 * Condition接口在使用前必须先调用ReentrantLock的lock()方法获得锁。
 * 之后调用Condition接口的await()将释放锁,并且在该Condition上等待,
 * 直到有其他线程调用Condition的signal()方法唤醒线程。使用方式和wait,notify类似。
 *
 */
@Slf4j
public class LockExample6 {

    public static void main(String[] args) throws Exception{
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        new Thread(() -> {
           try {
               reentrantLock.lock();
               log.info(" wait ");
               condition.await(); //对应操作是锁的释放 加入到 condition 队列   线程2 condition.signalAll();后被唤醒继续执行
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           log.info("get signal");
           reentrantLock.unlock();
        }).start();

        new Thread(() -> {
            reentrantLock.lock(); //加入到aqs等待队列中
            log.info(" get lock");
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            condition.signalAll();
            log.info("send signal ");
            reentrantLock.unlock();
        }).start();
    }

}
