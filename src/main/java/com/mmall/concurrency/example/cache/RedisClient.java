package com.mmall.concurrency.example.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * 对 redis  string 的get和set
 */
@Component
public class RedisClient {

    @Resource(name = "JedisPool")
//    @Resource  //不给name 默认byType =》 JedisPool
//    @Autowired
    private JedisPool jedisPool;

    public void set(String key, String value)  throws Exception{
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public void set(String key, String value, int expireSecond) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
//            jedis.set(key, value);
//            jedis.expire(key, 1000);

            jedis.setex(key, expireSecond, value);

//            boolean keyExist = jedis.exists(key);
//            // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
//            if (keyExist) {
//                jedis.del(key);
//            }
//            jedis.set(key, value, "NX", "EX", expireSecond);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public String get(String key)  throws Exception{
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
