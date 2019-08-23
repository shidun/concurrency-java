package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annoations.NotThreadSafe;

/**
 * 饿汉模式
 * 单例的实例在第一次使用的时候创建
 */
@NotThreadSafe
public class SingletonExample2 {

    /**
     * 私有构造函数
     */
    private SingletonExample2() {

    }
    /**
     * 单例对象
     */
    private static SingletonExample2 instance = new SingletonExample2();

    /**
     * 静态的工厂方法
     * 多线程并发时会出现 new 多次实例
     * @return
     */
    private static SingletonExample2 getInstance() {
        return instance;
    }
}
