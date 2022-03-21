package com.zzx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @program: SGBlog
 * @Description
 * @Author: 那个小楠瓜
 * @create: 2022-03-02 18:46
 **/
public class PathUtils {

    /**
     * @Description 生成唯一图片名称
     * @param fileName
     * @return 云服务器 fileName
     */
    public static String generateFilePath(String fileName) {
        //根据日期生成路径 2022/3/2
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String dataPath = sdf.format(new Date());
        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");
        // test.jpg -> .jpg
        String fileType = fileName.substring(index);
        //生成上传至云服务器的路径
        return new StringBuilder().append(dataPath).append(uuid).append(fileType).toString();
    }
}
