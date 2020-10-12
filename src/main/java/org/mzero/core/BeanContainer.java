package org.mzero.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mzero.aop.v2.annotation.Aspect;
import org.mzero.core.annotation.Component;
import org.mzero.core.annotation.Controller;
import org.mzero.core.annotation.Repository;
import org.mzero.core.annotation.Service;
import org.mzero.util.ClassUtil;
import org.mzero.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)    // 私有构造函数
public class BeanContainer {
    /**
     * bean载体：存放所有被指定注解标记的对象
     */
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap();

    /**
     * 容器是否加载的标识，加载过为true
     */
    private boolean loaded = false;

    /**
     * 加载bean的注解列表
     */
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION
            = Arrays.asList(Component.class, Controller.class, Service.class, Repository.class, Aspect.class);

    /**
     * 判断是否加载过
     *
     * @return
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * 获取bean实例数量
     *
     * @return
     */
    public int size() {
        return beanMap.size();
    }

    /**
     * 获取bean实例
     *
     * @return
     */
    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    private enum ContainerHolder {
        HOLDER;
        private BeanContainer instance;

        ContainerHolder() {
            instance = new BeanContainer();
        }
    }

    /**
     * 添加一个bean,并放回bean实例对象
     *
     * @param clazz
     * @param bean
     * @return
     */
    public Object addBean(Class<?> clazz, Object bean) {
        return beanMap.put(clazz, bean);
    }

    /**
     * 移除容器中的一个bean
     *
     * @param clazz Class对象
     * @return 删除的bean
     */
    public Object removeBean(Class<?> clazz) {
        return beanMap.remove(clazz);
    }

    /**
     * 根据Class对象获取Bean
     *
     * @param clazz 类名
     * @return
     */
    public Object getBean(Class<?> clazz) {
        return beanMap.get(clazz);
    }

    /**
     * 获取容器管理的所有Class对象集合
     *
     * @return
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * 获取容器中的所有bean对象
     *
     * @return
     */
    public Set<Object> getBeans() {
        return new HashSet<>(beanMap.values());
    }

    /**
     * 获取被指定注解标记的类
     *
     * @param annotation
     * @return
     */
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        // 1. 获取beanMap中所有Class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            log.warn("nothing in beanMap");
            return null;
        }
        // 2. 通过注解筛选出被标记的Class对象
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            // 类是有注解
            if (clazz.isAnnotationPresent(annotation)) {
                classSet.add(clazz);
            }
        }
        // size为0则返回null
        return classSet.size() > 0 ? classSet : null;
    }

    /**
     * 获取指定接口（父类）的实现类（子类）
     *
     * @param interfaceOrClass
     * @return
     */
    public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass) {
        // 1. 获取beanMap中所有Class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            log.warn("nothing in beanMap");
            return null;
        }
        // 2. 通过注解筛选出被标记的Class对象
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            // 判断是否为子类或实现类
            if (interfaceOrClass.isAssignableFrom(clazz) && !clazz.equals(interfaceOrClass)) {
                classSet.add(clazz);
            }
        }
        // size为0则返回null
        return classSet.size() > 0 ? classSet : null;
    }

    /**
     * 扫描加载所有的bean
     *
     * @param packageName 包名
     */
    public synchronized void loadBeans(String packageName) {
        // 判断是否加载过
        if (isLoaded()) {
            log.warn("Bean Container has been loaded");
            return;
        }
        // 加载包下所有类
        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
        if (ValidationUtil.isEmpty(classSet)) {
            log.warn("extract nothing from packageName:" + packageName);
            return;
        }

        // 将被BEAN_ANNOTATION中指定注解标记的类加入到容器中
        for (Class<?> clazz : classSet) {
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                // 判断类是否标注了BEAN_ANNOTATION中的注解
                if (clazz.isAnnotationPresent(annotation)) {
                    // 将目标类本身作为键，目标类的实例作为值，放入beanMap中
                    beanMap.put(clazz, ClassUtil.newInstance(clazz, true));
                }
            }
        }

        // 设置loaded加载标识为true
        loaded = true;
    }
}
