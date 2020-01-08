package com.bill.server.dao.dao.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bill.server.dao.annotation.Decrypt;
import com.bill.server.dao.annotation.Encrypt;
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

    public User queryUserById(Long id) {
        return baseMapper.queryUserById(id);
    }

    public void saveUser(User user) {
        baseMapper.insert(user);
    }

    public void saveUsers(List<User> users) {
        for (User user : users) {
            baseMapper.insert(user);
        }
    }


    @Decrypt
    public List<User> queryUser(Long id) {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(User::getId, id);
        User user = baseMapper.selectOne(lambdaQuery);
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(user);
        return users;
    }

    @Decrypt
    public List<User> getUserByName(String name) {
        User user = new User();
        user.setName(name);
        return baseMapper.selectUserByName(user);
    }

    @Decrypt
    public List<User> getUserByUser(User user) {
        return baseMapper.selectUserByPassword(user);
    }

    @Decrypt
    public String getUserDesc(@Encrypt String s) {
        User user = User.builder().name(s).build();
        List<User> users = baseMapper.selectUserByName(user);
        return users.get(1).getDescription();
    }
}
