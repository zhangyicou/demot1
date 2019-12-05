package com.example.demot1.config;

import com.example.demot1.event.OrderEvent;
import lombok.Getter;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @Author: yichu.zhang
 * @Date: 2019-09-29 14:42
 */
@Component
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    @Getter
    private int port;
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port = event.getWebServer().getPort();
    }

//    @Bean
//    public SimpleApplicationEventMulticaster simpleApplicationEventMulticaster(){
//        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
//        eventMulticaster.setTaskExecutor(createthreadPoolTaskExecutor());
//        return eventMulticaster;
//    }
//    @Bean
//    public ThreadPoolTaskExecutor createthreadPoolTaskExecutor(){
//        /**
//         * <property name="corePoolSize" value="10"/><!-- 核心线程数 -->
//         * <property name="maxPoolSize" value="20"/><!-- 最大线程数 -->
//         * <property name="queueCapacity" value="25"/><!-- 队列最大长度 -->
//         * <property name="threadNamePrefixSet" value="weiqiao-taskExecutor"/><!-- 线程名前缀集主要用来打印日志区分方便  -->
//         */
//        ThreadPoolTaskExecutor coreTaskExecutor = new ThreadPoolTaskExecutor();
//        coreTaskExecutor.setCorePoolSize(10);
//        coreTaskExecutor.setMaxPoolSize(20);
//        coreTaskExecutor.setQueueCapacity(25);
//        //coreTaskExecutor.setThreadNamePrefix();
//
//        return coreTaskExecutor;
//    }
}
