package com.example.demot1.event;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: yichu.zhang
 * @Date: 2019-11-22 16:21
 */
@Data
@Builder
@ToString
public class OrderEvent {
    private long orderId;
    private double price;
    private double size;
}
