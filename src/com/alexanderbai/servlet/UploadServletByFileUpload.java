package com.alexanderbai.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Description UploadServletByFileUpload
 * @Author AlexanderBai
 * @Data 2019/4/25 16:13
 **/
@javax.servlet.annotation.WebServlet(name = "UploadServletByFileUpload",urlPatterns = "/UploadServletByFileUpload")
public class UploadServletByFileUpload extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取解析器工厂
        DiskFileItemFactory diskFileItemFactory=new DiskFileItemFactory();
        //2、获取解析器
        ServletFileUpload servletFileUpload=new ServletFileUpload(diskFileItemFactory);

        //设置上传的编码
        servletFileUpload.setHeaderEncoding("UTF-8");

        //判断上传表单的类型
        if (!servletFileUpload.isMultipartContent(request)) {
            //上传为普通表单，
            System.out.println(request.getParameter("userName"));
            System.out.println(request.getParameter("password"));
            return;
        }
        //为上传文件表单，调用解析器解析器解析上传数据
        try {
            List<FileItem> fileItems=servletFileUpload.parseRequest(request);

            //遍历List，得到用于封装第一个上传输入项数据fileItem对象
            for (FileItem fileItem : fileItems) {

                if (fileItem.isFormField()) {
                    //得到的是普通的输入项
                    String name = fileItem.getFieldName();//获取输入项的名称
                    String value = fileItem.getString("UTF-8");
                    System.out.println(name + "=" + value);
                } else {
                    //得到上传输入项
                    String fileName=fileItem.getName();//获取上传输入名
                    fileName=fileName.substring(fileName.lastIndexOf("\\" )+1);
                    InputStream inputStream = fileItem.getInputStream();//获取上传数据

                    int len=0;
                    byte bytes[] = new byte[1024];

                    //新建一个目录
                    String savePath=this.getServletContext().getRealPath("/uploadFile");

                    //向upload目录中写入数据
                    FileOutputStream fileOutputStream=new FileOutputStream(savePath + "\\" + fileName);
                    while ((len = inputStream.read(bytes)) > 0) {
                        fileOutputStream.write(bytes,0,len);
                    }
                    inputStream.close();
                    fileOutputStream.close();
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }
}