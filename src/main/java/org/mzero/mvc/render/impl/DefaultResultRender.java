package org.mzero.mvc.render.impl;

import org.mzero.mvc.RequestProcessorChain;
import org.mzero.mvc.render.ResultRender;

/**
 * 默认渲染器
 *
 * @author Lei Chengdong
 * @date 2020/12/16
 */
public class DefaultResultRender implements ResultRender {

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().setStatus(requestProcessorChain.getRequestCode());
    }
}
