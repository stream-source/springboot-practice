package com.itwx.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 方式二：自定义线程池，注入SpringIOC容器中，代替默认线程池ThreadPoolTaskExecutor
 * 使用@Async注解时，需要指定线程池Bean名字，否则还是使用默认线程池，例如@Async("threadPoolTaskExecutor")
 */
@Configuration
@Slf4j
public class ThreadPoolConfig {
    @Value("${asyncThreadPool.corePoolSize:10}")
    private int corePoolSize;

    @Value("${asyncThreadPool.maxPoolSize:20}")
    private int maxPoolSize;

    @Value("${asyncThreadPool.queueCapacity:20}")
    private int queueCapacity;

    @Value("${asyncThreadPool.keepAliveSeconds:3}")
    private int keepAliveSeconds;

    @Value("${asyncThreadPool.awaitTerminationSeconds:5}")
    private int awaitTerminationSeconds;

    @Value("${asyncThreadPool.threadNamePrefix:wxThreadPool-}")
    private String threadNamePrefix;

    /**
     * 线程池配置
     * @param
     * @return java.util.concurrent.Executor
     * @author wliduo
     * @date 2019/2/15 14:44
     */
    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        log.info("---------- 线程池开始加载 ----------");
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程池大小
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        // 最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        // 队列容量----LinkedBlockingQueue 默认使用无边界的阻塞队列   ArrayBlockingQueue 有边界队列
        threadPoolTaskExecutor.setQueueCapacity(keepAliveSeconds);
        // 活跃时间---
        threadPoolTaskExecutor.setKeepAliveSeconds(queueCapacity);
        // 主线程等待子线程执行时间---也有可能是拒绝策略
        threadPoolTaskExecutor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        // 线程名字前缀
        threadPoolTaskExecutor.setThreadNamePrefix(threadNamePrefix);
        // RejectedExecutionHandler:当pool已经达到max-size的时候，如何处理新任务
        // CallerRunsPolicy:不在新线程中执行任务，而是由调用者所在的线程来执行
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        threadPoolTaskExecutor.initialize();
        log.info("---------- 线程池加载完成 ----------");
        return threadPoolTaskExecutor;
    }

}
