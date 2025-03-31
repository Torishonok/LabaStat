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
import java.util.List;
/**
 *
 * @author vikus
 */
public class Model {

    private double[] data; 
    private double mean;
    private double stdDev;
    private double max;
    private double min;
    private double geomMean;
    private double range;
    private int cnt;
    

    

    public String[] getSheetNames(String filePath) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        int numberOfSheets = workbook.getNumberOfSheets();
        String[] sheetNames = new String[numberOfSheets];

        for (int i = 0; i < numberOfSheets; i++) {
            sheetNames[i] = workbook.getSheetName(i);
        }

        workbook.close();
        file.close();
        return sheetNames;
    }

    public void importData(String filePath, String sheetName) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheet(sheetName);

        data.clear(); 

        for (Row row : sheet) {
            List<Double> rowData = new ArrayList<>();
            for (Cell cell : row) {
                if (cell.getCellType() == CellType.NUMERIC) {
                    rowData.add(cell.getNumericCellValue());
                }
            }
            if (!rowData.isEmpty()) {
                data.add(rowData);
            }
        }

        workbook.close();
        file.close();
    }

    

    
    
    
    public void calculateStatistics(String filePath, String sheetName)throws IOException {
            importData(filePath, sheetName);
            Stat stat = new Stat(data);
            
            mean = stat.calculateMean();
            stdDev = stat.calculateStandardDeviation();
            max = stat.calculateMax();
            min = stat.calculateMin();
            geomMean = stat.calculateGeometricMean();
            range = stat.calculateRange();
            
            
    }
    public double getMean() {
        return mean;
    }

    public double getStandardDeviation() {
        return stdDev;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }
    
     public void exportResults(String filePath, double[] means, double[] geometricMeans, double[] stdDevs, double[] ranges,double[][] covariances, int[] counts, double[] coeffOfVariation, double[][] confidenceIntervals, double[] variances, double[] maxValues, double[] minValues ) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Результаты");

        
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Столбец");
        headerRow.createCell(1).setCellValue("Среднее арифметическое");
        headerRow.createCell(2).setCellValue("Среднее геометрическое");
        headerRow.createCell(3).setCellValue("Стандартное отклонение");
        headerRow.createCell(4).setCellValue("Размах");
        headerRow.createCell(5).setCellValue("Ковариация");
        headerRow.createCell(6).setCellValue("Количество элементов");
        headerRow.createCell(7).setCellValue("Коэффициент вариации");
        headerRow.createCell(8).setCellValue("Доверительный интервал (нижний)");
        headerRow.createCell(9).setCellValue("Доверительный интервал (верхний)");
        headerRow.createCell(10).setCellValue("Дисперсия");
        headerRow.createCell(11).setCellValue("Максимум");
        headerRow.createCell(12).setCellValue("Минимум");

        
        for (int i = 0; i < means.length; i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue("Столбец " + (i + 1));
            row.createCell(1).setCellValue(means[i]);
            row.createCell(2).setCellValue(geometricMeans[i]);
            row.createCell(3).setCellValue(stdDevs[i]);
            row.createCell(4).setCellValue(ranges[i]);
            row.createCell(5).setCellValue(covariances[i][i]); 
            row.createCell(6).setCellValue(counts[i]);
            row.createCell(7).setCellValue(coeffOfVariation[i]);
            row.createCell(8).setCellValue(confidenceIntervals[i][0]);
            row.createCell(9).setCellValue(confidenceIntervals[i][1]);
            row.createCell(10).setCellValue(variances[i]);
            row.createCell(11).setCellValue(maxValues[i]);
            row.createCell(12).setCellValue(minValues[i]);
        }

       
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}
//ковариационгная матрица
//внутри модели 