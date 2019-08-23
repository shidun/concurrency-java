package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annoations.NotRecommend;
import com.mmall.concurrency.annoations.NotThreadSafe;
import com.mmall.concurrency.annoations.ThreadSafe;

/**
 * 懒汉模式 => 双重同步锁单例模式
 * 单例的实例在第一次使用的时候创建
 */
@NotThreadSafe
@NotRecommend
public class SingletonExample4 {

    /**
     * 私有构造函数
     */
    private SingletonExample4() {

    }
    /**
     * 单例对象
     */
    private static SingletonExample4 instance = null;

    //1. memory = allocate() 分配对象的内存空间
    //2.ctorInstance() 初始化对象
    //3.instance = memory 设置instance指向刚分配的内存

    //JVM和cpu优化，发生了指令重排
    //1. memory = allocate() 分配对象的内存空间
    //3.instance = memory 设置instance指向刚分配的内存
    //2.ctorInstance() 初始化对象

    /**
     * 静态的工厂方法
     * 多线程并发时会出现 new 多次实例
     * 类加 synchronized
     * @return
     */
    private static SingletonExample4 getInstance() {
        if (instance == null) {
            synchronized (SingletonExample.class) { //同步锁
                if (instance == null) {
                    instance = new SingletonExample4();
                }
            }
        }
        return instance;
    }
}
