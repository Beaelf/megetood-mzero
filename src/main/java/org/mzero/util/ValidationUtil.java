package org.mzero.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ValidationUtil {
    /**
     * 判断集合是否为null或size为0
     * @param obj
     * @return
     */
    public static boolean isEmpty(Collection<?> obj){
        return obj == null || obj.isEmpty();
    }

    /**
     * String是否为null或“”
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return str == null || "".equals(str);
    }

    /**
     * Array是否为null或size=0
     * @param arr
     * @return
     */
    public static boolean isEmpty(Object[] arr){
        return arr == null || arr.length == 0;
    }

    /**
     * Map是否为null或size为0
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?,?> map){
        return map == null || map.isEmpty();
    }






}
