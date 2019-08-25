package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CountDownLatchExample1 {

    private final static int threadCount = 200;

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        log.info("11111111111");
        for (int i=0; i < threadCount; i++) {
            final int threadNum = i;
//            Thread.sleep(3);  //等待时间无效
            executorService.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    log.info("exception;;..{}", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        log.info("2222222222");
        //等待时间是线程里面执行的时间  for循环的等待时间不算
        countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        log.info("finished");
        executorService.shutdown();// 执行完关闭线程池
//        executorService.shutdownNow();//立即关闭线程池
        log.info("cloesed");
    }

    private static void test(int threadNum) throws Exception{
        Thread.sleep(100);
        log.info("{}", threadNum);
//        Thread.sleep(100);
    }
}
