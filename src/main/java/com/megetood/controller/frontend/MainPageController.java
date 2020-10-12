package com.megetood.controller.frontend;

import com.megetood.entity.dto.MainPageInfoDTO;
import com.megetood.entity.dto.Result;
import com.megetood.service.combine.HeadLineShopCategoryCombineService;
import org.mzero.core.annotation.Controller;
import org.mzero.core.inject.annotation.Autowired;
import org.mzero.mvc.annotation.ReqeustMapping;
import org.mzero.mvc.type.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@ReqeustMapping(value = "/main")
public class MainPageController {
    @Autowired("HeadLineShopCategoryCombineServiceImpl2")
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp){
        return headLineShopCategoryCombineService.getMainPageInfo();
    }

    public HeadLineShopCategoryCombineService getHeadLineShopCategoryCombineService() {
        return headLineShopCategoryCombineService;
    }

    public void setHeadLineShopCategoryCombineService(HeadLineShopCategoryCombineService headLineShopCategoryCombineService) {
        this.headLineShopCategoryCombineService = headLineShopCategoryCombineService;
    }
    @ReqeustMapping(value = "test",method = RequestMethod.GET)
    public void throwException(){
        throw new RuntimeException("测试抛出异常");
    }
}
