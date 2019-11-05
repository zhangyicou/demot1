package com.example.demot1.context;

import java.util.Map;
import java.util.Objects;

/**
 * @Author: yichu.zhang
 * @Date: 2019-10-31 18:19
 */

public class HandlerContext {
    private Map<String, Object> handlerMap;

    public HandlerContext(Map<String, Object> handlerMap){
        this.handlerMap = handlerMap;
    }

    public Object getInstance(String type){
        Object clazz = handlerMap.get(type);
        if(Objects.isNull(clazz)){
            throw new IllegalArgumentException("not found handler for type : " + type);
        }

        return clazz;
    }
}
