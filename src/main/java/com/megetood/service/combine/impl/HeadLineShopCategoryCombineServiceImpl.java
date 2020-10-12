package com.megetood.service.combine.impl;

import com.megetood.entity.bo.HeadLine;
import com.megetood.entity.bo.ShopCategory;
import com.megetood.entity.dto.MainPageInfoDTO;
import com.megetood.entity.dto.Result;
import com.megetood.service.combine.HeadLineShopCategoryCombineService;
import com.megetood.service.solo.HeadLineService;
import com.megetood.service.solo.ShopCategoryService;
import org.mzero.core.annotation.Service;
import org.mzero.core.inject.annotation.Autowired;

import java.util.List;

@Service
public class HeadLineShopCategoryCombineServiceImpl implements HeadLineShopCategoryCombineService {
    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Override
    public Result<MainPageInfoDTO> getMainPageInfo() {
        // 1. 获取头条列表
        HeadLine headLineCondition = new HeadLine();
        headLineCondition.setEnableStatus(1);
        Result<List<HeadLine>> headLineList = headLineService.queryHeadLine(headLineCondition, 1, 4);
        // 2. 获取店铺类别列表
        ShopCategory shopCategoryCondition = new ShopCategory();
        Result<List<ShopCategory>> shopCategoryList = shopCategoryService.queryShopCategory(shopCategoryCondition, 1, 100);
        // 3. 合并数据
        Result<MainPageInfoDTO> result = mergeMainPageInfoResult(headLineList, shopCategoryList);
        return result;
    }

    private Result<MainPageInfoDTO> mergeMainPageInfoResult(Result<List<HeadLine>> headLineList, Result<List<ShopCategory>> shopCategoryList) {
        MainPageInfoDTO result = new MainPageInfoDTO();
        return null;
    }
}
