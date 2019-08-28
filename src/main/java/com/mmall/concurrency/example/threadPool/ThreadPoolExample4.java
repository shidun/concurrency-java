package com.mmall.concurrency.example.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPoolExample4 {

    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

        log.info("1111111");
//        executorService.schedule(new Runnable() {
//            @Override
//            public void run() {
//                log.info("Scheduled");
//            }
//        }, 3000, TimeUnit.MILLISECONDS);

//        executorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                log.info("scheduleAtFixedRate");
//            }
//        }, 1, 3, TimeUnit.SECONDS); //延迟1秒，每隔3秒去执行里面的任务
//        executorService.shutdown();

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("time run");
            }
        }, new Date(), 5*1000);
    }
}
