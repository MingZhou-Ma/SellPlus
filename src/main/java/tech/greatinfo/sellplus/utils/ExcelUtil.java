package tech.greatinfo.sellplus.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 描述：
 *
 * @author Badguy
 */
public class ExcelUtil {

    public static void exportXls(String sheetName, String titleValue, String[] headTitle, String[][] data, HttpServletResponse response) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 设置列宽
        for (int i = 0; i < headTitle.length; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }
        // 设置标题行列合并
        //sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));

        // 标题样式
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont titleFont = wb.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 20);
        titleStyle.setFont(titleFont);

        // 标题行
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(headTitle.length / 2);
        titleCell.setCellValue(titleValue);
        titleCell.setCellStyle(titleStyle);

        // 表头样式
        HSSFCellStyle headStyle = wb.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont headFont = wb.createFont();
        headFont.setBold(true);
        headStyle.setFont(headFont);

        // 表头行
        HSSFRow headRow = sheet.createRow(1);
        HSSFCell headCell;
        for (int i = 0; i < headTitle.length; i++) {
            headCell = headRow.createCell(i);
            headCell.setCellValue(headTitle[i]);
            headCell.setCellStyle(headStyle);
        }

        // 内容样式
        HSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);

        // 内容行
        for (String[] aData : data) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            for (int j = 0; j < aData.length; j++) {
                HSSFCell dataCell = dataRow.createCell(j);
                dataCell.setCellValue(aData[j]);
                dataCell.setCellStyle(dataStyle);
            }
        }

        //输出Excel文件
        response.setHeader("Content-disposition", "attachment; filename=details.xls");
        response.addHeader("Cache-Control", "no-cache");

        OutputStream output = response.getOutputStream();
        wb.write(output);
        output.flush();
        output.close();
    }
}
