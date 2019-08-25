package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;

@Slf4j
public class TimerExample{

    private static Timer timer=new Timer(true);

    public static void main(String[] args) throws InterruptedException
    {
        Runnable tr=new TestRunnable();
        Thread thread=new Thread(tr);
        thread.setDaemon(true); //设置守护线程
        thread.start(); //开始执行分进程
        Thread.sleep(3);
    }

   static class TestRunnable implements Runnable{
        public void run(){
            try{
//                Thread.sleep(1000);//守护线程阻塞1秒后运行
                log.info("1222222");
            }  catch(Exception e1){
                e1.printStackTrace();
            }
        }
    }
}
