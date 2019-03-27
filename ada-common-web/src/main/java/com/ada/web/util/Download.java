package com.ada.web.util;
/**
 * Created by lxh on 2017/11/12.
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author lxh
 * @brief： 功能概要描述
 * @details: 详细描述
 * @Date ： 2017/11/12
 */
public class Download {
    private static final Logger logger = LoggerFactory.getLogger(Download.class);

    /**
     * 通用的下载文件方法
     *
     * @param response
     * @param fileName   文件的绝对路径名称，如：d:/downloads/2th quarter.xlsx
     * @param fileNameCn 文件中文名称，如：第二季度报表.xlsx
     */
    public static void fileDownload(HttpServletResponse response, String fileName, String fileNameCn) {
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileNameCn);
        //通过文件路径获得File对象(假如此路径中有一个download.pdf文件)
        File file = new File(fileName);
        try (
                FileInputStream inputStream = new FileInputStream(file);
                //3.通过response获取ServletOutputStream对象(out)
                ServletOutputStream out = response.getOutputStream();
        ) {
            int index = 0;
            byte[] buffer = new byte[512];
            while ((index = inputStream.read(buffer)) != -1) {
                //4.写到输出流(out)中
                out.write(buffer, 0, index);
            }
        } catch (IOException e) {
            logger.error("下载文件错误：", e);
        }
    }

    private Download() {
        throw new IllegalStateException("Utility class");
    }
}
