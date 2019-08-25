package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SemaphoreExample2 {

    private final static int threadCount = 200;

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(6);
        log.info("11111111111");
        for (int i=0; i < threadCount; i++) {
            final int threadNum = i;
//            Thread.sleep(3);  //等待时间无效
            executorService.execute(() -> {
                try {
                    if (semaphore.tryAcquire(1550, TimeUnit.MILLISECONDS)) { //尝试获取一个许可证
                        test(threadNum);
                        semaphore.release();
                    }
                } catch (Exception e) {
                    log.info("exception;;..{}", e);
                }
            });
        }
        log.info("2222222222");
        //等待时间是线程里面执行的时间  for循环的等待时间不算
        log.info("finished");
        executorService.shutdown();// 执行完关闭线程池
//        executorService.shutdownNow();//立即关闭线程池
        log.info("cloesed");
    }

    private static void test(int threadNum) throws Exception{
        Thread.sleep(500);
        log.info("{}", threadNum);
//        Thread.sleep(100);
    }
}
