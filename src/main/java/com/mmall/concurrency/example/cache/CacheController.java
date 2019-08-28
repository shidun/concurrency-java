package com.mmall.concurrency.example.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private RedisClient redisClient;

    @RequestMapping("/set")
    public String set(@RequestParam("key") String key,
                      @RequestParam("value") String value) throws Exception{
        redisClient.set(key, value, 20);
        return "success";
    }

    @RequestMapping("/get")
    public String get(@RequestParam("key") String key) throws Exception{
        return redisClient.get(key);
    }
}
