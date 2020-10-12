package org.mzero.mvc.type;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储处理完后的结果，以及显示该数据的视图
 *
 * @author chengdong.lei@hand-china.com 2020/08/17 3:42
 */
public class ModelAndView {
    /**
     * 页面所在的路径
     */
    @Getter
    private String view;

    /**
     * 页面的data数据
     */
    @Getter
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView setView(String view) {
        this.view = view;
        return this;
    }

    public ModelAndView addViewData(String attributeName, Object attributeValue){
        model.put(attributeName,attributeValue);
        return this;
    }
}
