package demo.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationParser {
    // 解析类注解
    public static void parseTypeAnnotation() throws ClassNotFoundException {
        Class clazz = Class.forName("demo.annotation.MegetoodCourse");
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            CourseInfoAnnotaton courseInfoAnnotaton = (CourseInfoAnnotaton) annotation;
            System.out.println("课程名：" + courseInfoAnnotaton.courseName() + "\n"
                    + "课程标签：" + courseInfoAnnotaton.courseTag() + "\n"
                    + "课程简介：" + courseInfoAnnotaton.courseProfile() + "\n"
                    + "课程序号：" + courseInfoAnnotaton.courseIndex());
        }
    }

    /**
     * 解析成员变量上的注解
     * @throws ClassNotFoundException
     */
    public static void parseFieldAnnotation() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("demo.annotation.MegetoodCourse");
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            // 判断成员变量是否有注解
            boolean hasAnnotation = f.isAnnotationPresent(PersonInfoAnnotation.class);
            if (hasAnnotation) {
                PersonInfoAnnotation personInfoAnnotation = f.getAnnotation(PersonInfoAnnotation.class);
                System.out.println("名字：" + personInfoAnnotation.name() + "\n"
                        + "年龄：" + personInfoAnnotation.age() + "\n"
                        + "性别：" + personInfoAnnotation.gender());

                System.out.print("语言:");
                for (String s : personInfoAnnotation.language()) {
                    System.out.print(s+" ");
                }
            }
        }
    }

    // 解析方法注解
    public static void parseMethodAnnotation() throws ClassNotFoundException {
        Class clazz = Class.forName("demo.annotation.MegetoodCourse");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method f : methods) {
            // 判断成员变量是否有注解
            boolean hasAnnotation = f.isAnnotationPresent(CourseInfoAnnotaton.class);
            if (hasAnnotation) {
                CourseInfoAnnotaton courseInfoAnnotaton = f.getAnnotation(CourseInfoAnnotaton.class);
                System.out.println("课程名：" + courseInfoAnnotaton.courseName() + "\n"
                        + "课程标签：" + courseInfoAnnotaton.courseTag() + "\n"
                        + "课程简介：" + courseInfoAnnotaton.courseProfile() + "\n"
                        + "课程序号：" + courseInfoAnnotaton.courseIndex());
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
//        parseTypeAnnotation();
        parseFieldAnnotation();
//        parseMethodAnnotation();
    }

}

