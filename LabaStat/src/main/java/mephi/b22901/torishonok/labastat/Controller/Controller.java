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
import mephi.b22901.torishonok.labastat.Model.Model.StatisticsResults;
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
            
            view.showImportDialog();
        }
    }
    public void calculateStatistics(String filePath, String sheetName) {
    try {
        StatisticsResults results = model.calculateStatistics(filePath, sheetName);
        String outputFileName = JOptionPane.showInputDialog(view.getMainFrame(), "Введите имя для нового файла (без расширения):");
        view.exportResults(outputFileName, results.means, results.geometricMeans, results.stdDevs, results.ranges, results.covariances, results.counts, results.coeffOfVariation, results.confidenceIntervals, results.variances, results.maxValues, results.minValues);
    } catch (IOException ex) {
        view.showError("Ошибка импорта данных: " + ex.getMessage());
    }
}
}
    