package demo.pattern.proxy;

import demo.pattern.proxy.cglib.AlipayMethodInterceptor;
import demo.pattern.proxy.cglib.util.CjlibUtil;
import demo.pattern.proxy.impl.AliPayToC;
import demo.pattern.proxy.impl.ToCPaymentImpl;
import demo.pattern.proxy.jdkproxy.AliPayInvocationHandler;
import demo.pattern.proxy.jdkproxy.util.JdkDynamicProxyUtil;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/08/12 21:20
 */
public class ProxyDemo {
    public static void main(String[] args) {
//        ToCPayment toCPayment = new AliPayToC(new ToCPaymentImpl());
//        toCPayment.pay();
//
//        ToCPayment target = new ToCPaymentImpl();
//        ToCPayment toCProxy = JdkDynamicProxyUtil.newProxyInstance(target, new AliPayInvocationHandler(target));
//        toCProxy.pay();


        // jdk代理只支持接口机制
        CommonPay commonPay = new CommonPay();
        CommonPay proxy1 = JdkDynamicProxyUtil.newProxyInstance(commonPay, new AliPayInvocationHandler(commonPay));
        proxy1.pay();

        CommonPay proxy2 = CjlibUtil.createProxy(commonPay, new AlipayMethodInterceptor());
        proxy2.pay();
    }
}
