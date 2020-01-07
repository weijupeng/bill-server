package com.bill.server.service.user.impl;

import com.bill.server.api.model.Result;
import com.bill.server.api.req.UserAddRequestDTO;
import com.bill.server.dao.dao.user.UserDao;
import com.bill.server.dao.entity.User;
import com.bill.server.service.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author wjp
 * @date 2020/1/2 11:14
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public Result addUser(UserAddRequestDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        userDao.saveUser(user);
//        ArrayList<User> users = new ArrayList<>();
//        users.add(user);
//        users.add(user);
//        userDao.saveUsers(users);
        System.out.println(user);
        return Result.success();
    }

    @Override
    public Result queryByName(String name) {
        List<User> users = userDao.getUserByName(name);
        return Result.successData(users);
    }

    @Override
    public Result query(Long id) {
        List<User> users = userDao.queryUser(id);
        return Result.successData(users);
    }
}
