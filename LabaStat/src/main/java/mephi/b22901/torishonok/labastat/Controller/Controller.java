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

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        // Обработчик для импорта данных
        view.addImportListener(new ImportListener());
        // Обработчик для экспорта данных
        view.addExportListener(new ExportListener());
        // Обработчик для выхода из приложения
        view.addExitListener(e -> System.exit(0));
    }

    private class ImportListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Открываем диалог для выбора файла
            view.showImportDialog();
        }
    }

    private class ExportListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Логика для экспорта данных
            JOptionPane.showMessageDialog(view.getMainFrame(), "Экспорт данных не реализован.");
        }
    }

    public void updateSheetSelector(String filePath) {
        try {
            String[] sheetNames = model.getSheetNames(filePath);
            view.showSheetSelectionDialog(sheetNames, filePath);
        } catch (IOException ex) {
            view.showError("Ошибка получения листов: " + ex.getMessage());
        }
    }
}
