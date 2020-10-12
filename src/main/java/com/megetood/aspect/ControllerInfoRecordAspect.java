package com.megetood.aspect;

import lombok.extern.slf4j.Slf4j;
import org.mzero.aop.v2.annotation.Aspect;
import org.mzero.aop.v2.annotation.Order;
import org.mzero.aop.v2.aspect.DefaultAspect;
import org.mzero.core.annotation.Controller;

import java.lang.reflect.Method;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/08/14 21:44
 */
@Slf4j
//@Aspect(value = Controller.class)
@Aspect(pointcut = "within(com.megetood.controller.superadmin.*)")
@Order(10)
public class ControllerInfoRecordAspect extends DefaultAspect {

    private Long timestampCache;

    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        log.info("ControllerInfoRecordAspect:开始执行，执行的类是[{}]，执行的方法是[{}]，参数是[{}]",
                targetClass.getName(), method.getName(), args);
        timestampCache = System.currentTimeMillis();
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        log.info("ControllerInfoRecordAspect:执行完成，执行的类是[{}]，执行的方法是[{}]，参数是[{}]",
                targetClass.getName(), method.getName(), args);
        return returnValue;
    }

    @Override
    public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable e) throws Throwable {
        log.info("ControllerInfoRecordAspect:执行完成，执行的类是[{}]，执行的方法是[{}]，参数是[{}]，异常是[{}]",
                targetClass.getName(), method.getName(), args,e.getMessage());
    }
}
