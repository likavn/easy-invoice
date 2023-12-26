package com.github.likavn.invoice.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

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
}
