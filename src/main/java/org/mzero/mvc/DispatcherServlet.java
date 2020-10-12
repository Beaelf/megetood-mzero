package org.mzero.mvc;

import com.megetood.controller.frontend.MainPageController;
import com.megetood.controller.superadmin.HeadLineOperationController;
import org.mzero.MzeroApplication;
import org.mzero.aop.v2.AspectWeaver;
import org.mzero.core.BeanContainer;
import org.mzero.core.inject.DependencyInjector;
import org.mzero.mvc.processor.RequestProcessor;
import org.mzero.mvc.processor.impl.ControllerRequestProcessor;
import org.mzero.mvc.processor.impl.JspRequestProcessor;
import org.mzero.mvc.processor.impl.PreRequestProcessor;
import org.mzero.mvc.processor.impl.StaticResourceRequestProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. 拦截所有请求
 * 2. 解析所有请求
 * 3. 派发请求给controller处理
 */
@WebServlet("/*")
public class DispatcherServlet extends HttpServlet {
    List<RequestProcessor> PROCESSOR=new ArrayList<>();

    @Override
    public void init(){
        // 1. 初始化容器
        MzeroApplication.run("com.megetood");
        // 2. 初始化请求处理责任链
        PROCESSOR.add(new PreRequestProcessor());
        PROCESSOR.add(new StaticResourceRequestProcessor(getServletContext()));
        PROCESSOR.add(new JspRequestProcessor(getServletContext()));
        PROCESSOR.add(new ControllerRequestProcessor());
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp){
        // 1. 创建责任链对象实例
        RequestProcessorChain requestProcessorChain = new RequestProcessorChain(PROCESSOR.iterator(), req, resp);
        // 2. 通过责任链模式依次调用请求处理器对请求进行处理
        requestProcessorChain.doRequestProcessorChain();
        // 3. 对处理结果进行渲染
        requestProcessorChain.doRender();
    }


}
