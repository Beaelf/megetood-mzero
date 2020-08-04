package com.megetood.entity.dto;

import com.megetood.entity.bo.HeadLine;
import com.megetood.entity.bo.ShopCategory;
import lombok.Data;

@Data
public class MainPageInfoDTO {
    private HeadLine headLine;
    private ShopCategory shopCategory;
}
