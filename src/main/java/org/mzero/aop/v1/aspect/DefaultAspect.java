package org.mzero.aop.v1.aspect;

import java.lang.reflect.Method;

/**
 * 切面类默认抽象类
 *
 * @author chengdong.lei@hand-china.com 2020/08/12 22:28
 */
public abstract class DefaultAspect {
    /**
     * 事前拦截
     *
     * @param targetClass 目标类
     * @param method      目标方法
     * @param args        目标方法参数
     * @throws Throwable
     */
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {

    }

    /**
     * 事后拦截
     *
     * @param targetClass
     * @param method
     * @param args
     * @param returnValue
     * @return
     * @throws Throwable
     */
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        return returnValue;
    }

    /**
     * 异常时拦截
     *
     * @param targetClass
     * @param method
     * @param args
     * @param e
     * @throws Throwable
     */
    public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable e) throws Throwable {

    }
}
