package com.mmall.concurrency.example.lock;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock使得多个读线程同时持有读锁（只要写锁未被占用），而写锁是独占的。
 *
 * 只要有写锁 读就一直会饥饿状态
 *
 * 来看下readLock方法：
 * 由于ThreadA此时持有写锁，所以ThreadB获取读锁失败，将调用acquireRead方法，加入等待队列
 * 一个是读操作相关的锁，称为共享锁；一个是写相关的锁，称为排他锁
 * 线程进入读锁的前提条件：
 *
     * 没有其他线程的写锁，
     *
     * 没有写请求或者有写请求，但调用线程和持有锁的线程是同一个。
 *
 * 线程进入写锁的前提条件：
 *
     * 没有其他线程的读锁
     *
     * 没有其他线程的写锁
 *
 *
 */
@Slf4j
public class LockExample3 {

    private final Map<String ,Data> map = new TreeMap<>();

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock readLock = lock.readLock();

    private final Lock writerLock = lock.writeLock();

    public static void main(String[] args) throws Exception{
        //创建线程池

    }

    public Data get(String key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public Set<String> getAllKeys() {
        readLock.lock();
        try {
            return map.keySet();
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 写入锁时  不允许有读锁存在  读比较多会造成写锁饥饿
     */
    public Data put(String ket, Data value) {
        writerLock.lock(); //
        try {
            return map.put(ket, value);
        } finally {
            writerLock.unlock();
        }
    }

    class Data {

    }
}
