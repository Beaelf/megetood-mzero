package com.megetood.controller;

import com.megetood.entity.dto.MainPageInfoDTO;
import com.megetood.entity.dto.Result;
import com.megetood.service.combine.HeadLineShopCategoryCombineService;
import org.mzero.core.annotation.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainPageController {
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;
    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp){
        return headLineShopCategoryCombineService.getMainPageInfo();
    }
}
