package org.mzero.mvc.render.impl;

import org.mzero.mvc.RequestProcessorChain;
import org.mzero.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;

/**
 * 资源找不到时使用的渲染器
 *
 * @author chengdong.lei@hand-china.com 2020/08/16 17:21
 */
public class ResourceNotFoundResultRender implements ResultRender {
    private String httpMethod;
    private String httpPath;

    public ResourceNotFoundResultRender(String method, String path) {
        this.httpMethod = method;
        this.httpPath = path;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND,
                "获取不到对应的请求资源：请求路径[" + httpPath + "] 请求方法[" + httpMethod + "]");
    }
}
