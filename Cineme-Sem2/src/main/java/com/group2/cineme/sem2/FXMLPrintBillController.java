/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author BE BAU
 */
public class FXMLPrintBillController implements Initializable {
    
        @FXML
    private AnchorPane billarea;


       public void exportpdf() {
        
            PrinterJob job = PrinterJob.createPrinterJob();

            if (job != null) {
                boolean success = job.printPage(billarea);
                if (success) {
                    job.endJob();
                }
            }
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
exportpdf();
    }    
    
}
