package demo.pattern.proxy.jdkproxy.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/08/12 21:45
 */
public class JdkDynamicProxyUtil {
    public static <T> T newProxyInstance(T targetObject, InvocationHandler invocationHandler){
        ClassLoader classLoader = targetObject.getClass().getClassLoader();
        Class<?>[] interfaces = targetObject.getClass().getInterfaces();
        return (T) Proxy.newProxyInstance(classLoader,interfaces,invocationHandler);
    }
}
