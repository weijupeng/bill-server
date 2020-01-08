package com.bill.server.controller;

import com.bill.server.aop.annotation.MyLog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weijupeng
 */
@RestController
@RequestMapping(value = "/index")
public class IndexController {

    @MyLog(requestUrl = "/index请求")
    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}