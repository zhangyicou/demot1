package com.example.demot1.controller;

import com.example.demot1.domain.OrderDTO;
import com.example.demot1.service.OrderService;
import jdk.nashorn.internal.runtime.JSONFunctions;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yichu.zhang
 * @Date: 2019-11-01 15:55
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    private Logger logger = LoggerFactory.getLogger("TRACER");

    @RequestMapping(value = "/put", method = RequestMethod.POST)
    public HttpStatus order(@RequestBody OrderDTO order){

        String result = orderService.handle(order);
        logger.info("TRACK order : {}, result = {}", order,result);

        return HttpStatus.OK;
    }
}
