package org.mzero.mvc.render.impl;

import org.mzero.mvc.RequestProcessorChain;
import org.mzero.mvc.render.ResultRender;

/**
 * 默认渲染器
 *
 * @author chengdong.lei@hand-china.com 2020/08/16 17:21
 */
public class DefaultResultRender implements ResultRender {

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().setStatus(requestProcessorChain.getRequestCode());
    }
}
