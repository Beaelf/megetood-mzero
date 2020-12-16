package org.mzero.mvc.processor.impl;

import org.mzero.mvc.RequestProcessorChain;
import org.mzero.mvc.processor.RequestProcessor;

/**
 * 请求预处理，包括编码以及路径处理
 *
 * @author Lei Chengdong
 * @date 2020/12/16
 */
public class PreRequestProcessor implements RequestProcessor {

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        return true;
    }
}
