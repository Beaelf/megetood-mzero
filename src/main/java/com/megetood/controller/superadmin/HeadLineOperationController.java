package com.megetood.controller.superadmin;

import com.megetood.entity.bo.HeadLine;
import com.megetood.entity.dto.Result;
import com.megetood.service.solo.HeadLineService;
import lombok.extern.slf4j.Slf4j;
import org.mzero.core.annotation.Controller;
import org.mzero.core.inject.annotation.Autowired;
import org.mzero.mvc.annotation.ReqeustMapping;
import org.mzero.mvc.annotation.RequestParam;
import org.mzero.mvc.annotation.ResponseBody;
import org.mzero.mvc.type.ModelAndView;
import org.mzero.mvc.type.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Controller
@ReqeustMapping(value = "/headline")
public class HeadLineOperationController {
    @Autowired
    private HeadLineService headLineService;

    @ReqeustMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addHeadLine(@RequestParam("lineName") String lineName,
                                    @RequestParam("linelink") String linelink,
                                    @RequestParam("lineImg") String lineImg,
                                    @RequestParam("priority") Integer priority) {
        HeadLine headLine = HeadLine
                .builder()
                .lineName(lineName)
                .lineLink(linelink)
                .lineImg(lineImg)
                .priority(priority).build();
        Result<Boolean> result = headLineService.addHeadLine(headLine);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("addheadline.jsp").addViewData("result",result);
        return modelAndView;
    }

    @ReqeustMapping(value = "/remove", method = RequestMethod.GET)
    public void removeHeadLine() {
        System.out.println("删除headline");
    }

    public Result<Boolean> modifyHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        // TODO : 参数处理
        return headLineService.modifyHeadLine(new HeadLine());
    }

    public Result<HeadLine> queryHeadLineById(HttpServletRequest req, HttpServletResponse resp) {
        // TODO : 参数处理
        return headLineService.queryHeadLineById(1);
    }

    @ReqeustMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<HeadLine>> queryHeadLine() {
        // TODO : 参数处理
        return headLineService.queryHeadLine(null, 1, 100);
    }

}
