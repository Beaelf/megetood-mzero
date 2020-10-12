package org.mzero.aop.v1;

import org.mzero.aop.v1.annotation.Aspect;
import org.mzero.aop.v1.annotation.Order;
import org.mzero.aop.v1.aspect.AspectInfo;
import org.mzero.aop.v1.aspect.DefaultAspect;
import org.mzero.core.BeanContainer;
import org.mzero.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/08/14 20:53
 */
public class AspectWeaver {
    private BeanContainer beanContainer;

    public AspectWeaver() {
        beanContainer = BeanContainer.getInstance();
    }

    public void doAop() {
        // 1. 获取所有的切面类
        Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);
        if (ValidationUtil.isEmpty(aspectSet)) {
            return;
        }

        // 2. 将切面类按照不同的织入目标进行切分
        Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap = new HashMap<>();
        for (Class<?> aspectClass : aspectSet) {
            if (verifyAspect(aspectClass)) {
                categorizedAspect(categorizedMap, aspectClass);
            } else {
                throw new RuntimeException("@Aspect and @Order have not been added to the Aspect class," +
                        "or Aspect class does not extend from DefaultAspect, or the value in Aspect Tag equals @Aspect");
            }
        }
        if(ValidationUtil.isEmpty(categorizedMap)){
            return;
        }

        // 3. 按照不同的织入目标分别按序织入Aspect的逻辑
        for(Class<? extends Annotation> category: categorizedMap.keySet()){
            weaveByCategory(category, categorizedMap.get(category));
        }

    }

    /**
     * 织入逻辑
     *
     * @param category
     * @param aspectInfoList
     */
    private void weaveByCategory(Class<? extends Annotation> category, List<AspectInfo> aspectInfoList) {
        // 1. 获取被代理类的集合
        Set<Class<?>> classSet = beanContainer.getClassesByAnnotation(category);
        if(ValidationUtil.isEmpty(classSet)){
            return;
        }

        // 2. 遍历被代理类，分别为每个被代理类生成动态代理实例
        for(Class<?> targetClass: classSet){
            // 创建动态代理对象
            AspectListExecutor aspectListExecutor = new AspectListExecutor(targetClass,aspectInfoList);
            Object proxyBean = ProxyCreator.createProxy(targetClass, aspectListExecutor);
            // 3. 将动态代理对象实例添加到容器，代替未被代理前的实例
            beanContainer.addBean(targetClass,proxyBean);
        }
    }

    /**
     * 根据切入点进行分类
     * 这这里时根据@Aspect value进行的分类
     *
     * @param categorizedMap
     * @param aspectClass
     */
    private void categorizedAspect(Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap, Class<?> aspectClass) {
        Order orderTag = aspectClass.getAnnotation(Order.class);
        Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);

        DefaultAspect aspect = (DefaultAspect) beanContainer.getBean(aspectClass);
        AspectInfo aspectInfo = new AspectInfo(orderTag.value(), aspect);

        if(!categorizedMap.containsKey(aspectTag.value())){
            // 第一次出现，以该joinpoint为key,创建新的List<AspectInfo>为value
            List<AspectInfo> aspectInfoList=new ArrayList<>();
            aspectInfoList.add(aspectInfo);
            categorizedMap.put(aspectTag.value(),aspectInfoList);
        }else {
            // 不是第一次，则网joinpoint对应的value里添加新的Aspect逻辑
            List<AspectInfo> aspectInfoList = categorizedMap.get(aspectTag.value());
            aspectInfoList.add(aspectInfo);
        }
    }

    /**
     * 1. 一定要遵循给Aspect类添加@Aspect和@Order标签的规范
     * 2. 必须继承自DefaultAspect.class
     * 3. @Aspect的属性值不能是其本身
     *
     * @param aspectClass
     * @return
     */
    private boolean verifyAspect(Class<?> aspectClass) {
        return aspectClass.isAnnotationPresent(Aspect.class)
                && aspectClass.isAnnotationPresent(Order.class)
                && DefaultAspect.class.isAssignableFrom(aspectClass)
                && aspectClass.getAnnotation(Aspect.class).value()!=Aspect.class;
    }
}