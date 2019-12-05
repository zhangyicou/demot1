package com.example.demot1.listener;

import com.example.demot1.config.ServerConfig;
import com.example.demot1.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yichu.zhang
 * @Date: 2019-11-22 16:16
 */
@Slf4j
@Component
public class OkcoinEventListener {
    @Autowired
    private ServerConfig config;

    @Async
    @EventListener
    public void listOrderEvent(OrderEvent event){
       log.info("event receive : {}", event.toString());
        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("event end : {}", event.toString());
    }

    @PreDestroy
    public void listPreDestroyEvent(){
        log.info("application context PreDestroy event ... port = {}", config.getPort());
    }

    /**
     * 你为什么不执行?
     * Spring2.5新增的事件，当容器调用ConfigurableApplicationContext的Stop()方法停止容器时触发该事件。
     * @param event
     */
    @EventListener
    public void listStoppedEvent(ContextStoppedEvent event){
        log.info("application context stopped event ... port = {}", config.getPort());
    }

    /**
     * 当ApplicationContext被关闭时触发该事件。容器被关闭时，其管理的所有单例Bean都被销毁。
     * @param event
     */
    @EventListener
    public void listClosedEvent(ContextClosedEvent event){
        log.info("application context closed event ... port = {}", config.getPort());
    }


}
