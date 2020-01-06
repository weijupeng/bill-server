package com.bill.server.common.aspect;

import com.bill.server.common.annotation.Encrypt;
import com.bill.server.common.utils.AesUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author wjp
 * @date 2020/1/2 17:21
 * 加密操作
 */
@Slf4j
@Aspect
@Component
public class EncryptAspect {

    @Pointcut("@annotation(com.bill.server.common.annotation.Encrypt)")
    public void pointcut() {
    }

    @Around("pointcut()")
    private Object resolveParameter(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        Object[] args = joinPoint.getArgs();
        if (!CollectionUtils.isEmpty(Arrays.asList(args))) {
            for (int i = 0; i < args.length; i++) {
                //string类型
                String typeName = args[i].getClass().getTypeName();
                if (Objects.equals(typeName, String.class.getName())) {
                    args[i] = decrypt(args[i].toString());
                    continue;
                }
                //是list
                if (args[i] instanceof Collection) {
                    for (Object o : (Collection) args[i]) {
                        hasDecryptFieds(o);
                    }
                    continue;
                }
                //不是基本类型
                if (Objects.isNull(args[i]) || ClassUtils.isPrimitiveOrWrapper(args[i].getClass())
                        || args[i] instanceof Map) {
                    continue;
                }
                //实体类型
                args[i] = hasDecryptFieds(args[i]);
            }
        }
        result = joinPoint.proceed(args);
        return result;
    }

    /**
     * @param arg 参数为实体类型
     * @return
     * @throws Exception
     */
    private Object hasDecryptFieds(Object arg) throws Exception {
        Field[] fields = arg.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
            if (declaredAnnotations.length > 0) {
                for (Annotation declaredAnnotation : declaredAnnotations) {
                    if (declaredAnnotation.annotationType().getTypeName().equals(Encrypt.class.getTypeName())) {
                        field.setAccessible(true);
                        String s = String.valueOf(field.get(arg));
                        log.info("字段的值：{}", s);
                        String aesEncrypt = AesUtils.aesEncrypt(s, AesUtils.key);
                        log.info("加密后的字段" + aesEncrypt);
                        field.set(arg, aesEncrypt);
                    }
                }
            }

        }
        return arg;
    }

    private String decrypt(String arg) throws Exception {
        String s = AesUtils.aesEncrypt(arg, AesUtils.key);
        log.info("加密后的入参" + s);
        return s;

    }

}
