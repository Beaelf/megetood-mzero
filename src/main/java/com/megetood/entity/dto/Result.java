package com.megetood.entity.dto;

import lombok.Data;

@Data
public class Result<T>{
    /**
     * 请求结果状态玛
     */
    private int code;
    /**
     * 请求结果详情
     */
    private String msg;
    /**
     * 请求返回结果集
     */
    private T data;
}
