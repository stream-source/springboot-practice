package com.itwx.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async("threadPoolTaskExecutor")
    public void configTest() {
        System.out.println(Thread.currentThread().getName());
    }

}
