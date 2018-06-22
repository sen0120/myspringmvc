package com.test.poi;

import com.alibaba.fastjson.JSONObject;
import com.hus.crypto.AesUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class TestPoi {

    private final static String excel2003L = ".xls"; // 2003- 版本的excel  
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel

    public static void main(String[] args) throws Exception {
//        TestPoi testPoi = new TestPoi();
//        File file = new File("/Users/fanyun/Documents/尊享理财产品录入_20180312_v1.xlsx");
        //"borrower":"","idCardNo":"","borrowUsage":"",
        //"borrower":"","repaymentSource":"","borrowUsage":"",
        System.out.println(AesUtil.decrypt("WobHzVBcXC8Dpks6XGcyWA==", AesUtil.getKeystore()));
//        System.out.println(decrypt("UdUJ4dm2r2/r4F2bi53sNmks7elnKRYgSyHC+BuMYfY="));
//        List<Object> listByExcel = testPoi.getListByExcel(new FileInputStream(file), "*.xlsx");
//        System.out.println(listByExcel);

//        System.out.println(AesUtil.decrypt("LJQQbHxl3OGrbx/DlILPQQ2o/lFJB9sWDKjXTnvh0q4=", AesUtil.getKeystore()));
//        System.out.println(AesUtil.decrypt("4hhLNRQOtWmnxVF1V/nhalEx/Ms3u5ZvBZ0kdwPKG6I=", AesUtil.getKeystore()));
    }

    //CommonUtils.decrypt
    public List<Object> getListByExcel(InputStream in, String fileName) throws Exception {
        // 创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        List<Object> list = new ArrayList<Object>();

        // 遍历Excel中所有的sheet
        sheet = work.getSheetAt(0);
        // 遍历当前sheet中的所有行
        for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum() + 1; j++) { // 这里的加一是因为下面的循环跳过取第一行表头的数据内容了
            if (j == 0) continue;
            row = sheet.getRow(j);

            // 遍历所有的列
            HonourProdProduct vo = new HonourProdProduct();
            for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                cell = row.getCell(y);
                this.indexToValue(vo, cell, y);
            }
            vo.setStatus(0);
            list.add(vo);
            System.out.println(JSONObject.toJSONString(vo));
        }
        work.close();

        return list;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr); // 2003-  
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr); // 2007+  
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    public void indexToValue(HonourProdProduct vo, Cell cell, int index) {
        switch (index) {
            case 0:
                vo.setId(cell.getStringCellValue().replace("尊享系列", ""));
                vo.setName(cell.getStringCellValue());
                break;
            case 1:
                vo.setPriority("" + Double.valueOf(cell.getNumericCellValue()).intValue());
                break;
            case 2:
                vo.setProfit(new BigDecimal(String.valueOf(cell.getNumericCellValue())));
                break;
            case 3:
                vo.setPeriod("D" + StringUtils.leftPad(
                        new BigDecimal(cell.getNumericCellValue()).setScale(0, RoundingMode.DOWN).toString(), 3, "0"));
                vo.setPeriodDaysInt(Double.valueOf(cell.getNumericCellValue()).intValue());
                break;
            case 4:
                vo.setStartBenefit(cell.getDateCellValue());
                break;
            case 5:
                vo.setEndBenefit(cell.getDateCellValue());
                break;
            case 6:
                vo.setRaiseAmount(new BigDecimal(cell.getNumericCellValue()));
                vo.setRemainAmount(new BigDecimal(cell.getNumericCellValue()));
                break;
            case 7:
                vo.setLeastAmount(new BigDecimal(cell.getNumericCellValue()));
                break;
            case 8:
                vo.setSteppingAmount(new BigDecimal(cell.getNumericCellValue()));
                break;
            case 9:
                vo.setRedeemTypeDesc(cell.getStringCellValue());
                break;
            case 10:
                vo.setNote(cell.getRichStringCellValue().getString());
                break;
            default:
        }
    }

} 