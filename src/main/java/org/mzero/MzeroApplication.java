package org.mzero;

import org.mzero.aop.v2.AspectWeaver;
import org.mzero.core.BeanContainer;
import org.mzero.core.inject.DependencyInjector;

/**
 * 容器启动类
 *
 * @author Lei Chengdong
 * @date 2020/12/16
 */
public class MzeroApplication {
    public static void run(String packageName) {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans(packageName);
        new AspectWeaver().doAop();
        new DependencyInjector().doIoc();
    }

    public static void main(String[] args) {
        run("com/megetood/demo");
    }
}
