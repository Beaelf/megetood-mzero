package demo.pattern.proxy.cglib.util;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/08/12 22:07
 */
public class CjlibUtil {
    public static <T> T createProxy(T target, MethodInterceptor methodInterceptor){
        return (T)Enhancer.create(target.getClass(),methodInterceptor);
    }
}
