package org.mzero.mvc.render;

import org.mzero.mvc.RequestProcessorChain;

/**
 * 渲染请求结果
 *
 * @author Lei Chengdong
 * @date 2020/12/16
 */
public interface ResultRender {
    // 执行渲染
    void render(RequestProcessorChain requestProcessorChain) throws Exception;
}
