package com.bill.server.service.user.impl;

import com.bill.server.api.model.Result;
import com.bill.server.api.req.UserAddRequestDTO;
import com.bill.server.common.annotation.DecryptAnno;
import com.bill.server.common.utils.MD5Util;
import com.bill.server.dao.dao.user.UserDao;
import com.bill.server.dao.entity.User;
import com.bill.server.service.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        String md5 = MD5Util.getStringMd5(user.getPassword());
        user.setPassword(md5);
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(user);
        userDao.saveUsers(users);
        return Result.success();
    }



    @Override
    public Result query(Long id) {
        User user = userDao.queryUserById(id);
        Assert.isTrue(Objects.nonNull(user), "数据不存在");
        return Result.successData(user);
    }
}
