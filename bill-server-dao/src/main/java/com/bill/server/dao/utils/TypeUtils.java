package com.bill.server.dao.utils;

import java.util.Objects;

/**
 * @author kancy
 * @date 2020/1/5 21:59
 **/
public class TypeUtils {

    /**
     * 该对象是否是String类型
     * @param object
     * @return
     */
    public static boolean isStringType(Object object){
        if (Objects.isNull(object)){
            return false;
        }
        return Objects.equals(object, String.class) || Objects.equals(object.getClass(), String.class);
    }
}
