package com.github.likavn.invoice.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * 工具类
 *
 * @author likavn
 * @since 2023/01/01
 */
@Slf4j
@UtilityClass
public class Func {
    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.size() <= 0;
    }

    public static boolean isEmpty(Object[] objs) {
        return null == objs || objs.length <= 0;
    }

    public static boolean isEmpty(Collection<?> list) {
        return null == list || list.isEmpty();
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public void setFieldVal(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, covertValue(field.getType(), value));
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
    }

    public Object covertValue(Class<?> type, Object value) {
        if (type == String.class || type == Character.class) {
            return value.toString();
        } else if (type == Byte.class) {
            return Byte.valueOf(value.toString());
        } else if (type == Short.class) {
            return Short.valueOf(value.toString());
        } else if (type == Integer.class) {
            return Integer.valueOf(value.toString());
        } else if (type == Long.class) {
            return Long.valueOf(value.toString());
        } else if (type == Boolean.class) {
            return Boolean.valueOf(value.toString());
        } else if (type == Float.class) {
            return Float.valueOf(value.toString());
        } else if (type == Double.class) {
            return Double.valueOf(value.toString());
        }
        Assert.isTrue(false, "不支持的类型");
        return null;
    }
}
