package org.mzero.core.inject;

import lombok.extern.slf4j.Slf4j;
import org.mzero.core.BeanContainer;
import org.mzero.core.inject.annotation.Autowired;
import org.mzero.util.ClassUtil;
import org.mzero.util.ValidationUtil;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author leichengdong
 * @version 1.0.0
 * @date 2020/8/4 13:05
 */
@Slf4j
public class DependencyInjector {
    /**
     * Bean容器
     */
    private BeanContainer beanContainer;

    public DependencyInjector() {
        beanContainer = BeanContainer.getInstance();
    }

    /**
     * ioc
     */
    public void doIoc() {
        // 获取bean容器中的所有类对象
        Set<Class<?>> classSet = beanContainer.getClasses();
        if (ValidationUtil.isEmpty(classSet)) {
            log.warn("empty classSet in BeanContainer");
            return;
        }

        // 1.遍历Bean容器中所有的Class对象
        for (Class<?> clazz : classSet) {
            // 2. 遍历所有成员变量
            Field[] fields = clazz.getDeclaredFields();
            if (ValidationUtil.isEmpty(fields)) {
                continue;
            }
            for (Field field : fields) {
                // 3. 找出被Autowired标记的成员变量
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String autowiredValue = autowired.value();
                    // 4. 获取成员变量类型
                    Class<?> fieldClass = field.getType();
                    // 5. 获取成员变量对应的实例
                    Object fieldValue = getFieldInstance(fieldClass, autowiredValue);
                    if (fieldValue == null) {
                        throw new RuntimeException("unable to inject relevant type, target fieldClass is: " + fieldClass.getName());
                    } else {
                        // 6. 通过反射向目标类的成员变量注入其对应的类实例
                        Object targetBean = beanContainer.getBean(clazz);
                        ClassUtil.setField(field, targetBean, fieldValue, true);
                    }
                }
            }
        }
    }

    /**
     * 获取成员变量对应的实例
     *
     * @param fieldClass
     * @return
     */
    private Object getFieldInstance(Class<?> fieldClass, String autowiredValue) {
        Object fieldValue = beanContainer.getBean(fieldClass);
        if (fieldValue != null) {
            // 成员变量的类型对应的实例对象已经存在，直接返回
            return fieldValue;
        } else {
            // 不存在，则获取autowiredValue获取
            Class<?> implementedClass = getImplementedClass(fieldClass, autowiredValue);
            if (implementedClass != null) {
                return beanContainer.getBean(implementedClass);
            } else {
                return null;
            }
        }
    }

    /**
     * 获取成员变量的实现类
     *
     * @param fieldClass     成员变量类对象
     * @param autowiredValue 实现类类名
     * @return
     */
    private Class<?> getImplementedClass(Class<?> fieldClass, String autowiredValue) {
        // 获取fieldClass类型的所有实例对象
        Set<Class<?>> classSet = beanContainer.getClassesBySuper(fieldClass);
        if (ValidationUtil.isEmpty(classSet)) {
            if (ValidationUtil.isEmpty(autowiredValue)) {
                if (classSet.size() == 1) {
                    return classSet.iterator().next();
                } else {
                    // 多个实现类，则抛出异常，让用户自己指定
                    throw new RuntimeException("mutiple implemented class for " + fieldClass.getName() + ", please set @Autowired'value to pick one");
                }
            } else {
                // 返回类名为autowiredValue类对象
                for (Class<?> clazz : classSet) {
                    if (autowiredValue.equals(clazz.getSimpleName())) {
                        return clazz;
                    }
                }
            }
        }
        return null;
    }
}
