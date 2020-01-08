package com.bill.server.aop.annotation;

import java.lang.annotation.*;

/**
 * @author wjp
 * @date 2020/1/7 17:01
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLog {
    String requestUrl();
}
