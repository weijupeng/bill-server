package com.bill.server.dao.mapper.user;

import com.bill.server.dao.entity.User;
import com.bill.server.dao.mapper.SupperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wjp
 * @date 2020/1/2 10:51
 */
@Mapper
public interface UserMapper extends SupperMapper<User> {
    /**
     * 查找用户
     * @param id id
     * @return user
     */
    User queryUserById(@Param("id") Long id);

    List<User> selectUserByPassword(@Param("user") User user);

    List<User> selectUserByName(@Param("user") User user);
}
