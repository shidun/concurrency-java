package com.mmall.concurrency.example.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@Slf4j
public class RedisConfig {

//    @Bean(name = "redisPool")
//    public JedisPool jedisPool(@Value("${jedis.host}") String host,
//                               @Value("${jedis.port}") int port) {
//        return new JedisPool(host, port);
//
//    }

    @Value("${jedis.host}")
    private String host;
    @Value("${jedis.port}")
    private int port;
    @Value("${jedis.password}")
    private String password;
    @Value("${jedis.timeout}")
    private int timeout;
    @Value("${jedis.database}")
    private int database;

    @Bean(name = "JedisPool")
//    @Bean  //不给名字 默认 name = "JedisPool"
    public JedisPool redisPoolFactory()  throws Exception{
        log.info("JedisPool注入成功！！");
        log.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        return new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
    }
}
