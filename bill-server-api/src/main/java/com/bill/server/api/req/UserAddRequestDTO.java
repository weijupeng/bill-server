package com.bill.server.api.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wjp
 * @date 2020/1/2 14:27
 */
@Data
public class UserAddRequestDTO {
    @NotNull(message = "name不能传空")
    private String name;
    @NotNull(message = "密码不能传空")
    private String password;
    @NotNull(message = "描述不能传空")
    private String description;
}
