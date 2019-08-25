package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 *  FutureTask 获取线程的返回结果
 */
@Slf4j
public class FutureTaskExample {

    public static void main(String[] args) throws Exception{
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("do something in callable");
                Thread.sleep(3000);
                return "success";
            }
        });

        new Thread(futureTask).start();
        log.info("do something in main");
//        Thread.sleep(2000);
        log.info("mian-{}", futureTask.get());
    }
}
