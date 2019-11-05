package com.example.demot1.handler;

import com.example.demot1.domain.OrderDTO;

/**
 * @Author: yichu.zhang
 * @Date: 2019-10-31 18:27
 */
public abstract class AbstractHandler {
    abstract public String handle(OrderDTO order);
}
