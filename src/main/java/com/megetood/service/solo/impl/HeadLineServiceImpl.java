package com.megetood.service.solo.impl;

import com.alibaba.fastjson.JSON;
import com.megetood.entity.bo.HeadLine;
import com.megetood.entity.dto.Result;
import com.megetood.service.solo.HeadLineService;
import lombok.extern.slf4j.Slf4j;
import org.mzero.core.annotation.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Override
    public Result<Boolean> addHeadLine(HeadLine headLine) {
        log.info("--------------addHeaderLine执行-----------------");
        log.info(JSON.toJSONString(headLine,true));
        return new Result<Boolean>().ok(200,true);
    }

    @Override
    public Result<Boolean> removeHeadLine(int headLineId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyHeadLine(HeadLine headLine) {
        return null;
    }

    @Override
    public Result<HeadLine> queryHeadLineById(int headLineId) {
        return null;
    }

    @Override
    public Result<List<HeadLine>> queryHeadLine(HeadLine headLineCondition, int pageIndex, int pageSize) {
        List<HeadLine> headLineList = new ArrayList<>();
        HeadLine headLine = HeadLine.builder().lineId(1L).lineName("头条1").lineLink("www.toutiao.com").build();
        HeadLine headLine2 = HeadLine.builder().lineId(2L).lineName("头条2").lineLink("www.baidu.com").build();
        headLineList.add(headLine);
        headLineList.add(headLine2);
        return new Result<List<HeadLine>>().ok(200,headLineList);
    }
}
