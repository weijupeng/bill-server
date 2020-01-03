package com.bill.server.dao.dao.user;

import com.bill.server.common.annotation.DecryptAnno;
import com.bill.server.dao.dao.SuperDao;
import com.bill.server.dao.entity.User;
import com.bill.server.dao.mapper.user.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wjp
 * @date 2020/1/2 10:52
 */
@Repository
@Slf4j
public class UserDao extends SuperDao<UserMapper, User> {

    @DecryptAnno
    public User queryUserById(Long id) {
        return baseMapper.queryUserById(id);
    }

    @DecryptAnno
    public void saveUser(User user) {
        baseMapper.insert(user);
    }

    @DecryptAnno
    public void saveUsers(List<User> users) {
        log.info(users + "测试");
        for (User user : users) {
            baseMapper.insert(user);
        }
    }
}
