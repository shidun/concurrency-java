package com.mmall.concurrency.example.threadLocal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/threadLocal")
public class ThreadLocalController {

    @RequestMapping("/index")
    public Long index() {
        return RequestHodler.getId();
    }
}
