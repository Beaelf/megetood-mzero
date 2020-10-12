package org.mzero.mvc.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 待执行的Controller、方法实例和参数映射
 *
 * @author chengdong.lei@hand-china.com 2020/08/16 19:40
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ControllerMethod {
    /**
     * Controller对应的Class对象
     */
    private Class<?> controllerClass;

    /**
     * 执行的Controller方法实例
     */
    private Method invokeMethod;

    /**
     * 方法参数名及对应的参数类型
     */
    private Map<String, Class<?>> methodParameters;
}
