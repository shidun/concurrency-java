package com.mmall.concurrency.example.threadLocal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestHodler {

    private final static ThreadLocal<Long> requestHodler = new ThreadLocal<>();

    public static void add(Long id) {
        requestHodler.set(id);
    }

    public static long getId() {
        return requestHodler.get();
    }

    public static void remove() {
        requestHodler.remove();
        log.info("remove ThreadLocal");
    }
}
