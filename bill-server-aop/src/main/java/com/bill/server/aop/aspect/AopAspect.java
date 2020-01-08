package com.bill.server.aop.aspect;

import com.alibaba.fastjson.JSONArray;
import com.bill.server.dao.annotation.Encrypt;
import com.bill.server.dao.utils.AesUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author wjp
 * @date 2020/1/8 9:41
 */
@Aspect
@Component
@Slf4j
public class AopAspect {


    private static final Integer ZERO = 0;


    @Pointcut("execution(* com.bill.server.dao.dao.user.*.*(..))")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取到入参
        Object[] args = joinPoint.getArgs();
        //克隆参数
        Object[] newArgs = new Object[args.length];
        //获取到方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //获取到参数
        Parameter[] parameters = method.getParameters();
        //空参不需要加密
        if (args.length <= ZERO) {
            newArgs = args;
        } else {
            //参数存在
            for (int i = 0; i < args.length; i++) {
                //对参数进行操作
                Object arg = args[i];
                Parameter parameter = parameters[i];
                //是基本类型，判断是否存在注解
                if (isPrimitive(arg)) {
                    newArgs[i] = resolvePrimitive(arg, parameter);
                    continue;
                }
                //是空或者集合类型
                if (Objects.isNull(arg) || ClassUtils.isPrimitiveOrWrapper(arg.getClass()) || arg instanceof Map) {
                    newArgs[i] = arg;
                    continue;
                }
                if (arg instanceof Collection) {
                    newArgs[i] = resolveList(arg);
                    continue;
                }
                //实体类型
                newArgs[i] = hasDecryptFields(arg);
            }
        }
        return joinPoint.proceed(newArgs);
    }

    private Object resolvePrimitive(Object arg, Parameter parameter) throws Exception {
        //获取到注解
        Annotation[] annotations = parameter.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (Objects.equals(annotation.annotationType().getTypeName(), Encrypt.class.getTypeName())) {
                return decrypt(arg.toString());
            }
        }
        return arg;
    }


    /**
     * 判断是否是基本类型参数
     * @param obj 参数
     * @return Boolean
     */
    private boolean isPrimitive(Object obj) {
        try {
            boolean isString = Objects.equals(obj.getClass(), String.class);
            return obj.getClass().isPrimitive() || isString;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 对string类姓进行加密
     * @param arg string
     * @return string
     * @throws Exception e
     */
    private String decrypt(String arg) throws Exception {
        return AesUtils.aesEncrypt(arg, AesUtils.key);
    }

    private Object hasDecryptFields(Object arg) throws Exception {
        Field[] fields = arg.getClass().getDeclaredFields();
        Object instance = arg.getClass().newInstance();
        BeanUtils.copyProperties(arg, instance);
        for (Field field : fields) {
            Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
            if (declaredAnnotations.length > 0) {
                for (Annotation declaredAnnotation : declaredAnnotations) {
                    if (declaredAnnotation.annotationType().getTypeName().equals(Encrypt.class.getTypeName())) {
                        field.setAccessible(true);
                        String s = String.valueOf(field.get(instance));
                        String aesEncrypt = s;
                        try {
                            AesUtils.aesDecrypt(s, AesUtils.key);
                        } catch (Exception e) {
                            aesEncrypt = AesUtils.aesEncrypt(s, AesUtils.key);
                        }
                        field.set(instance, aesEncrypt);
                    }
                }
            }

        }
        return instance;

    }

    /**
     * 解决入参是集合形式
     * @param arg 入参
     * @return object
     * @throws Exception e
     */
    private Object resolveList(Object arg) throws Exception {
        JSONArray array = new JSONArray();
        for (Object object : (Collection) arg) {
            Object o = hasDecryptFields(object);
            array.add(o);
        }
        return array;
    }
}
