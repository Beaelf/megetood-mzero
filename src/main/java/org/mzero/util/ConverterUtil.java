package org.mzero.util;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/08/16 20:47
 */
public class ConverterUtil {
    /**
     * 返回基本数据类型的空值
     * @param type
     * @return
     */
    public static Object primitiveNull(Class<?> type){
        if(type == int.class || type == double.class
            || type == short.class || type == long.class
            || type == byte.class || type == float.class){
            return 0;
        }else if(type == boolean.class){
            return false;
        }
        return null;
    }

    /**
     * String 类型转换成对应的参数类型
     * @param type
     * @param reqeustValue
     * @return
     */
    public static Object convert(Class<?> type,String reqeustValue){
        if(isPrimitive(type)){
            if(ValidationUtil.isEmpty(reqeustValue)){
                return primitiveNull(type);
            }
            if(type.equals(int.class)||type.equals(Integer.class)){
                return Integer.parseInt(reqeustValue);
            }else if(type.equals(String.class)){
                return reqeustValue;
            }else if(type.equals(Double.class)||type.equals(double.class)){
                return Double.parseDouble(reqeustValue);
            }else if (type.equals(Float.class)|| type.equals(float.class)){
                return Float.parseFloat(reqeustValue);
            }else if (type.equals(Long.class)|| type.equals(long.class)){
                return Long.parseLong(reqeustValue);
            }else if(type.equals(Boolean.class)||type.equals(boolean.class)){
                return Boolean.parseBoolean(reqeustValue);
            }else if (type.equals(Short.class)||type.equals(short.class)){
                return Short.parseShort(reqeustValue);
            }else if(type.equals(Byte.class)||type.equals(byte.class)){
                return Byte.parseByte(reqeustValue);
            }
            return reqeustValue;
        }else {
            throw new RuntimeException("could not support non primitive type conversion yet");
        }
    }

    /**
     * 判断是否时基本数据类型（包括包装类型以及String）
     * @param type
     * @return
     */
    private static boolean isPrimitive(Class<?> type){
    return type == String.class
            || type == int.class
            || type == Integer.class
            || type == double.class
            || type == Double.class
            || type == short.class
            || type == Short.class
            || type == long.class
            || type == Long.class
            || type == byte.class
            || type == Byte.class
            || type == float.class
            || type == Float.class
            || type == boolean.class
            || type == Boolean.class
            || type == char.class
            || type == Character.class;
    }
}
