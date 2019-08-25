package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class FutureExample {


    private static Timer timer=new Timer(true);

    static class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            log.info("do something in callable");
            Thread.sleep(3000);
            return "success";
        }
    }

    public static void main(String[] args) throws Exception{

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(new MyCallable());
        log.info("do main");
//        Thread.sleep(1000);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("thread running");
            }
        });
        Integer a = 100;
        Future<Integer> future1 =  executorService.submit(thread, a);
        log.info("end {}", future1.get());

        log.info("success main:_{}", future.get()); //线程阻塞了 需要线程执行完才能继续下去
        log.info("111111111");
        executorService.shutdown();
    }
}
