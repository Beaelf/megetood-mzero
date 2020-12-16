package org.mzero.aop.v1;

import lombok.Getter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.mzero.aop.v1.aspect.AspectInfo;
import org.mzero.util.ValidationUtil;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * description
 *
 * @author Lei Chengdong
 * @date 2020/12/16
 */
public class AspectListExecutor implements MethodInterceptor {
    // 被代理的类
    private Class<?> targetClass;

    // 升序排列的切面类集合
    @Getter
    private List<AspectInfo> sortedAspectInfoList;

    public AspectListExecutor(Class<?> targetClass, List<AspectInfo> aspectInfoList) {
        this.targetClass = targetClass;
        this.sortedAspectInfoList = sortAspectInfoList(aspectInfoList);
    }

    /**
     * 按order进行升序排序，让order值小的优先织入
     *
     * @param aspectInfoList
     * @return
     */
    private List<AspectInfo> sortAspectInfoList(List<AspectInfo> aspectInfoList) {
        Collections.sort(aspectInfoList, (o1, o2) -> o1.getOrderIndex() - o2.getOrderIndex());
        return aspectInfoList;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object returnValue = null;
        if (ValidationUtil.isEmpty(sortedAspectInfoList)) {
            return returnValue;
        }
        // 1. 按照order的顺序升序执行完所有Aspect的before方法
        invokeBeforeAdvice(method, args);
        // 2. 执行被代理类的方法
        try {
            returnValue = methodProxy.invokeSuper(proxy, args);
            // 3. 被代理类正常返回，则按照order的降序执行完所有Aspect的AfterReturning方法
            returnValue = invokeAfterReturnAdvices(method, args, returnValue);
        } catch (Exception e) {
            // 4. 被代理方法抛出异常，则按照order的降序执行完所有Aspect的AfterThrowing方法
            invokeAfterThrowingAdvice(method, args, e);
        }

        return returnValue;
    }

    private void invokeBeforeAdvice(Method method, Object[] args) throws Throwable{
        for(AspectInfo aspectInfo: sortedAspectInfoList){
            aspectInfo.getAspectObject().before(targetClass, method, args);
        }
    }

    private Object invokeAfterReturnAdvices(Method method, Object[] args, Object returnValue) throws Throwable {
        Object result = null;
        for(int i = sortedAspectInfoList.size()-1;i>=0;i--){
            result = sortedAspectInfoList.get(i).getAspectObject().afterReturning(targetClass,method,args,returnValue);
        }
        return result;
    }

    private void invokeAfterThrowingAdvice(Method method, Object[] args, Exception e) throws Throwable {
        for(int i = sortedAspectInfoList.size()-1;i>=0;i--){
            sortedAspectInfoList.get(i).getAspectObject().afterThrowing(targetClass,method,args,e);
        }
    }
}
