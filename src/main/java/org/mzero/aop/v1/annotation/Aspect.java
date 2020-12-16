package org.mzero.aop.v1.annotation;

import java.lang.annotation.*;

/**
 * 用于标记一个类为切面实现类
 *
 * @author Lei Chengdong
 * @date 2020/12/16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 标识作用于哪些注解标记的类
     *
     * @return
     */
    Class<? extends Annotation> value();
}
