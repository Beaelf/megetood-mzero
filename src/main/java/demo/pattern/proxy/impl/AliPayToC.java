package demo.pattern.proxy.impl;

import demo.pattern.proxy.ToCPayment;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/08/12 21:18
 */
public class AliPayToC implements ToCPayment {
    ToCPayment toCPayment;
    public AliPayToC(ToCPayment toCPayment){
        this.toCPayment = toCPayment;
    }
    @Override
    public void pay() {
        beforePay();
        toCPayment.pay();
        afterPay();
    }

    private void afterPay() {
        System.out.println("支付给商家");
    }

    private void beforePay() {
        System.out.println("从银行取款");
    }

}
