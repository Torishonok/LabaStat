/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package mephi.b22901.torishonok.labastat;

import mephi.b22901.torishonok.labastat.Controller.Controller;
import mephi.b22901.torishonok.labastat.Model.Model;
import mephi.b22901.torishonok.labastat.View.View;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 *
 * @author vikus
 */
public class LabaStat {

    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        
        view.addImportListener(e -> view.showImportDialog());
        view.addExportListener(e -> {
            // Логика для экспорта данных
            JOptionPane.showMessageDialog(view.getMainFrame(), "Экспорт данных не реализован.");
        });
        view.addExitListener(e -> System.exit(0));

    }
}