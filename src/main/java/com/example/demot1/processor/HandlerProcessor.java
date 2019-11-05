package com.example.demot1.processor;

import com.example.demot1.annotation.HandlerType;
import com.example.demot1.context.HandlerContext;
import com.example.demot1.handler.AbstractHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yichu.zhang
 * @Date: 2019-10-31 18:39
 */
@Component
public class HandlerProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        //Map<String, Class> handlerMap = Maps.newLinkedHashMapWithExpectedSize(3);

        Map<String, Object> contextHandlerMap = new HashMap<>();
        Map<String, Object> handerMap = configurableListableBeanFactory.getBeansWithAnnotation(HandlerType.class);
        handerMap.entrySet().stream().forEach(entry ->
            contextHandlerMap.put(entry.getValue().getClass().getAnnotation(HandlerType.class).value(), entry.getValue())

        );
        HandlerContext context = new HandlerContext(contextHandlerMap);
        configurableListableBeanFactory.registerSingleton(HandlerContext.class.getName(), context);
    }
}
