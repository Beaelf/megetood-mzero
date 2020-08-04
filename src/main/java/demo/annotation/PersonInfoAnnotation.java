package demo.annotation;

import jdk.nashorn.internal.ir.annotations.Reference;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * 不能通过implement来继承注解、接口等
 */
@Target(ElementType.FIELD)  // 作用与成员变量
@Retention(RetentionPolicy.RUNTIME) // 保留到运行时
public @interface PersonInfoAnnotation {
    // 名字
    public String name();
    // 年龄
    public int age() default 19;
    // 性别
    public String gender() default "男";
    // 开发语言
    public String[] language();
}
