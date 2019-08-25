package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CyclicBarrierExample1 {

    //多少个线程同步等待
    private static CyclicBarrier barrier = new CyclicBarrier(5);

    public static void main(String[] args) throws Exception{

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            final int threadNul = i;
            executorService.execute(() -> {
                try {
                    race(threadNul);
                } catch (Exception e) {
                    log.info("exception {}", e);
                }
            });
        }
        executorService.shutdown();
    }

    private static void race(int num) throws Exception{
        Thread.sleep(1000);
        log.info("thread - {} - is ready", num);
        try {
            barrier.await(2000, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            log.info("{}", e);
        }
        log.info("{} continue", num);
    }
}
