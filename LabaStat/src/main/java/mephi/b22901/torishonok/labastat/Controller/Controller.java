/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.labastat.Controller;

import mephi.b22901.torishonok.labastat.Model.Export;
import mephi.b22901.torishonok.labastat.Model.Import;
import mephi.b22901.torishonok.labastat.Model.Stat;
import mephi.b22901.torishonok.labastat.View.View;

/**
 *
 * @author vikus
 */
public class Controller {
   private Import importedData;
    private Stat process;
    private Export exportInfo;
    private View view;

    public Controller() {
        view = new View(this);
    }

    public void read(String path) {
        importedData = new Import(this);
        importedData.ChecknRead(path);
    }

    public void process() {
        if (importedData.getData()!=null) {
            process = new Stat(importedData.getData());
        }
    }

    public int getSheet(Integer[] num) {
        return view.giveAnswer(num);
    }

    public void export(String path) {
        exportInfo = new Export(this);
        if (!process.returnData().isEmpty()) {
            exportInfo.exportData(path, process.returnData(), process.returnConfInterval(), process.returnCovariance());
        }
    }

}
