package org.mzero.mvc.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.mzero.core.BeanContainer;
import org.mzero.mvc.RequestProcessorChain;
import org.mzero.mvc.annotation.ReqeustMapping;
import org.mzero.mvc.annotation.RequestParam;
import org.mzero.mvc.annotation.ResponseBody;
import org.mzero.mvc.processor.RequestProcessor;
import org.mzero.mvc.render.ResultRender;
import org.mzero.mvc.render.impl.JsonResultRender;
import org.mzero.mvc.render.impl.ResourceNotFoundResultRender;
import org.mzero.mvc.render.impl.ViewResultRender;
import org.mzero.mvc.type.ControllerMethod;
import org.mzero.mvc.type.RequestPathInfo;
import org.mzero.util.ConverterUtil;
import org.mzero.util.ValidationUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Controller请求处理器
 *
 * @author Lei Chengdong
 * @date 2020/12/16
 */
@Slf4j
public class ControllerRequestProcessor implements RequestProcessor {
    /**
     * IOC容器
     */
    private BeanContainer beanContainer;
    /**
     * 请求和controller方法的映射集合
     */
    private Map<RequestPathInfo, ControllerMethod> pathControllerMethodMap = new ConcurrentHashMap<>();

    public ControllerRequestProcessor() {
        this.beanContainer = BeanContainer.getInstance();
        Set<Class<?>> requestMappingSet = beanContainer.getClassesByAnnotation(ReqeustMapping.class);
        initPathControllerMethodMap(requestMappingSet);
    }

    private void initPathControllerMethodMap(Set<Class<?>> requestMappingSet) {
        if (ValidationUtil.isEmpty(requestMappingSet)) {
            return;
        }
        // 1. 遍历所有被@RequestMapping标记的类，获取类上面注解的属性值作为一级路径
        for (Class<?> requestMappingClass : requestMappingSet) {
            ReqeustMapping requestMapping = requestMappingClass.getAnnotation(ReqeustMapping.class);
            String basePath = requestMapping.value();
            if (!basePath.startsWith("/")) {
                basePath = "/" + basePath;
            }
            // 2. 遍历类里所有被@RequestMapping标记的方法，获取方法上面该注解的值，作为二级路径
            Method[] methods = requestMappingClass.getDeclaredMethods();
            if (ValidationUtil.isEmpty(methods)) {
                continue;
            }
            for (Method method : methods) {
                if (method.isAnnotationPresent(ReqeustMapping.class)) {
                    ReqeustMapping methodRequest = method.getAnnotation(ReqeustMapping.class);
                    String methodPath = methodRequest.value();
                    if (!methodPath.startsWith("/")) {
                        methodPath = "/" + methodPath;
                    }
                    String url = basePath + methodPath;
                    // 3. 解析方法里被@RequestParam标记的参数
                    // 获取该注解的属性值
                    // 获取被注解标记的参数的数据类型，简历参数名和类型映射
                    HashMap<String, Class<?>> methodParams = new HashMap<>();
                    Parameter[] parameters = method.getParameters();
                    if (!ValidationUtil.isEmpty(parameters)) {
                        for (Parameter parameter : parameters) {
                            RequestParam param = parameter.getAnnotation(RequestParam.class);
                            // 所有Controller的方法参数都要使用@RequestParam注解标记
                            if (param == null) {
                                throw new RuntimeException("The parameter must have @RequestParam");
                            }
                            methodParams.put(param.value(), parameter.getType());
                        }
                    }
                    // 4. 将获取到的信息封装成RequestPathInfo实例和ControllerMethod实例，放置到映射表里
                    String httpMethod = String.valueOf(methodRequest.method());
                    RequestPathInfo requestPathInfo = new RequestPathInfo(httpMethod, url);
                    if (this.pathControllerMethodMap.containsKey(requestPathInfo)) {
                        log.warn("duplicate url:{} registration, current class {} method {} will override the former one",
                                requestPathInfo.getHttpPath(), requestMappingClass.getName(), method.getName());
                    }
                    ControllerMethod controllerMethod = new ControllerMethod(requestMappingClass, method, methodParams);
                    this.pathControllerMethodMap.put(requestPathInfo, controllerMethod);

                }
            }
        }
    }


    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        // 1. 解析HttpServletRequest的请求方法，请求路径，获取对应的ControllerMethod实例
        String method = requestProcessorChain.getRequestMethod();
        String path = requestProcessorChain.getRequestPath();
        ControllerMethod controllerMethod = this.pathControllerMethodMap.get(new RequestPathInfo(method, path));
        if (controllerMethod == null) {
            requestProcessorChain.setResultRender(new ResourceNotFoundResultRender(method, path));
            return false;
        }
        // 2. 解析请求参数，并传递给获取到的ControllerMethod实例去执行
        Object result = invokeControllerMethod(controllerMethod, requestProcessorChain.getRequest());
        // 3. 根据处理结果，选择对应的render进行渲染
        setResultRender(result, controllerMethod, requestProcessorChain);
        return true;
    }

    private void setResultRender(Object result, ControllerMethod controllerMethod, RequestProcessorChain requestProcessorChain) {
        if (result == null) {
            return;
        }
        ResultRender resultRender;
        boolean isJson = controllerMethod.getInvokeMethod().isAnnotationPresent(ResponseBody.class);
        if (isJson) {
            resultRender = new JsonResultRender(result);
        } else {
            resultRender = new ViewResultRender(result);
        }
        requestProcessorChain.setResultRender(resultRender);
    }

    private Object invokeControllerMethod(ControllerMethod controllerMethod, HttpServletRequest request) {
        // 1. 从请求里获取GET或者POST的参数名以及其对应的值
        Map<String, String> requestParamMap = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> parameter : parameterMap.entrySet()) {
            if (!ValidationUtil.isEmpty(parameter.getValue())) {
                // 只支持一个参数对应一个值的形式
                requestParamMap.put(parameter.getKey(), parameter.getValue()[0]);
            }
        }
        // 2. 根据获取到的请求参数名以及其对应的值，以及controllerMethod里面的参数和类型的映射关系，去实例化出方法对应的参数
        List<Object> methodParams = new ArrayList<>();
        Map<String, Class<?>> methodParamMap = controllerMethod.getMethodParameters();
        for (String paramName : methodParamMap.keySet()) {
            Class<?> type = methodParamMap.get(paramName);
            String reqeustValue = requestParamMap.get(paramName);
            Object value;
            // 只支持对String以及基本数据类型和其包装类型
            if (reqeustValue == null) {
                // 将请求里的参数值转换成适配于参数类型的空值
                value = ConverterUtil.primitiveNull(type);
            } else {
                value = ConverterUtil.convert(type, reqeustValue);
            }
            methodParams.add(value);
        }
        // 3. 执行Controller里面对应的方法并返回结果
        Object controller = beanContainer.getBean(controllerMethod.getControllerClass());
        Method invokeMethod = controllerMethod.getInvokeMethod();
        invokeMethod.setAccessible(true);
        Object result;
        try {
            if (methodParams.size() == 0) {
                result = invokeMethod.invoke(controller);
            } else {
                result = invokeMethod.invoke(controller, methodParams.toArray());
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            // 调用异常
            // 通过getTargetException获取执行方法抛出的异常
            throw new RuntimeException(e.getTargetException());
        }
        return result;
    }
}
