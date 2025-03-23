/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.labastat.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;
import mephi.b22901.torishonok.labastat.Model.Model;
import mephi.b22901.torishonok.labastat.View.View;

/**
 *
 * @author vikus
 */
public class Controller {
   private Model model;
    private View view;

    public Controller(Model model) {
        this.model = model;
        }
    public void setView(View view) {
        this.view = view;
    }
 
    private class ImportListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Открываем диалог для выбора файла
            view.showImportDialog();
        }
    }

   //private class ExportListener implements ActionListener {
        //@Override
        //public void actionPerformed(ActionEvent e) {
            // Логика для экспорта данных
            //JOptionPane.showMessageDialog(view.getMainFrame(), "Экспорт данных не реализован.");
        //}
    //} 

    public void updateSheetSelector(String filePath) {
        try {
            String[] sheetNames = model.getSheetNames(filePath);
            view.showSheetSelectionDialog(sheetNames, filePath);
        } catch (IOException ex) {
            view.showError("Ошибка получения листов: " + ex.getMessage());
        }
    }
    public void calculateStatistics(String filePath, String sheetName) {
        try {
            model.importData(filePath, sheetName);
            double[] means = model.calculateMean();
            double[] geometricMeans = model.calculateGeometricMean();
            double[] stdDevs = model.calculateStandardDeviation();
            double[] ranges = model.calculateRange();
            double[][] covariances = model.calculateCovariance();
            int[] counts = model.countElements();
            double[] coeffOfVariation = model.calculateCoefficientOfVariation();
            double[][] confidenceIntervals = model.calculateConfidenceInterval(0.95); // 95% доверительный интервал
            double[] variances = model.calculateVariance();
            double[] maxValues = model.calculateMax();
            double[] minValues = model.calculateMin();

            String outputFileName = JOptionPane.showInputDialog(view.getMainFrame(), "Введите имя для нового файла (без расширения):");
            if (outputFileName != null && !outputFileName.trim().isEmpty()) {
                String outputFilePath = outputFileName + ".xlsx";
                model.exportResults(outputFilePath, means, geometricMeans, stdDevs, ranges, covariances, counts, coeffOfVariation, confidenceIntervals, variances, maxValues, minValues);
                JOptionPane.showMessageDialog(view.getMainFrame(), "Результаты успешно сохранены в " + outputFilePath, "Успех", JOptionPane.INFORMATION_MESSAGE);
            }

            
        } catch (IOException ex) {
            view.showError("Ошибка импорта данных: " + ex.getMessage());
        }
    }
    
}
