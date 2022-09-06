package com.zzx.domain.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.zzx.domain.ResponseResult;
import com.zzx.domain.entity.excel.CategoryExcel;
import com.zzx.enums.AppHttpCodeEnum;
import com.zzx.utils.WebUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.domain.service.impl
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-09-05 16:53
 * @Description: 导出 Excel 的实现类
 * @Version: 1.0
 */
@Service
public class ExportExcelService {

    /**
     * 导出数据为 Excel 表格
     * @param fileName Excel 表格的名称
     * @param response 响应
     * @param excelList 要写入到 Excel 表格的数据
     */
    public void export(String fileName, HttpServletResponse response, List excelList) {
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader(fileName, response);
            //写入 Excel 中
            EasyExcel.write(response.getOutputStream(), CategoryExcel.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(excelList);
        } catch (Exception e) {
            //失败时返回 JSON 数据
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, "导出失败");
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}
