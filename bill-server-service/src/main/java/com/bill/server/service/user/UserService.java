package com.bill.server.service.user;

import com.bill.server.api.model.Result;
import com.bill.server.api.req.UserAddRequestDTO;

/**
 * @author wjp
 * @date 2020/1/2 11:14
 */
public interface UserService {
    /**
     * 查询用户
     * @param id 用户ID
     * @return user
     */
    Result query(Long id);

    /**
     * 新增用户
     * @param dto 实体
     * @return return
     */
    Result addUser(UserAddRequestDTO dto);

    /**
     * 通过用户名查找
     * @param name 用户名
     * @return result
     */
    Result queryByName(String name);

}
