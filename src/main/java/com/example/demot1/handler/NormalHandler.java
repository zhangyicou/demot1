package com.example.demot1.handler;

import com.example.demot1.annotation.HandlerType;
import com.example.demot1.domain.OrderDTO;
import org.springframework.stereotype.Service;

/**
 * @Author: yichu.zhang
 * @Date: 2019-10-31 18:50
 */
@Service("normalHandler")
@HandlerType("1")
public class NormalHandler extends AbstractHandler {
    @Override
    public String handle(OrderDTO order) {
        return "处理普通订单";
    }
}
