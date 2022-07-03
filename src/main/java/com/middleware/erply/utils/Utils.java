package com.middleware.erply.utils;

import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;

public class Utils {

    public static void setField(Field f, Object target, Object value) {
        setField(f.getName(), target, value);
    }

    public static void setField(String fieldName, Object target, Object value) {
        new BeanWrapperImpl(target).setPropertyValue(fieldName, value);
    }

    public static <T> T getField(Field f, Object target) {
        return getField(f.getName(), target);
    }

    public static <T> T getField(String fieldName, Object target) {
        return (T) new BeanWrapperImpl(target).getPropertyValue(fieldName);
    }
}