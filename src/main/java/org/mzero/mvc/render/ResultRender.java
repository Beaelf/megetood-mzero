package org.mzero.mvc.render;

import org.mzero.mvc.RequestProcessorChain;

/**
 * 渲染请求结果
 *
 * @author chengdong.lei@hand-china.com 2020/08/16 17:19
 */
public interface ResultRender {
    // 执行渲染
    void render(RequestProcessorChain requestProcessorChain) throws Exception;
}
