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
        Controller controller = new Controller(model);
        View view = new View(controller, model); // Передаем контроллер и модель в View
        controller.setView(view);
        
        view.addImportListener(e -> view.showImportDialog());
        view.addExportListener(e -> {
            // Здесь должна быть логика экспорта данных
        });
        view.addExitListener(e -> System.exit(0));

    }
}