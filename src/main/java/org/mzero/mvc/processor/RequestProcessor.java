package org.mzero.mvc.processor;

import org.mzero.mvc.RequestProcessorChain;

/**
 * 请求执行器
 *
 * @author Lei Chengdong
 * @date 2020/12/16
 */
public interface RequestProcessor {
    boolean process(RequestProcessorChain requestProcessorChain) throws Exception;
}
