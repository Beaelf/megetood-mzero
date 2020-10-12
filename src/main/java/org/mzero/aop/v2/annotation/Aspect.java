package org.mzero.aop.v2.annotation;

import java.lang.annotation.*;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/08/12 22:24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    String pointcut();
}
