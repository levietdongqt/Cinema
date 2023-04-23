/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import POJO.Customer;
import POJO.Product;
import POJO.Schedule;
import POJO.Ticket;
import Utils.AlertUtils;
import Utils.SessionUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author BE BAU
 */
public class FXMLPrintBillController implements Initializable {

    private DecimalFormat formatter = new DecimalFormat("#,##0");
    @FXML
    private AnchorPane billprint = new AnchorPane();

    @FXML
    private TextField txtCus;

    @FXML
    private TextField txtDateTime;

    @FXML
    private TextField txtEmp;

    @FXML
    private TextField txtFilm;

    @FXML
    private TextArea txtPro;

    @FXML
    private TextField txtSeat;

    @FXML
    private TextField txtTicket;

    @FXML
    private TextField txtTime;

    @FXML
    private TextField txtTotal;
    
    @FXML
    private TextField txtDis;

    private Customer cus = new Customer();

    private Schedule sche = new Schedule();

    private LocalDateTime printDate;

    private BigDecimal total;
    
    private long discount;

    private String text3 = String.format("%-10s %20s %20s","Name","Quantity","Price")+"\n";

    public FXMLPrintBillController(Customer cus, Schedule sche, LocalDateTime printDate, BigDecimal total,long discount) {
        this.cus = cus;
        this.sche = sche;
        this.printDate = printDate;
        this.total = total;
        this.discount = discount;
    }

    public void exportpdf() {

        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null) {
            boolean success = job.printPage(billprint);
            if (success) {
                job.endJob();
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadView();
            exportpdf();
            App.setView("FXMLShowSchedule");
            Alert alert = AlertUtils.getAlert("Buy Ticket successful!!", Alert.AlertType.INFORMATION);
            alert.show();
            Stage stage = (Stage) billprint.getScene().getWindow();
            stage.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } 

    }

    public void loadView() {
        this.txtDateTime.setText(printDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        String textCus = "";
        if (cus != null) {
            textCus = cus.getCustomerName();
        } else {
            textCus = "unknow";
        }
        this.txtCus.setText(textCus);
        this.txtEmp.setText(SessionUtil.getEmployee().getEmpName());
        this.txtFilm.setText(sche.getFilm().getFilmName());
        if (sche.getStartTime().toLocalDate().equals(LocalDate.now())) {
            this.txtTime.setText(sche.getStartTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        } else {
            this.txtTime.setText(sche.getStartTime().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));
        }
        String text2 = "";
        List<Ticket> tickets = SessionUtil.getTicketList();
        for (Ticket ticket1 : tickets) {
            text2 += ticket1.getSeatMap() + " ";
        }
        this.txtSeat.setText(text2);
        this.txtTicket.setText(String.format("%d", tickets.size()));

        Map<Product, Integer> mapPro = SessionUtil.getProductList();
        if (!mapPro.isEmpty()) {
            mapPro.forEach((t, u) -> {
                text3 +=String.format("%-10s %20s %20d", t.getProductName(),u.toString(),(t.getPrice()*u))+"\n";
            });
        }
        this.txtPro.setText(text3);
        this.txtDis.setText(String.format("-%d", discount));
        this.txtTotal.setText(formatter.format(total) + " VND");
        SessionUtil.getProductList().clear();
        SessionUtil.getTicketList().clear();

    }

}
