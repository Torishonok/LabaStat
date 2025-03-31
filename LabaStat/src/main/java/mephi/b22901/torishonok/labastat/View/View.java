/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.labastat.View;

import java.awt.BorderLayout;
import mephi.b22901.torishonok.labastat.Model.Model;
import mephi.b22901.torishonok.labastat.Controller.Controller;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author vikus
 */
public class View {
    private JFrame mainFrame;
    private JButton importButton;
    private JButton exportButton;
    private JButton exitButton;
    private String selectedFilePath; 
    private Controller controller; 
    private Model model;
    private boolean dataAvailable;

    public View(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
        this.dataAvailable = false;
        mainFrame = new JFrame("Statistics Calculator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 150);
        mainFrame.setLayout(new GridLayout(3, 1));

        importButton = new JButton("Импорт");
        exportButton = new JButton("Экспорт");
        exitButton = new JButton("Выход");

        mainFrame.add(importButton);
        mainFrame.add(exportButton);
        mainFrame.add(exitButton);

        mainFrame.setVisible(true);
    }

    public void showImportDialog() {
        JDialog importDialog = new JDialog(mainFrame, "Импорт данных", true);
        importDialog.setSize(400, 200);
        importDialog.setLayout(new BorderLayout());

        JButton fileChooserButton = new JButton("Выбрать файл Excel");
        JComboBox<String> sheetSelector = new JComboBox<>();
        JButton confirmButton = new JButton("Импортировать");
        JButton cancelButton = new JButton("Отмена");

        fileChooserButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Выберите файл Excel");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx"));

            int returnValue = fileChooser.showOpenDialog(importDialog);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.getName().endsWith(".xlsx")) {
                    try {
                        selectedFilePath = selectedFile.getAbsolutePath();
                        JOptionPane.showMessageDialog(importDialog, "Файл успешно выбран: " + selectedFile.getName(), "Успех", JOptionPane.INFORMATION_MESSAGE);
                        updateSheetSelector(selectedFilePath, sheetSelector);
                    } catch (IOException ex) {
                        Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(importDialog, "Пожалуйста, выберите файл формата Excel (.xlsx )", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        confirmButton.addActionListener(e -> {
             String selectedSheet = (String) sheetSelector.getSelectedItem();
        if (selectedSheet != null) {
            controller.calculateStatistics(selectedFilePath, selectedSheet);
            dataAvailable = true;
        }
            importDialog.dispose();
        });

        cancelButton.addActionListener(e -> importDialog.dispose());

        JPanel panel = new JPanel();
        panel.add(fileChooserButton);
        panel.add(sheetSelector);
        importDialog.add(panel, BorderLayout.NORTH);
        importDialog.add(confirmButton, BorderLayout.CENTER);
        importDialog.add(cancelButton, BorderLayout.SOUTH);

        importDialog.setVisible(true);
    }
    
    public void updateSheetSelector(String filePath, JComboBox<String> sheetSelector) throws IOException {
        try{
        String[] sheetNames = model.getSheetNames(filePath);
        sheetSelector.removeAllItems();
        for (String sheetName : sheetNames) {
            sheetSelector.addItem(sheetName);
        }
        }catch(IOException ex){
            showError("Ошибка получения листов: " + ex.getMessage());
        }
}
    
    public void updateSheetSelector(String filePath) {
        try {
            String[] sheetNames = model.getSheetNames(filePath);
            showSheetSelectionDialog(sheetNames, filePath);
        } catch (IOException ex) {
            showError("Ошибка получения листов: " + ex.getMessage());
        }
    }
    
    public void addImportListener(ActionListener listener) {
        importButton.addActionListener(listener);
    }

    public void addExportListener(ActionListener listener) {
        exportButton.addActionListener(e -> {
            if (!dataAvailable) {
                JOptionPane.showMessageDialog(mainFrame, "Отсутствуют данные для экспорта.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Данные экспортированы.", "Успех", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public void addExitListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }
    public void exportResults(String outputFileName, double[] means, double[] geometricMeans, double[] stdDevs, double[] ranges,double[][] covariances, int[] counts, double[] coeffOfVariation, double[][] confidenceIntervals, double[] variances, double[] maxValues, double[] minValues ) throws IOException {
            if (outputFileName != null && !outputFileName.trim().isEmpty()) {
                String outputFilePath = outputFileName + ".xlsx";
                try{
                model.exportResults(outputFilePath, means, geometricMeans, stdDevs, ranges, covariances, counts, coeffOfVariation, confidenceIntervals, variances, maxValues, minValues);
                JOptionPane.showMessageDialog(getMainFrame(), "Результаты успешно сохранены в " + outputFilePath, "Успех", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            showError("Ошибка импорта данных: " + ex.getMessage());
        }
       }
    }
     public JFrame getMainFrame() {
        return mainFrame; 
    }
     public void showError(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
     public void showSheetSelectionDialog(String[] sheetNames, String filePath) {
        JDialog sheetDialog = new JDialog(mainFrame, "Выбор листа", true);
        sheetDialog.setSize(500, 350);
        sheetDialog.setLayout(new BorderLayout());
        

        JComboBox<String> sheetSelector = new JComboBox<>(sheetNames);
        JButton selectButton = new JButton("Выбрать");
        JButton cancelButton = new JButton("Отмена");

        selectButton.addActionListener(e -> {
            String selectedSheet = (String) sheetSelector.getSelectedItem();
            JOptionPane.showMessageDialog(sheetDialog, "Выбран лист: " + selectedSheet, "Успех", JOptionPane.INFORMATION_MESSAGE);
            sheetDialog.dispose();
        });

        cancelButton.addActionListener(e -> sheetDialog.dispose());
}}



