package com.bill.server.dao.aspect;

import com.bill.server.dao.annotation.Encrypt;
import com.bill.server.dao.utils.AesUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author wjp
 * @date 2020/1/2 17:21
 * 解密操作
 */
@Slf4j
@Aspect
@Component
public class DecryptAspect {

    @Pointcut("@annotation(com.bill.server.dao.annotation.Decrypt)")
    public void pointcut() {
    }

    @Around("pointcut()")
    private Object resolveParameter(ProceedingJoinPoint joinPoint) throws Throwable {
        //执行完获取结果
        Object result = joinPoint.proceed();
        //是集合的话
        if (result instanceof Collection) {
            for (Object o : (Collection) result) {
                resolveEntity(o);
            }
            return result;
        }
        //string类型
        if (Objects.equals(result, String.class.getName())) {
            return AesUtils.aesDecrypt(String.valueOf(result), AesUtils.key);
        }
        if (Objects.isNull(result) || ClassUtils.isPrimitiveOrWrapper(result.getClass())
                || result instanceof Map) {
            return result;
        }
        //是实体
        resolveEntity(result);
        return result;
    }

    private void resolveEntity(Object result) throws Exception {
        //实体不为空
        if (Objects.isNull(result)) {
            return;
        }
        Class<?> clazz = result.getClass();
        if (clazz.getDeclaredFields().length > 0) {
            //遍历字段
            for (Field field : clazz.getDeclaredFields()) {
                //获取字段上的注解
                Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
                if (declaredAnnotations.length > 0) {
                    //遍历注解
                    for (Annotation declaredAnnotation : declaredAnnotations) {
                        //获取解密的字段并进行解密
                        if (declaredAnnotation.annotationType().getTypeName().equals(Encrypt.class.getTypeName())) {
                            //暴力破解
                            field.setAccessible(true);
                            //获取当前字段的值并加密
                            String aesEncrypt;
                            try {
                                aesEncrypt = AesUtils.aesDecrypt(String.valueOf(field.get(result)), AesUtils.key);
                            } catch (Exception e) {
                                break;
                            }
                            //设置值
                            field.set(result, aesEncrypt);
                        }
                    }
                }
            }
        }
    }

}
