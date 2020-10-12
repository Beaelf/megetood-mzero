package demo.pattern.proxy.impl;

import demo.pattern.proxy.ToBPayment;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/08/12 21:15
 */
public class ToBPaymentImpl implements ToBPayment {
    @Override
    public void pay() {
        System.out.println("公司支付");
    }
}
