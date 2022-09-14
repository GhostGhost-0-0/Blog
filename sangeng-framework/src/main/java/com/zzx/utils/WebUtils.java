package com.zzx.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @program: SGBlog
 * @Description web 工具类
 * @Author: 那个小楠瓜
 * @create: 2022-02-24 15:59
 **/
public class WebUtils {

    /**
     * 将字符串渲染到客户端
     * @param response 渲染对象
     * @param string 待渲染的字符串
     */
    public static void renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置下载请求头
     * @param fileName
     * @param response
     * @throws UnsupportedEncodingException
     */
    public static void setDownLoadHeader(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment; filename=" + fName);
    }

}
