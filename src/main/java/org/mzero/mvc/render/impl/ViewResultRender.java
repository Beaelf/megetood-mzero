package org.mzero.mvc.render.impl;

import org.mzero.mvc.RequestProcessorChain;
import org.mzero.mvc.render.ResultRender;
import org.mzero.mvc.type.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 页面渲染器
 *
 * @author chengdong.lei@hand-china.com 2020/08/16 17:21
 */
public class ViewResultRender implements ResultRender {

    public static final String VIEW_PATH = "/templates/";
    private ModelAndView modelAndView;

    public ViewResultRender(Object o) {
        if (o instanceof ModelAndView) {
            // 1. 如果入参类型是ModelAndView,则直接赋值给成员变量
            this.modelAndView = (ModelAndView) o;
        } else if (o instanceof String) {
            // 2. 如果入参类型是String,则视为视图，需要包装后再赋值给成员变量
            this.modelAndView = new ModelAndView().setView((String) o);
        }else {
            // 3. 其他情况则，直接抛出异常
            throw new RuntimeException("illegel request result type");
        }
    }

    /**
     * 将请求处理结果按视图路径进行展示

     * @param requestProcessorChain
     * @throws Exception
     */
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        HttpServletRequest request = requestProcessorChain.getRequest();
        HttpServletResponse response = requestProcessorChain.getResponse();
        String path = modelAndView.getView();
        Map<String, Object> model = modelAndView.getModel();
        for (Map.Entry<String,Object> entry:model.entrySet()){
            request.setAttribute(entry.getKey(),entry.getValue());
        }
        // JSP
        request.getRequestDispatcher(VIEW_PATH +path).forward(request,response);
    }
}
