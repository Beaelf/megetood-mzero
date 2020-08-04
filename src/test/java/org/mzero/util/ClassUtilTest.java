package org.mzero.util;


import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class ClassUtilTest {

    @DisplayName("提取目标类的方法：extracPackageClassTest")
    @Test
    public void extracPackageClassTest(){
        Set<Class<?>> classSet = ClassUtil.extractPackageClass("com.megetood.entity");
        System.out.println(
                JSON.toJSONString(classSet,true));
        Assertions.assertEquals(4, classSet.size());
    }
}
