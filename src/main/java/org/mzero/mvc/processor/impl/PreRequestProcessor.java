package org.mzero.mvc.processor.impl;

import org.mzero.mvc.RequestProcessorChain;
import org.mzero.mvc.processor.RequestProcessor;

/**
 * 请求预处理，包括编码以及路径处理
 *
 * @author chengdong.lei@hand-china.com 2020/08/16 17:02
 */
public class PreRequestProcessor implements RequestProcessor {

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        return true;
    }
}
