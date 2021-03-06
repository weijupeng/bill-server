package com.bill.server.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bill.server.common.annotation.DecryptAnno;
import lombok.Data;

/**
 * @author wjp
 * @date 2020/1/2 10:48
 */
@Data
@TableName("t_user")
public class User {
    @TableId
    private Long id;
    @DecryptAnno
    private String name;
    private String password;
    private String description;
}
