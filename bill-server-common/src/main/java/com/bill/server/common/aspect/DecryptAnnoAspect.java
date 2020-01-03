package com.bill.server.common.aspect;

import com.bill.server.common.annotation.DecryptAnno;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;

/**
 * @author wjp
 * @date 2020/1/2 17:21
 */
@Slf4j
@Aspect
@Component
public class DecryptAnnoAspect {

    @Pointcut("@annotation(com.bill.server.common.annotation.DecryptAnno)")
    public void pointcut() {
    }

    @Around("pointcut()")
    private Object resolveParameter(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Object[] args = joinPoint.getArgs();
        Assert.isTrue(args.length > 0, "无参数可以校验");
        for (int i = 0; i < args.length; i++) {
            String typeName = args[i].getClass().getTypeName();
            if (Objects.equals(typeName, "java.lang.String")) {
                args[i] = decrypt(args[i]);
            }
            if (args[i] instanceof Collection) {
                for (Object o : (Collection) args[i]) {
                    hasDecryptFieds(o);
                }
            } else {
                args[i] = hasDecryptFieds(args[i]);
            }
        }

        result = joinPoint.proceed(args);
        return result;
    }

    private Object hasDecryptFieds(Object arg) throws Exception {
        Field[] fields = arg.getClass().getDeclaredFields();
        Object instance = arg.getClass().newInstance();
        BeanUtils.copyProperties(arg, instance);
        for (Field field : fields) {
            check(instance,field);

        }
        return instance;
    }

    private void check(Object instance,Field field) throws IllegalAccessException {
        Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
        if (declaredAnnotations.length > 0) {
            for (Annotation declaredAnnotation : declaredAnnotations) {
                if (declaredAnnotation.annotationType().getTypeName().equals(DecryptAnno.class.getTypeName())) {
                    field.setAccessible(true);
                    field.set(instance, "123444");
                }
            }
        }
    }


    private Object decrypt(Object arg) {
        return arg + "加密过了";

    }
}
