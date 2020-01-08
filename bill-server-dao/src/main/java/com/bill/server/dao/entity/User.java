package com.bill.server.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bill.server.dao.annotation.Decrypt;
import com.bill.server.dao.annotation.Encrypt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wjp
 * @date 2020/1/2 10:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class User {
    @TableId
    private Long id;
    private String name;
    @Decrypt
    @Encrypt
    private String password;
    private String description;
}
