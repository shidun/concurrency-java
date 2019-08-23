package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annoations.NotThreadSafe;

/**
 * 饿汉模式
 * 单例的实例在第一次使用的时候创建
 */
@NotThreadSafe
public class SingletonExample6 {

    /**
     * 私有构造函数
     */
    private SingletonExample6() {

    }

    /**
     * 单例对象
     */
    private static SingletonExample6 instance = null;

    static {
        instance = new SingletonExample6();
    }

    /**
     * 静态的工厂方法
     * 多线程并发时会出现 new 多次实例
     * @return
     */
    private static SingletonExample6 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(getInstance().hashCode());
        System.out.println(getInstance().hashCode());
    }
}
