package org.mzero.mvc;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mzero.mvc.processor.RequestProcessor;
import org.mzero.mvc.render.ResultRender;
import org.mzero.mvc.render.impl.DefaultResultRender;
import org.mzero.mvc.render.impl.InternalErrorResultRender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * 1. 以责任链模式执行注册的请求处理器
 * 2. 委派给特定的Render实例对处理后的结果进行渲染
 *
 * @author Lei Chengdong
 * @date 2020/12/16
 */
@Getter
@Setter
@Slf4j
public class RequestProcessorChain {

    /**
     * 请求处理器迭代器
     */
    private Iterator<RequestProcessor> requestProcessorIterator;

    /**
     * 请求request
     */
    private HttpServletRequest request;

    /**
     * 请求response
     */
    private HttpServletResponse response;

    /**
     * http请求方法
     */
    private String requestMethod;

    /**
     * http请求路径
     */
    private String requestPath;

    /**
     * http响应状态码
     */
    private int requestCode;

    /**
     * 请求结果渲染器
     */
    private ResultRender resultRender;


    public RequestProcessorChain(Iterator<RequestProcessor> iterator, HttpServletRequest req, HttpServletResponse resp) {
        this.requestProcessorIterator = iterator;
        this.request = req;
        this.response = resp;
        this.requestMethod = req.getMethod();
        this.requestPath = req.getPathInfo();
        this.requestCode = HttpServletResponse.SC_OK;
    }

    public void doRequestProcessorChain() {
        try {
            // 1. 通过迭代器遍历注册的请求处理器实现类列表
            while (requestProcessorIterator.hasNext()) {
                // 2. 直到某个请求处理器执行后返回为false为止
                if (!requestProcessorIterator.next().process(this)) {
                    break;
                }
            }
        } catch (Exception e) {
            // 3. 期间如果出现异常，就交由内部异常渲染器处理
            this.resultRender = new InternalErrorResultRender(e.getMessage());
            log.error("doRequestProcessorChain error:",e);
        }
    }

    public void doRender() {
        // 1. 如果请求处理实现类均为选择合适的渲染器，则使用默认的
        if(this.resultRender==null){
            this.resultRender = new DefaultResultRender();
        }
        // 2. 调用渲染器的render方法对结果进行渲染
        try {
            this.resultRender.render(this);
        } catch (Exception e) {
            log.error("doRender error:",e);
            throw new RuntimeException(e);
        }
    }
}
