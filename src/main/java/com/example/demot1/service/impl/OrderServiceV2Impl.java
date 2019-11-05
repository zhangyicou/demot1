package com.example.demot1.service.impl;

import com.example.demot1.context.HandlerContext;
import com.example.demot1.domain.OrderDTO;
import com.example.demot1.handler.AbstractHandler;
import com.example.demot1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yichu.zhang
 * @Date: 2019-10-31 18:18
 */
@Service
public class OrderServiceV2Impl implements OrderService {

    @Autowired
    private HandlerContext context;

    @Override
    public String handle(OrderDTO order) {
        AbstractHandler handler = (AbstractHandler)context.getInstance(order.getType());

        return handler.handle(order);
    }
}
