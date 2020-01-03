package com.bill.server.api.model;

import com.bill.server.api.enums.BaseEnum;
import lombok.Getter;

/**
 * @author wjp
 * @date 2020/1/2 11:30
 */
@Getter
public class Result<T> {
    private Boolean success;
    private String code;
    private String msg;
    private T data;

    public Result(Boolean success, String code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    private Result(Boolean success, BaseEnum baseEnum) {
        this.success = success;
        this.code = baseEnum.getCode();
        this.msg = baseEnum.getMsg();
    }

    private Result setData(T data) {
        this.data = data;
        return this;
    }

    public static Result valuesOf(Boolean success, String code, String msg) {
        return new Result(success, code, msg);
    }

    public static Result success() {
        return new Result(true, BaseEnum.SUCCESS);
    }

    public static <T> Result<T> successData(T data) {
        return success().setData(data);
    }

    public static Result fail() {
        return new Result(false, BaseEnum.FAILED);
    }

    public static Result fail(BaseEnum baseEnum) {
        return new Result(false, baseEnum);
    }


}
