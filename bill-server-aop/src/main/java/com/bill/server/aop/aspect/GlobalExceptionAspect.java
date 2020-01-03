package com.bill.server.aop.aspect;

import com.bill.server.api.enums.BaseEnum;
import com.bill.server.api.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wjp
 * @date 2020/1/2 14:05
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAspect {
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result usuallyExceptionHandle(IllegalArgumentException e) {
        log.error(e.getMessage());
        return Result.valuesOf(false, BaseEnum.DATA_NOT_EXIST.getCode(), e.getMessage());
    }


//    @ExceptionHandler(value = Throwable.class)
//    public Result exceptionHandle(Throwable e) {
//        log.error(e.getMessage());
//        return Result.valuesOf(false, BaseEnum.DATA_NOT_EXIST.getCode(), e.getMessage());
//    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result methodArgumentExceptionHandle(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return Result.valuesOf(false, BaseEnum.DATA_NOT_EXIST.getCode(), errorMessage(e));
    }

    private String errorMessage(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            String messages = error.getArguments()[0].toString().split("default message")[1];
            stringBuilder.append(StringUtils.isEmpty(error.getDefaultMessage()) ? "param is invalid" : String.format("参数%s:%s", messages, error.getDefaultMessage()));
        }
        return stringBuilder.toString();
    }


}
