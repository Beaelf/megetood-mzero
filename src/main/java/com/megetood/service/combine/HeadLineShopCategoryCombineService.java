package com.megetood.service.combine;

import com.megetood.entity.dto.MainPageInfoDTO;
import com.megetood.entity.dto.Result;

public interface HeadLineShopCategoryCombineService {
    Result<MainPageInfoDTO> getMainPageInfo();
}
