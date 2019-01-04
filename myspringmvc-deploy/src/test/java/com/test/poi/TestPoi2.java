package com.test.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestPoi2 {

    private final static String excel2003L = ".xls"; // 2003- 版本的excel  
    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel

    public static void main(String[] args) throws Exception {
//        sendGetRequest("https://activity.xxx.com//test/worldcup18/trade/succ/ordermq?tzOrderNo=18062913134578700010120350413641");
        a1();
    }

    private static void a1() throws Exception {
        TestPoi2 testPoi = new TestPoi2();
        File file = new File("/Users/fanyun/linshi/fun_201807021216.xlsx");
        //"borrower":"","idCardNo":"","borrowUsage":"",
        //"borrower":"","repaymentSource":"","borrowUsage":"",
//        System.out.println(AesUtil.decrypt("u74wpR/RZrbRXcw7t2792w==", AesUtil.getKeystore()));
//        System.out.println(decrypt("UdUJ4dm2r2/r4F2bi53sNmks7elnKRYgSyHC+BuMYfY="));


        List<String> listByExcel = testPoi.getListByExcel(new FileInputStream(file), "*.xlsx");
        int i = 0;
        for (String ele : listByExcel) {
            i++;
            System.out.println("https://activity.xxx.com/test/guaguakaTongziTradeSuccMqCallBack/orderNo?orderNo=" + ele);
            sendGetRequest("https://activity.xxx.com/test/guaguakaTongziTradeSuccMqCallBack/orderNo?orderNo=" + ele);
            Thread.sleep(10L);
        }
        System.out.println(i);


//        System.out.println(listByExcel);

//        System.out.println(AesUtil.decrypt("FXIu93u7lvGx9ml2sxtYOkUbLa6XTysP0RBuvF3Em2I=", AesUtil.getKeystore()));
//        System.out.println(AesUtil.decrypt("4hhLNRQOtWmnxVF1V/nhalEx/Ms3u5ZvBZ0kdwPKG6I=", AesUtil.getKeystore()));
//        sendGetRequest("http://activity.xxx.com/test/sendCopperForInviter","param=");
//        sendGetRequest("https://www.baidu.com/s?wd=123&rsv_spt=1&rsv_iqid=0x8b1fcced00023931&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=4&rsv_sug1=2&rsv_sug7=101&rsv_t=1911ZcpU5p2a%2B8wpC5l7oZ3P%2FsfxbTfFXBSKI1Kl0AOvUUUkFHyEqGhU4IGsa9ZwXRE6&rsv_sug2=0&inputT=722&rsv_sug4=805");

    }

    /**
     * 发送http get请求
     */
    public static void sendGetRequest(String urlAndParam) {
        try {
            URL url = new URL(urlAndParam);
            URLConnection connection = url.openConnection();
            InputStream in = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(in, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            isr.close();
            in.close();
            System.out.println(sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
            /*connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");*/
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    //CommonUtils.decrypt
    public List<String> getListByExcel(InputStream in, String fileName) throws Exception {
        // 创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        List<String> list = new ArrayList<String>();

        // 遍历Excel中所有的sheet
        sheet = work.getSheetAt(0);
        // 遍历当前sheet中的所有行
        for (int j = sheet.getFirstRowNum(); j < sheet.getLastRowNum() + 1; j++) { // 这里的加一是因为下面的循环跳过取第一行表头的数据内容了
            if (j == 0) continue;
            row = sheet.getRow(j);

            // 遍历所有的列
            /*String orderNo = null;
            for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                cell = row.getCell(y);
                orderNo = this.indexToValue(cell, y);
            }*/
            list.add(this.indexToValue(row.getCell(0), 0));
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

    public String indexToValue(Cell cell, int index) {
        switch (index) {
            case 0:
                return cell.getStringCellValue();
//                vo.setTzOrderNo(cell.getStringCellValue());
//            break;
            default:
                break;
        }
        return null;
    }

    private static class TzOrder {
        private String tzOrderNo;

        public String getTzOrderNo() {
            return tzOrderNo;
        }

        public void setTzOrderNo(String tzOrderNo) {
            this.tzOrderNo = tzOrderNo;
        }
    }

} 
