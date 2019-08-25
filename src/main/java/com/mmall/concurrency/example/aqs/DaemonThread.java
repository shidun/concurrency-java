package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

/**
 * 守护线程和非守护线程
 *  主线程就是Java中的main方法 gc线程是指Java垃圾回收中的线程  这2个是守护线程
 *  用户线程是指用户自定义的线程   是非守护进程
 *  当main方法结束的时候 不会停止非守护进程的执行
 *  当主线程结束时，结束其余的子线程（守护线程）自动关闭
 */
@Slf4j
public class DaemonThread {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 90; i++) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("用户线程：{}", i);
            }
        });
        thread.setDaemon(true);
        thread.start();

        for (int i = 0; i < 5; i++) {
            log.info("主线程：{}", i);
        }
        log.info("success");
    }
}
