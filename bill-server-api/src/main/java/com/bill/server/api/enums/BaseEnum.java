package com.bill.server.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wjp
 * @date 2020/1/2 11:32
 */
@Getter
@AllArgsConstructor
public enum BaseEnum {
    /**
     * 例
     */
    SUCCESS("0000", "请求成功"),
    FAILED("9999", "系统异常"),

    DATA_NOT_EXIST("D0001", "数据不存在");

    private String code;
    private String msg;
}
