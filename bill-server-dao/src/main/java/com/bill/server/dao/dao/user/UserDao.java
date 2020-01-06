package com.bill.server.dao.dao.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bill.server.common.annotation.Decrypt;
import com.bill.server.common.annotation.Encrypt;
import com.bill.server.dao.dao.SuperDao;
import com.bill.server.dao.entity.User;
import com.bill.server.dao.mapper.user.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wjp
 * @date 2020/1/2 10:52
 */
@Repository
@Slf4j
public class UserDao extends SuperDao<UserMapper, User> {

    @Encrypt
    public User queryUserById(Long id) {
        return baseMapper.queryUserById(id);
    }

    @Encrypt
    public void saveUser(User user) {
        baseMapper.insert(user);
    }

    @Encrypt
    public void saveUsers(List<User> users) {
        log.info(users + "测试");
        for (User user : users) {
            baseMapper.insert(user);
        }
    }
    @Encrypt
    @Decrypt
    public User queryUser(Long id) {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(User::getId, id);
        User user = baseMapper.selectOne(lambdaQuery);
        return user;
    }

    @Encrypt
    @Decrypt
    public List<User> getUserByName(String name) {
        LambdaQueryWrapper<User> query = Wrappers.lambdaQuery();
        query.eq(User::getName, name);
        ArrayList<User> users = new ArrayList<>();
        users.add(getOne(query));
        users.add(getOne(query));
        return users;
    }

}
