package com.example.demot1.domain;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Author: yichu.zhang
 * @Date: 2019-10-31 18:16
 */
@Data
@ToString
public class OrderDTO {
    private String code;
    private BigDecimal price;
    /**
     * 订单类型
     * 1. 普通订单
     * 2. 团购订单
     * 3. 促销订单
     */
    private String type;
}
