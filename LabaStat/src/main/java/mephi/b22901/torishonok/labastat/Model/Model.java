/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.labastat.Model;



    import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author vikus
 */
public class Model {

    private Map<String, Double> dataMap;
    private String[] sheetNames;

    public Model() {
        dataMap = new HashMap<>();
    }

    public void importData(String filePath, String selectedSheet) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheet(selectedSheet);

        dataMap.clear(); // Очистка предыдущих данных

        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() == CellType.NUMERIC) {
                    String key = "Row " + row.getRowNum() + " Col " + cell.getColumnIndex();
                    dataMap.put(key, cell.getNumericCellValue());
                } else if (cell.getCellType() == CellType.STRING) {
                    // Пропускаем строки
                    continue;
                }
            }
        }
        workbook.close();
        file.close();
    }

    public void exportData(String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Statistics");

        int rowIndex = 0;
        for (Map.Entry<String, Double> entry : dataMap.entrySet()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }

        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }

    public String[] getSheetNames(String filePath) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        int numberOfSheets = workbook.getNumberOfSheets();
        sheetNames = new String[numberOfSheets];

        for (int i = 0; i < numberOfSheets; i++) {
            sheetNames[i] = workbook.getSheetName(i);
        }

        workbook.close();
        file.close();
        return sheetNames;
    }

    public Map<String, Double> getDataMap() {
        return dataMap;
    }
}
