package com.megetood.controller;

import com.megetood.controller.superadmin.HeadLineOperationController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. 拦截所有请求
 * 2. 解析所有请求
 * 3. 派发请求给controller处理
 */
@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp){
        System.out.println("request path is " + req.getServletPath());
        System.out.println("request method is " + req.getMethod());

        if ("/frontend/getmainpageinfo".equals(req.getServletPath()) && "GET".equals(req.getMethod())) {
            new MainPageController().getMainPageInfo(req, resp);
        }
        if ("/superadmin/addheadline".equals(req.getServletPath()) && "POST".equals(req.getMethod())) {
            new HeadLineOperationController().addHeadLine(req,resp);
        }
    }
}
