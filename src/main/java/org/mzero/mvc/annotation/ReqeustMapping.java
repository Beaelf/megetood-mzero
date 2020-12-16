package org.mzero.mvc.annotation;

import org.mzero.mvc.type.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识Controller的方法与请求路径和请求方法的映射关系
 *
 * @author Lei Chengdong
 * @date 2020/12/16
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReqeustMapping {
    /**
     * 请求路径
     *
     * @return
     */
    String value() default "";

    /**
     * 请求方法
     *
     * @return
     */
    RequestMethod method() default RequestMethod.GET;
}
