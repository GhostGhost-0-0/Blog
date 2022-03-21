package com.zzx.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @program: SGBlog
 * @Description
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

    public static void setDownLoadHeader(String filename, ServletContext context, HttpServletResponse response) throws UnsupportedEncodingException {
        String mimeType = context.getMimeType(filename);//获取文件的 mime 类型
        response.setHeader("content-type", mimeType);
        String fname = URLEncoder.encode(filename, "UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=" + fname);

        //response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //response.setCharacterEncoding("utf-8");
    }
}
