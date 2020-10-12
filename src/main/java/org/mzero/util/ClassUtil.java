package org.mzero.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ClassUtil {

    private static final String FILE_PROTOCOL = "file";

    /**
     * 获取指定包下的所有类
     * 1.获取包所在路径
     * 2.获取路径下的所有class文件
     *
     * @param packageName
     * @return Class对象集合
     */
    public static Set<Class<?>> extractPackageClass(String packageName){
        /*
            1. 获取类加载器：获取项目发布实际路径
            2. 通过类加载器取到加载的资源信息
            3. 依据不同资源类型，采用不同的方式获取资源集合：我们这里获取的时class后缀的类文件
         */
        ClassLoader classLoader = getClassLoader();
        URL url = classLoader.getResource(packageName.replace(".", "/"));
        if (url == null){
            log.warn("unable to retrieve anything from package:"+packageName);
            return null;
        }
        // 过滤文件资源，筛选出文件类型
        Set<Class<?>> classSet = null;
        if(FILE_PROTOCOL.equalsIgnoreCase(url.getProtocol())){
            classSet = new HashSet<Class<?>>();
            File packageDirectory = new File(url.getPath());
            // 获取class后缀的文件，存入到classSet集合
            extractClassFile(classSet,packageDirectory,packageName);
        }
        return classSet;
    }

    /**
     * 递归获取目标package里的所有class文件（包括子包）
     *
     * @param classSet      装载目标类的集合
     * @param fileSource    文件或目录
     * @param packageName   包名
     */
    private static void extractClassFile(Set<Class<?>> classSet, File fileSource, String packageName) {
        // 找到资源，终止递归: 非文件夹
        if(!fileSource.isDirectory()){
            return;
        }
        // 如果是文件夹, 获取当前文件下的所有文件和文件夹
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                // 判断是否为文件夹
                if(file.isDirectory()){
                    return true;
                }else {
                    // 获取文件绝对路径
                    String absoluteFilePath = file.getAbsolutePath();
                    if (absoluteFilePath.endsWith(".class")){
                        // 是class类型文件，则直接加载
                        addToClassSet(absoluteFilePath);
                    }
                }
                return false;
            }

            /**
             * 1. 从class文件的绝对路径中获取所有包含package的类名
             * 2. 格式化成com.megetood.dto.MainPageInfoDTO形式
             * 3. 通过反射机制获取对应的Class对象加入到classSet中
             *
             * @param absoluteFilePath
             */
            private void addToClassSet(String absoluteFilePath) {
                absoluteFilePath = absoluteFilePath.replace(File.separator, ".");
                String className = absoluteFilePath.substring(absoluteFilePath.indexOf(packageName),absoluteFilePath.lastIndexOf("."));
                Class<?> targetClass = loadClass(className);
                classSet.add(targetClass);
            }

        });

        // 将获取到的文件夹进行递归操作
        if (files != null){
            for (File f: files){
                extractClassFile(classSet, f, packageName);
            }
        }
    }

    /**
     * 根据类名获取类对象
     *
     * @param className 根包开始的全路径类名
     *                  the fully qualified name of the desired class
     * @return
     */
    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("load class error:",e);
            throw new RuntimeException();
        }
    }

    /**
     * 获取当前线程的ClassLoader
     * @return
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 创建类实例
     * @param clazz Class
     * @param accessible 是否支持创建私有的class对象实例
     * @param <T>   class的类型
     * @return  实例化的类
     */
    public static <T> T newInstance(Class<?> clazz, boolean accessible){
        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(accessible);
            return (T)constructor.newInstance();
        } catch (Exception e) {
            log.error("newInstance error",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 为实例的成员变量注入对应实体对象
     *
     * @param field      成员变量
     * @param target     类实例
     * @param value      成员变量的值
     * @param accessible 是否允许私有属性
     */
    public static void setField(Field field, Object target, Object value, boolean accessible){
        field.setAccessible(accessible);
        try {
            field.set(target,value);
        } catch (IllegalAccessException e) {
            log.error("setField error", e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        extractPackageClass("com.megetood.entity");
    }
}
