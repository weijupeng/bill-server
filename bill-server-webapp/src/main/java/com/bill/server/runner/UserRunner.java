package com.bill.server.runner;

import com.bill.server.dao.dao.user.UserDao;
import com.bill.server.dao.entity.User;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wjp
 * @date 2020/1/8 15:22
 */
@Component
public class UserRunner implements ApplicationRunner {

    @Resource
    private UserDao userDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> users = userDao.getUserByName("KKKKK");
        System.out.println("users = " + users);

        List<User> userList = userDao.getUserByUser(User.builder().password("1234").build());
        System.out.println("user = " + userList);

//        String desc = userDao.getUserDesc("KKKKK");
//        System.out.println(desc);
//
//        User user = userDao.getById(1L);
//        System.out.println("user = " + user);
    }
}
