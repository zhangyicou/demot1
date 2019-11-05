package com.example.demot1.config;

import lombok.Getter;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
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
}
