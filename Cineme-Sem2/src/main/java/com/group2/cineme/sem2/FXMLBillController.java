package com.group2.cineme.sem2;

import DAO.ProductDAO;
import POJO.Bill;
import POJO.Ticket;
import Utils.SessionUtil;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class FXMLBillController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private ScrollPane scrollPane;
    
    Bill b1 = new Bill();

    public FXMLBillController(VBox vbox) {
        this.vbox = vbox;
    }

    public FXMLBillController() {

    }

    public void readData() throws Exception {
        ProductDAO pdao = new ProductDAO();

        pdao.getAll("Product").forEach((t) -> {
            SessionUtil.getProductList().put(t, 4);
        });

//        
//        Ticket tk = new Ticket();
//        Ticket tk2 = new Ticket();
//        
//        tk.setBill(b1);
//        tk2.setBill(b1);
//        
//        tk.setSeatMap("A10");
//        tk2.setSeatMap("B50");
//        SessionUtil.getTicketList().add(tk);
//        SessionUtil.getTicketList().add(tk2);
//        
//        b1.setEmployee(SessionUtil.getEmployee());
//        b1.setPrintDate(LocalDateTime.now());
        SessionUtil.getProductList().forEach((p, a) -> {
            System.out.println(p.getProductName());
            System.out.println(a);
        });
    }

    public void loadProduct() {
        SessionUtil.getProductList().forEach((product, quantity) -> {
            
            HBox hbox = new HBox();
            
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(50);
            hbox.getChildren().add(new Label(product.getProductName()));
            hbox.getChildren().add(new Label("" + quantity));
            hbox.getChildren().add(new Label("" + product.getPrice()));
        
            vbox.getChildren().add(hbox);
            
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("\n VO \n");
        
        scrollPane.setMaxHeight(vbox.getPrefHeight());
        
        try {
            readData();
            loadProduct();
        } catch (Exception ex) {
            Logger.getLogger(FXMLBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
