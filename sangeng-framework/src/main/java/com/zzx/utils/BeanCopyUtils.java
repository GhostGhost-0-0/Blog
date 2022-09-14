package com.zzx.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: SGBlog
 * @Description Bean的拷贝工具类
 * @Author: 那个小楠瓜
 * @create: 2022-02-23 16:33
 **/
public class BeanCopyUtils {

    private BeanCopyUtils() {}

    public static <V> V copyBean(Object source,Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(source, result);
            //返回结果

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <O,V> List<V> copyBeanList(List<O> list,Class<V> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
