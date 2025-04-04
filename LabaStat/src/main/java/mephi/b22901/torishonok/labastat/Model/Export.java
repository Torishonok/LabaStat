/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.labastat.Model;



import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import mephi.b22901.torishonok.labastat.Controller.Controller;
import org.apache.commons.math3.linear.RealMatrix;
/**
 *
 * @author vikus
 */
public class Export {

    private Controller controller;

    public Export(Controller controller) {
        this.controller = controller;
    }

    public void exportData(String path, HashMap<String, double[]> data, double[][] interval, RealMatrix matrix) {

        if (!path.endsWith(".xlsx")) {
            path = path + ".xlsx";
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet1 = workbook.createSheet("Статистические показатели");

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); 
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        int columns = data.values().iterator().next().length;
        int rows = data.size();

        Row header1 = sheet1.createRow(0);
        for (int i = 0; i < columns; i++) {
            Cell cell = header1.createCell(i + 1);
            cell.setCellValue("Выборка " + (i + 1));
            cell.setCellStyle(style);
        }

        int count = 1;
        for (Map.Entry<String, double[]> element : data.entrySet()) {
            Row row = sheet1.createRow(count);
            row.createCell(0).setCellValue(element.getKey());

            double[] value = element.getValue();
            for (int i = 0; i < columns; i++) {
                Cell cell = row.createCell(i + 1);
                cell.setCellValue(value[i]);
                cell.setCellStyle(style);
            }
            count++;
        }

        Row header2 = sheet1.createRow(rows + 1);
        header2.createCell(0).setCellValue("Доверит. интервал");
        for (int i = 0; i < columns; i++) {
            Cell cell = header2.createCell(i + 1);
            cell.setCellValue("[" + interval[i][0] + ", " + interval[i][1] + "]");
            cell.setCellStyle(style);
        }

        for (int i = 0; i <= columns; i++) {
            sheet1.autoSizeColumn(i);
        }

        Sheet sheet2 = workbook.createSheet("Ковариационная матрица");
        Row values = sheet2.createRow(0);
        for (int j = 0; j < columns; j++) {
            Cell cell = values.createCell(j + 1);
            cell.setCellValue("Выборка " + (j + 1));
            cell.setCellStyle(style);
        }

        for (int i = 0; i < columns; i++) {
            double[] temp = matrix.getRow(i);
            Row row = sheet2.createRow(i + 1);
            for (int j = 0; j <= columns; j++) {
                if (j == 0) {
                    row.createCell(j).setCellValue("Выборка " + (i + 1));
                } else {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(temp[j - 1]);
                    cell.setCellStyle(style);
                }
                sheet2.autoSizeColumn(j);
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(new File(path));
            workbook.write(out);
            JOptionPane.showMessageDialog(null, "File has been written successfully\nPath: " + path, "OK", JOptionPane.INFORMATION_MESSAGE);
            workbook.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
 