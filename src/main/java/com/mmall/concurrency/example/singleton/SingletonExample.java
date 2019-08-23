package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annoations.NotRecommend;
import com.mmall.concurrency.annoations.NotThreadSafe;
import com.mmall.concurrency.annoations.ThreadSafe;

/**
 * 懒汉模式
 * 单例的实例在第一次使用的时候创建
 */
@ThreadSafe
@NotRecommend
public class SingletonExample {


    /**
     * 私有构造函数
     */
    private SingletonExample() {

    }
    /**
     * 单例对象
     */
    private static SingletonExample instance = null;
    /**
     * 静态的工厂方法
     * 多线程并发时会出现 new 多次实例
     * synchronized 性能变低 不推荐
     * @return
     */
    private static synchronized SingletonExample getInstance() {
        if (instance == null) {
            instance = new SingletonExample();
        }
        return instance;
    }
}
