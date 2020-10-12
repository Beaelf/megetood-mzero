package demo.pattern.proxy.impl;

import demo.pattern.proxy.ToCPayment;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/08/12 21:15
 */
public class ToCPaymentImpl implements ToCPayment {
    @Override
    public void pay() {
        System.out.println("用户支付");
    }
}
