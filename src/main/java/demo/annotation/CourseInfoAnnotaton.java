package demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD}) // 可作用与类、方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface CourseInfoAnnotaton {
    // 课程名
    public String courseName();
    // 课程标签
    public String courseTag();
    // 课程简介
    public String courseProfile();
    // 课程序号
    public int courseIndex() default 303;
}
