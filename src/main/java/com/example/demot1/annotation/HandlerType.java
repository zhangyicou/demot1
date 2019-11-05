package com.example.demot1.annotation;

import java.lang.annotation.*;

/**
 * @Author: yichu.zhang
 * @Date: 2019-10-31 18:31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HandlerType {
    String value();
}
