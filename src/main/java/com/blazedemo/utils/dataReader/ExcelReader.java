package com.blazedemo.utils.dataReader;

import com.blazedemo.utils.logs.LogsManager;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class ExcelReader {
    private static final String TEST_DATA_PATH = "src/test/java/testData/";

    //bad practice
    public static String getExcelData(String excelFileName, String sheetName, int rowNum, int colNum) {
        //read data from excel file
        XSSFWorkbook workbook;
        XSSFSheet sheet;

        String cellData;
        try {
            workbook = new XSSFWorkbook(TEST_DATA_PATH + excelFileName);
            sheet = workbook.getSheet(sheetName);
            cellData = sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
            return cellData;
        } catch (IOException e) {
            LogsManager.error("Error reading excel file: ", excelFileName, e.getMessage());
            return "";
        }
    }
}

