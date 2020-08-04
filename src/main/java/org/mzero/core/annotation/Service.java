package org.mzero.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 作用与类上
@Retention(RetentionPolicy.RUNTIME) // 生命周期到达运行时
public @interface Service {
}
