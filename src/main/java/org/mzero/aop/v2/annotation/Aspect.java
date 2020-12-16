package org.mzero.aop.v2.annotation;

import java.lang.annotation.*;

/**
 * description
 *
 * @author Lei Chengdong
 * @date 2020/12/16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    String pointcut();
}
