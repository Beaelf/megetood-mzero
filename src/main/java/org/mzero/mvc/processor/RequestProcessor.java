package org.mzero.mvc.processor;

import org.mzero.mvc.RequestProcessorChain;

/**
 * 请求执行器
 *
 * @author chengdong.lei@hand-china.com 2020/08/16 17:00
 */
public interface RequestProcessor {
    boolean process(RequestProcessorChain requestProcessorChain) throws Exception;
}
