//package com.adachina.common.util.excel;
//
//import com.mgzf.sdk.log.Logger;
//import com.mgzf.sdk.log.LoggerFactory;
//import com.mgzf.sdk.util.exception.SdkMogoErrorCode;
//import com.mgzf.sdk.util.exception.SdkMogoException;
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.RichTextString;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//import org.apache.poi.xssf.usermodel.XSSFRichTextString;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.lang.reflect.InvocationTargetException;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Description
// * @Author litianlong
// * @Date 2019-03-22 11:28
// */
//public class ExcelExportUtil {
//
//    private static final Logger logger = LoggerFactory.getLogger(ExcelExportUtil.class);
//
//    private SXSSFWorkbook sxssfWorkbook;
//    private Sheet sheet;
//
//    public Sheet getSheet() {
//        return sheet;
//    }
//
//    public SXSSFWorkbook getSxssfWorkbook() {
//        return sxssfWorkbook;
//    }
//
//    /**
//     * 创建book and sheet
//     * 并设置超过1000行的数据需要导出到磁盘，减少内存使用率
//     * 设置压缩临时文件，减少磁盘使用
//     */
//    private void initWorkbook() {
//        // keep 1000 rows in memory, exceeding rows will be flushed to disk
//        sxssfWorkbook = new SXSSFWorkbook(1000);
//        //压缩临时文件，否则会将空间占的很大，20M会产生1G以上的大文件
//        sxssfWorkbook.setCompressTempFiles(true);
//        sheet = sxssfWorkbook.createSheet();
//        //只设置默认的宽度，不要再设置全部的宽度值了
//        sheet.setDefaultColumnWidth((short) 15);
//    }
//
//    /**
//     * 创建表头
//     *
//     * @param headTitlesMap KEY：表头英文名称，Value:表头中文名
//     * @param columnWidthMap 列宽度：KEY：表头英文名称，Value:表头列宽（字符数）
//     * @return
//     */
//    public SXSSFWorkbook writeHead(Map<String, String> headTitlesMap, Map<String,Integer> columnWidthMap) {
//        initWorkbook();
//        org.apache.poi.ss.usermodel.Row row = this.getSheet().createRow(0);
//        int i = 0;
//        Cell cell;
//        for (Map.Entry<String, String> entry : headTitlesMap.entrySet()) {
//            cell = row.createCell(i);
//            RichTextString richTextString = new XSSFRichTextString(entry.getValue());
//            cell.setCellValue(richTextString);
//            logger.debug("i:" + i + ",key :" + entry.getValue());
//            //设置列宽
//            if(columnWidthMap.containsKey(entry.getKey())) {
//                sheet.setColumnWidth(i,columnWidthMap.get(entry.getKey())*256);
//            }
//            i++;
//        }
//        return sxssfWorkbook;
//    }
//
//    /**
//     * 输出数据
//     * 所有的输出格式都是字符串，如果需要处理输出格式的问题，请将数据处理好再输出
//     * @param dataList     输出的数据列表
//     * @param headTitleMap 表头,英文名与中文名的对照 如：<userId,用户编号>
//     * @param rowStart     开始的行数
//     * @param <T>          数据Bean
//     * @return
//     * @throws SdkMogoException
//     */
//    public <T> Integer writeData(List<T> dataList, Map<String, String> headTitleMap, Integer rowStart) throws SdkMogoException {
//        int rownum = rowStart;
//        logger.info("开始进入导出, dataSize:{}", dataList.size());
//        for (T data : dataList) {
//            org.apache.poi.ss.usermodel.Row row = sheet.createRow(rownum);
//            int columnum = 0;
//            String value = "";
//            for (Map.Entry<String, String> entry : headTitleMap.entrySet()) {
//                logger.debug("columnum:{}", columnum);
//                Cell cell = row.createCell(columnum);
//                logger.debug("filed:{}", entry.getKey());
//                try {
//                    // 设置所有的单元格为字符串
//                    value = BeanUtils.getProperty(data, entry.getKey());
//                    logger.debug("value:{}", value);
//                    cell.setCellValue(value);
//                } catch (IllegalAccessException e) {
//                    throw new SdkMogoException(SdkMogoErrorCode.SYS_ERROR, "无权限取到类中的字段", e);
//                } catch (InvocationTargetException e) {
//                    throw new SdkMogoException(SdkMogoErrorCode.SYS_ERROR, "调用目标错误", e);
//                } catch (NoSuchMethodException e) {
//                    throw new SdkMogoException(SdkMogoErrorCode.SYS_ERROR, "数据实体无此方法", e);
//                }
//                columnum++;
//            }
//            rownum++;
//        }
//        return rownum;
//    }
//
//    /**
//     * 由于要将临时文件写到实体的物理文件中去，视导出文件的大小花时间不同
//     *
//     * @param fileName 文件的绝对路径名称，如：d:/downloads/2th quarter.xlsx
//     * @return 文件的绝对路径，下载时使用
//     */
//    public String saveExcel(String fileName) {
//        logger.info("保存导出的Excel到临时目录：{}", fileName);
//        try {
//            FileOutputStream out = new FileOutputStream(fileName);
//            sxssfWorkbook.write(out);
//            out.close();
//            // dispose of temporary files backing this workbook on disk
//            sxssfWorkbook.dispose();
//            logger.info("保存导出的Excel到临时目录：{},完成。", fileName);
//        }catch (IOException e) {
//            logger.error("保存生成的Excel文件失败：{}", e);
//        }
//        return fileName;
//    }
//
//    /**
//     * 输出到输出流
//     * @param outputStream
//     * @return
//     */
//    public Integer toOutputStrean(OutputStream outputStream) {
//        try {
//            sxssfWorkbook.write(outputStream);
//            outputStream.close();
//            // dispose of temporary files backing this workbook on disk
//            sxssfWorkbook.dispose();
//            outputStream.close();
//            outputStream.flush();
//            logger.info("导出的Excel完成。");
//            return 0;
//        }catch (IOException e) {
//            logger.error("导出Excel文件失败", e);
//            return 1;
//        }
//    }
//}
