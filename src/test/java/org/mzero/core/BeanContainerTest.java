package org.mzero.core;

import com.megetood.controller.frontend.MainPageController;
import com.megetood.service.solo.HeadLineService;
import org.junit.jupiter.api.*;
import org.mzero.core.annotation.Service;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeanContainerTest {
    private static BeanContainer beanContainer;

    @BeforeAll
    static void init(){
        beanContainer = BeanContainer.getInstance();
    }

    @Order(1)
    @DisplayName("加载bean")
    @Test
    public void loadBeansTest(){
        Assertions.assertEquals(false, beanContainer.isLoaded());
        beanContainer.loadBeans("com.megetood");
        System.out.println("数量"+beanContainer.size());
    }

    @DisplayName("获取bean")
    @Order(2)
    @Test
    public void getBeanTset(){
        MainPageController mainPageController = (MainPageController) beanContainer.getBean(MainPageController.class);
        Assertions.assertEquals(true, mainPageController instanceof MainPageController);
    }

    @DisplayName("getClassesByAnnotation")
    @Order(3)
    @Test
    public void getClassesByAnnotation(){
        Set<Class<?>> set = beanContainer.getClassesByAnnotation(Service.class);
        System.out.println("getClassesByAnnotation:");
        for (Class<?> clazz: set){
            System.out.println(clazz.getName());
        }
    }


    @DisplayName("getClassesBySuperTest")
    @Order(4)
    @Test
    public void getClassesBySuperTest(){
        Set<Class<?>> set = beanContainer.getClassesBySuper(HeadLineService.class);
        System.out.println("getClassesBySuperTest:");
        for (Class<?> clazz: set){
            System.out.println(clazz.getName());
        }
    }





}
