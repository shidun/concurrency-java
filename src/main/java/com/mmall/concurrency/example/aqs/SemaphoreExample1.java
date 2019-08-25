package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class SemaphoreExample1 {

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
                    semaphore.acquire(3); //获取多个许可证
                    test(threadNum);
                    semaphore.release(3); //释放多个许可证
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
        log.info("{}", threadNum);
        Thread.sleep(500);
//        Thread.sleep(100);
    }
}
