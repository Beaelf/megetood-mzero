package org.mzero.aop.v2;

import org.mzero.aop.v2.annotation.Aspect;
import org.mzero.aop.v2.annotation.Order;
import org.mzero.aop.v2.aspect.AspectInfo;
import org.mzero.aop.v2.aspect.DefaultAspect;
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
        // 2. 拼装AspectInfoList
        List<AspectInfo> aspectInfoList = packAspectInfoList(aspectSet);
        // 3. 遍历容器的类
        Set<Class<?>> classSet = beanContainer.getClasses();
        for (Class<?> targetClass : classSet) {
            // 排除AspectClass自身
            if (targetClass.isAnnotationPresent(Aspect.class)) {
                continue;
            }
            // 4. 粗筛符合条件的Aspect
            List<AspectInfo> roughMatchedAsepct =
                    collectRoughMatchedAspectListForSpecificClass(aspectInfoList, targetClass);
            // 5. 尝试进行Aspect的织入
            wrapIfNecessary(roughMatchedAsepct, targetClass);
        }

    }

    private void wrapIfNecessary(List<AspectInfo> roughMatchedAsepct, Class<?> targetClass) {
        AspectListExecutor aspectListExecutor = new AspectListExecutor(targetClass, roughMatchedAsepct);
        Object proxyBean = ProxyCreator.createProxy(targetClass, aspectListExecutor);
        beanContainer.addBean(targetClass, proxyBean);

    }

    private List<AspectInfo> collectRoughMatchedAspectListForSpecificClass(List<AspectInfo> aspectInfoList, Class<?> targetClass) {
        List<AspectInfo> roughMatchedAspectList = new ArrayList<>();
        for (AspectInfo aspectInfo : aspectInfoList) {
            if (aspectInfo.getPointcutLocator().roughMatchs(targetClass)) {
                roughMatchedAspectList.add(aspectInfo);
            }
        }
        return roughMatchedAspectList;
    }


    private List<AspectInfo> packAspectInfoList(Set<Class<?>> aspectSet) {
        List<AspectInfo> aspectInfoList = new ArrayList<>();
        for (Class<?> aspectClass : aspectSet) {
            if (!verifyAspect(aspectClass)) {
                throw new RuntimeException("@Aspect and @Order have not been added to the Aspect class, or Aspect class does not extend from DefaultAspect");
            }
            Order orderTag = aspectClass.getAnnotation(Order.class);
            Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
            String poincut = aspectTag.pointcut();
            DefaultAspect aspect = (DefaultAspect) beanContainer.getBean(aspectClass);
            AspectInfo aspectInfo = new AspectInfo(orderTag.value(), aspect, new PointcutLocator(poincut));
            aspectInfoList.add(aspectInfo);
        }
        return aspectInfoList;
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
                && DefaultAspect.class.isAssignableFrom(aspectClass);
    }
}