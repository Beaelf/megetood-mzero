package org.mzero.aop.v1;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * 创建代理对象
 *
 * @author Lei Chengdong
 * @date 2020/12/16
 */
public class ProxyCreator {
    public static Object createProxy(Class<?> targetClass, MethodInterceptor methodInterceptor){
        return Enhancer.create(targetClass,methodInterceptor);
    }

}
