package org.mzero.aop;

import com.megetood.controller.superadmin.HeadLineOperationController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mzero.aop.v2.AspectWeaver;
import org.mzero.core.BeanContainer;
import org.mzero.core.inject.DependencyInjector;

/**
 * description
 *
 * @author chengdong.lei@hand-china.com 2020/08/14 21:41
 */
public class AspectWeaverTest {
    @DisplayName("测试doAop")
    @Test
    public void doAopTest(){
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.megetood");
        new AspectWeaver().doAop();
        new DependencyInjector().doIoc();
        HeadLineOperationController bean = (HeadLineOperationController) beanContainer.getBean(HeadLineOperationController.class);
//        bean.addHeadLine(null,null);
    }

}
