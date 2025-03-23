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

    private List<List<Double>> data; // Данные из листа

    public Model() {
        data = new ArrayList<>();
    }

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

        data.clear(); // Очистка предыдущих данных

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

    public double[] calculateMean() {
        return Stat.calculateMean(convertDataToArray());
    }

    public double[] calculateGeometricMean() {
        return Stat.calculateGeometricMean(convertDataToArray());
    }

    public double[] calculateStandardDeviation() {
        return Stat.calculateStandardDeviation(convertDataToArray());
    }

    public double[] calculateRange() {
        return Stat.calculateRange(convertDataToArray());
    }
    
    public double[][] calculateCovariance() {
        return Stat.calculateCovariance(convertDataToArray());
    }

    public int[] countElements() {
        return Stat.countElements(convertDataToArray());
    }

    public double[] calculateCoefficientOfVariation() {
        return Stat.calculateCoefficientOfVariation(convertDataToArray());
    }

    public double[][] calculateConfidenceInterval(double confidenceLevel) {
        return Stat.calculateConfidenceInterval(convertDataToArray(), confidenceLevel);
    }

    public double[] calculateVariance() {
        return Stat.calculateVariance(convertDataToArray());
    }

    public double[] calculateMax() {
        return Stat.calculateMax(convertDataToArray());
    }

    public double[] calculateMin() {
        return Stat.calculateMin(convertDataToArray());
    }

    private double[][] convertDataToArray() {
        double[][] arrayData = new double[data.size()][data.get(0).size()];
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                arrayData[i][j] = data.get(i).get(j);
            }
        }
        return arrayData;
    }
     public void exportResults(String filePath, double[] means, double[] geometricMeans, double[] stdDevs, double[] ranges,double[][] covariances, int[] counts, double[] coeffOfVariation, double[][] confidenceIntervals, double[] variances, double[] maxValues, double[] minValues ) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Результаты");

        // Записываем заголовки
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

        // Записываем данные
        for (int i = 0; i < means.length; i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue("Столбец " + (i + 1));
            row.createCell(1).setCellValue(means[i]);
            row.createCell(2).setCellValue(geometricMeans[i]);
            row.createCell(3).setCellValue(stdDevs[i]);
            row.createCell(4).setCellValue(ranges[i]);
            row.createCell(5).setCellValue(covariances[i][i]); // Ковариация с самим собой
            row.createCell(6).setCellValue(counts[i]);
            row.createCell(7).setCellValue(coeffOfVariation[i]);
            row.createCell(8).setCellValue(confidenceIntervals[i][0]);
            row.createCell(9).setCellValue(confidenceIntervals[i][1]);
            row.createCell(10).setCellValue(variances[i]);
            row.createCell(11).setCellValue(maxValues[i]);
            row.createCell(12).setCellValue(minValues[i]);
        }

        // Записываем файл
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}
