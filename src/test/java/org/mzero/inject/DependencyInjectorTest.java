package org.mzero.inject;

import com.megetood.controller.frontend.MainPageController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mzero.core.BeanContainer;
import org.mzero.core.inject.DependencyInjector;

/**
 * @author leichengdong
 * @version 1.0.0
 * @date 2020/8/4 22:17
 */
public class DependencyInjectorTest {
    @DisplayName("依赖注入doIoc")
    @Test
    public void doIcoTest(){
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.megetood");
        Assertions.assertEquals(true, beanContainer.isLoaded());
        MainPageController   mainPageController = (MainPageController) beanContainer.getBean(MainPageController.class);
        Assertions.assertEquals(true,mainPageController instanceof MainPageController);
        Assertions.assertEquals(null, mainPageController.getHeadLineShopCategoryCombineService());
        new DependencyInjector().doIoc();
        Assertions.assertNotEquals(null, mainPageController.getHeadLineShopCategoryCombineService());
    }
}
