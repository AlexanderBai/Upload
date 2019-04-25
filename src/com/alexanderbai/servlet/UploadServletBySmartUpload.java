package com.alexanderbai.servlet;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description TODO
 * @Author AlexanderBai
 * @Data 2019/4/25 16:13
 **/
@javax.servlet.annotation.WebServlet(name = "UploadServletBySmartUpload",urlPatterns = "/UploadServletBySmartUpload")
public class UploadServletBySmartUpload extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //实例化组件
        SmartUpload smartUpload = new SmartUpload();

        //初始化上传操作
        smartUpload.initialize(this.getServletConfig(),request,response);

        try {
            //上传准备
            smartUpload.upload();
            //对于普通的数据，单纯用request对象是无法获取的，也是需要依赖smartUpload
            System.out.println(request.getParameter("username"));
            String userName=smartUpload.getRequest().getParameter("userName");
            String password=smartUpload.getRequest().getParameter("password");
            System.out.println(userName);
            System.out.println(password);
            //上传到uploadFile文件中
            smartUpload.save("uploadFile");

        } catch (SmartUploadException e) {
            e.printStackTrace();
        }
    }
}