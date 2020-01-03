package com.bill.server.controller;

import com.bill.server.api.model.Result;
import com.bill.server.api.req.UserAddRequestDTO;
import com.bill.server.service.user.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wjp
 * @date 2020/1/2 11:11
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/query/{id}")
    public Result query(@PathVariable("id") Long id) {
        return userService.query(id);
    }

    @PostMapping("/add")
    public Result addUser(@Validated @RequestBody UserAddRequestDTO dto) {
        return userService.addUser(dto);
    }
}
