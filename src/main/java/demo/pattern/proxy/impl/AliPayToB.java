package demo.pattern.proxy.impl;

import demo.pattern.proxy.ToBPayment;
import demo.pattern.proxy.ToCPayment;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/08/12 21:24
 */
public class AliPayToB implements ToBPayment {
    ToBPayment toBPayment;
    public AliPayToB(ToBPayment toBPayment){
        this.toBPayment = toBPayment;
    }
    @Override
    public void pay() {
        beforePay();
        toBPayment.pay();
        afterPay();
    }

    private void afterPay() {
        System.out.println("支付给商家");
    }

    private void beforePay() {
        System.out.println("从银行取款");
    }
}
