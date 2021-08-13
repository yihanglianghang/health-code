package com.nxl.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;

public class POITest {

    //使用poi读取Excel文件中的数据
    @Test
    public void test() throws Exception {
        //加载指定文件，创建一个Excel对象(工作簿)
        XSSFWorkbook excel=
                new XSSFWorkbook(new FileInputStream(new File("E:\\poi.xlsx")));

      // 读取Excel文件中第一个sheet标签页
        XSSFSheet sheet =excel.getSheetAt(0);
             //  遍历sheet标签页，获得每一行数据
        for (Row row : sheet) {
            //  遍历行，获得每个单元格对象
            for(Cell cell: row){
                System.out.println(cell.getStringCellValue());
            }
        }
      //关闭资源
        excel.close();
    }

    //使用POI向Excel文件写入数据，并且通过输出流将创建的Excel文件保存到本地磁盘中
    @Test
    public void test1() throws IOException {
        //在内存中创建一个Excel文件
        XSSFWorkbook execl=new XSSFWorkbook();
        //创建一个工作表对象
        XSSFSheet sheet=execl.createSheet("java案例");
        //在工作表中创建行对象
        XSSFRow title=sheet.createRow(0);
        //在行中创建单元格对象
        title.createCell(0).setCellValue("poi");
        title.createCell(1).setCellValue("quartz");
        title.createCell(2).setCellValue("redis");

        XSSFRow dataRow=sheet.createRow(1);
        dataRow.createCell(0).setCellValue("execl");
        dataRow.createCell(0).setCellValue("coner");
        dataRow.createCell(0).setCellValue("sdif");


        //创建一个输出流，通过输出流将内存中的Excel文件写到磁盘中
        FileOutputStream out=new FileOutputStream(new File("e:\\hello.xlss"));

       execl.write(out);
       out.flush();
       execl.close();
    }
}
