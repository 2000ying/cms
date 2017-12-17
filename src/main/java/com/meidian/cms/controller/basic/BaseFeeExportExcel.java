package com.meidian.cms.controller.basic;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 运费导出excel公用的东西
 *
 * @author yangweiqiang
 * @date 2016/7/19
 */
public abstract class BaseFeeExportExcel {

    public Logger logger = Logger.getLogger(this.getClass());
    
    //显示的导出表的标题
    public String title;
    //导出表的列名
    public String[] rowName;
    //请求返回参数
    public HttpServletResponse response;

    public List<Object[]> dataList = new ArrayList<Object[]>();
    public HttpServletRequest request;
    public String selectedCityName;
    public String month;

    //构造方法，传入要导出的数据
    public BaseFeeExportExcel(String title, String[] rowName, List<Object[]> dataList, HttpServletResponse response, HttpServletRequest request, String selectedCityName, String month) {
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
        this.response = response;
        this.request = request;
        this.selectedCityName = selectedCityName;
        this.month = month;
    }

    /**
     * 描述: 每个模块实现自己的导出逻辑
     * @author yangweiqiang
     * @date   2016/7/19
     */
    public abstract void export() throws Exception;

    /**
     * 描述: 是否是IE
     * @author yangweiqiang
     * @date   2016/7/19
     */
    public boolean isIE(){
        String agent = request.getHeader("USER-AGENT");
        boolean isIE = (null != agent && -1 != agent.indexOf("MSIE") || null != agent
                && -1 != agent.indexOf("Trident")); //IE
        return isIE;
    }

    /**
     * 描述: 构建excel的表头
     * @author yangweiqiang
     * @date   2016/7/19
     * @param
     * @return
     */
    public Workbook setExcelTitle(){
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(title);                    // 创建工作表

        // 产生表格标题行
        Row titleRow = sheet.createRow(0);
        Cell cellTiltle = titleRow.createCell(0);

        //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
        HSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);//获取列头样式对象
        HSSFCellStyle style = getStyle(workbook);                    //单元格样式对象

        //设置导出的日期
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 2));
        cellTiltle.setCellStyle(columnTopStyle);
        cellTiltle.setCellValue("日期:" + month);

        // 定义导出所需列数
        int columnNum = rowName.length;
        Row rowRowName = sheet.createRow(2);                // 在索引2的位置创建行(最顶端的行开始的第二行)

        // 将列头设置到sheet的单元格中
        for (int n = 0; n < columnNum; n++) {
            Cell cellRowName = rowRowName.createCell(n);                //创建列头对应个数的单元格
            cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);                //设置列头单元格的数据类型
            HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
            cellRowName.setCellValue(text);                                    //设置列头单元格的值
            cellRowName.setCellStyle(columnTopStyle);                        //设置列头单元格样式
        }
        return workbook;
    }
    
    /**
     * 描述: 执行导出
     * @author yangweiqiang
     * @date   2016/7/19
     * @param 
     * @return 
     */
    public void doExport(Workbook workbook) throws Exception{
        if (workbook != null) {
            OutputStream out = null;
            try {
                String excelFileName = title + "-" + selectedCityName + "-" + month + ".xls";
                String fileName = "";
                if (isIE()) {
                    fileName = java.net.URLEncoder.encode(excelFileName, "UTF8");
                } else {
                    fileName = new String(excelFileName.getBytes("UTF-8"), "ISO-8859-1");
                }
                String headStr = "attachment; filename=\"" + fileName + "\"";
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition", headStr);
                out = response.getOutputStream();
                workbook.write(out);
            } catch (IOException e) {
                logger.error("导出失败", e);
            }finally{
                try
                {
                    logger.info("导出成功!关闭IO流~~~");
                    out.flush();
                    out.close();
                    workbook.close();
                }catch (IOException e)
                {
                    logger.error("IO流close失败!");
                }
            }
        }
    }
    
    /**
     * 描述: 设置列的宽度自动大小
     * @author yangweiqiang
     * @date   2016/7/19
     * @param sheet sheet 
     * @param columnNum 列的个数
     * @return 
     */
    public static void setSheetColumnWidthAutoResize(Sheet sheet,int columnNum){
        for (int colNum = 0; colNum < columnNum; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(colNum) != null) {
                    Cell currentCell = currentRow.getCell(colNum);
                    if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        int length = 0;
                        try {
                            length =20;// currentCell.getStringCellValue().getBytes().length;
                        } catch (Exception e) {
                        }
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            if (colNum == 0) {
                sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
            } else {
                sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
            }
        }
    }
    
    /**
     * yangweiqiang
     * 给excel添加数值类型数据校验
     *
     * @param sheet    sheet页
     * @param rangeAddressList
     *          需要校验的数据范围地址,因每个excel需要校验的数据范围不一样，所以需要自行设置
     * @param minValue 最小值
     * @param maxValue 最大值
     */
    public void addSheetValidationByNumberData(Sheet sheet, List<CellRangeAddress> rangeAddressList ,int minValue, int maxValue) {
        //限制只能输入数字
        HSSFDataValidationHelper helper = (HSSFDataValidationHelper) sheet.getDataValidationHelper();

        String minInputAmount = String.valueOf(minValue);
        String maxInputAmount = String.valueOf(maxValue);
        DVConstraint numericConstraint = DVConstraint.createNumericConstraint(DataValidationConstraint.ValidationType.DECIMAL,
                DataValidationConstraint.OperatorType.BETWEEN, minInputAmount, maxInputAmount);

        CellRangeAddressList region = new CellRangeAddressList();
        for (CellRangeAddress address : rangeAddressList){
            region.addCellRangeAddress(address);
        }

        HSSFDataValidation validation = (HSSFDataValidation) helper.createValidation(numericConstraint, region);
        validation.createErrorBox("输入值非法", "数值型,请输入" + minInputAmount + "~" + maxInputAmount + "之间的数值!");
        validation.setShowErrorBox(true);

        sheet.addValidationData(validation);
    }

    /*
     * 列头单元格样式
     */
    public static HSSFCellStyle getColumnTopStyle(Workbook workbook) {

        // 设置字体
        HSSFFont font = (HSSFFont) workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 11);
        //字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = (HSSFCellStyle) workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }

    /*
   * 列数据信息单元格样式
   */
    public static HSSFCellStyle getStyle(Workbook workbook) {
        // 设置字体
        HSSFFont font = (HSSFFont) workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = (HSSFCellStyle) workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }
}
