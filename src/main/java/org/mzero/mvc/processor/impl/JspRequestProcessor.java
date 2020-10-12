package org.mzero.mvc.processor.impl;

import lombok.NoArgsConstructor;
import org.mzero.mvc.RequestProcessorChain;
import org.mzero.mvc.processor.RequestProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * jsp资源请求处理
 *
 * @author chengdong.lei@hand-china.com 2020/08/16 17:02
 */
@NoArgsConstructor
public class JspRequestProcessor implements RequestProcessor {

    /**
     * jsp请求的RequestDispatcher的名称
     */
    private static final String JSP_SERVLET = "jsp";

    /**
     * jsp请求资源路径前缀
     */
    private static final String JSP_RESOURCE_PREFIX="/templates/";

    /**
     * jsp的RequestDispatcher
     */
    private RequestDispatcher jspServlet;

    public JspRequestProcessor(ServletContext servletContext) {
        this.jspServlet = servletContext.getNamedDispatcher(JSP_SERVLET);
        if(null == this.jspServlet){
            throw new RuntimeException("There is no jsp servlet");
        }
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        if (isJspResource(requestProcessorChain.getRequestPath())){
            jspServlet.forward(requestProcessorChain.getRequest(),requestProcessorChain.getResponse());
            return false;
        }
        return true;
    }

    /**
     * 请求的资源类型是否为jsp
     * @param path
     * @return
     */
    private boolean isJspResource(String path) {
        return path.startsWith(JSP_RESOURCE_PREFIX);
    }
}
