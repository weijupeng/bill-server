//package com.bill.server.dao.aspect;
//
//import com.alibaba.fastjson.JSONArray;
//import com.bill.server.dao.annotation.Encrypt;
//import com.bill.server.dao.utils.AesUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ClassUtils;
//import org.springframework.util.CollectionUtils;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * @author wjp
// * @date 2020/1/2 17:21
// * 加密操作
// */
//@Slf4j
//@Aspect
//@Component
//public class EncryptAspect extends BaseCryptAspect{
//
//    @Pointcut("@annotation(com.bill.server.dao.annotation.Encrypt)")
//    public void pointcut() {
//    }
//
//    @Around("pointcut()")
//    private Object resolveParameter(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object result;
//        Object[] args = joinPoint.getArgs();
//        Object[] objects = new Object[args.length];
//        if (!CollectionUtils.isEmpty(Arrays.asList(args))) {
//            for (int i = 0; i < args.length; i++) {
//                //string类型
//                String typeName = args[i].getClass().getTypeName();
//                if (Objects.equals(typeName, String.class.getName())) {
//                    objects[i] = decrypt(args[i].toString());
//                    continue;
//                }
//                //是list
//                if (args[i] instanceof Collection) {
//                    objects[i] = resolveList(args[i]);
//                    continue;
//                }
//                //不是基本类型
//                if (Objects.isNull(args[i]) || ClassUtils.isPrimitiveOrWrapper(args[i].getClass())
//                        || args[i] instanceof Map) {
//                    objects[i] = args[i];
//                    continue;
//                }
//                //实体类型
//                objects[i] = hasDecryptFields(args[i]);
//            }
//        }
//        result = joinPoint.proceed(objects);
//        return result;
//    }
//
//    }
