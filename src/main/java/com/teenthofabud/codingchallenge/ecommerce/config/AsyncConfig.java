package com.teenthofabud.codingchallenge.ecommerce.config;

import com.teenthofabud.codingchallenge.ecommerce.exception.AsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    private final String applicationName;

    public AsyncConfig(@Value("${spring.application.name}") String applicationName) {
        this.applicationName = applicationName;
    }

    @Bean
    public ApplicationEventMulticaster applicationEventMultiCaster(Executor taskExecutor) {
        ThreadPoolTaskExecutor executor = executor();
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor(executor);
        simpleAsyncTaskExecutor.setVirtualThreads(true);
        SimpleApplicationEventMulticaster eventMultiCaster = new SimpleApplicationEventMulticaster();
        eventMultiCaster.setTaskExecutor(simpleAsyncTaskExecutor);
        return eventMultiCaster;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

    @Override
    public Executor getAsyncExecutor() {
        return executor();
    }

    private ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix(String.format("%s-", applicationName));
        executor.initialize();
        return executor;
    }

}
