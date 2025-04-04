/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.labastat.View;

import java.awt.BorderLayout;
import java.awt.Font;
import mephi.b22901.torishonok.labastat.Controller.Controller;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author vikus
 */
public class View {
    private Controller controller;
    private boolean isDataProcessed = false;

    ;

    public View(Controller controller) {
        this.controller = controller;
        JFrame frame = new JFrame("XLSX Data Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 300);
        frame.setLayout(new BorderLayout());

        JButton processButton = new JButton("Импортировать файл для расчета показателей");
        processButton.setFont(new Font("Lato", Font.BOLD, 15));
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = getPathForReading();
                if (path != null) {
                    controller.read(path);
                    controller.process();
                     isDataProcessed = true;
                }
            }
        });

        JButton exportButton = new JButton("Экспортировать xlsx файл");
        exportButton.setFont(new Font("Lato", Font.BOLD, 15));
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isDataProcessed) {
                     JOptionPane.showMessageDialog(frame, "Сначала импортируйте файл и рассчитайте показатели.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String path = getPathForExport();
                if (path != null) {
                    controller.export(path);
                }
            }
        });

        JButton exitButton = new JButton("Закончить работу");
        exitButton.setFont(new Font("Lato", Font.BOLD, 15));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(processButton);
        panel.add(exportButton);
        panel.add(exitButton);

        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public String getPathForReading() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel file(*.xlsx)", "xlsx");
        fileChooser.setFileFilter(filter);
        String path = null;
        int ret = fileChooser.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getAbsolutePath();
        } else if (ret == JFileChooser.CANCEL_OPTION) {
            return null;
        }
        return path;
    }

    public int giveAnswer(Integer[] num) {
         String[] options = new String[num.length];
        for (int i = 0; i < num.length; i++) {
            options[i] = "Лист " + num[i];
        }
        String selected = (String) JOptionPane.showInputDialog(null, "Выберите номер листа", "Диалоговое окно", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (selected != null) {
            return Integer.parseInt(selected.split(" ")[1]); 
        }
        return -1; 
    }

    public String getPathForExport() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel file(*.xlsx)", "xlsx");
        fileChooser.setFileFilter(filter);
        String path = null;
        int ret = fileChooser.showSaveDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getAbsolutePath();
        } else if (ret == JFileChooser.CANCEL_OPTION) {
            return null;
        }
        return path;
    }

}




