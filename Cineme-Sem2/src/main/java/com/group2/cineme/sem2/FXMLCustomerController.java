package com.group2.cineme.sem2;

import POJO.Customer;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class FXMLCustomerController implements Initializable{
    Customer cus = new Customer();

    @FXML
    private AnchorPane inname;
    
    @FXML
    private AnchorPane inbday;
    
    @FXML
    private AnchorPane inaddr;
    
    @FXML
    private AnchorPane inphone;
    
    @FXML
    private AnchorPane inemail;
    
    @FXML
    private AnchorPane inpoints;
    
    @FXML
    private TextField cusname;
    
    @FXML
    private DatePicker cusbday;
    
    @FXML
    private TextField cusaddr;
    
    @FXML
    private TextField cusphone;
    
    @FXML
    private TextField cusemail;
    
    @FXML
    private TextField cuspoints;

    
    private void checkName() {
        cusname.setOnKeyTyped(event -> {
            String name = cusname.getText().trim();
            try {
                inname.setVisible(false);
                cus.setCustomerName(name);
            } catch (IOException e) {
                inname.setVisible(true);
            }
        });
    }

    private void checkBday() {
        cusbday.setOnKeyTyped(event -> {
           LocalDate birth = cusbday.getValue();
            try {
                cus.setBirthDate(birth);
                inbday.setVisible(false);
            } catch (IOException e) {
                inbday.setVisible(true);
            }
        });
    }

    private void checkAddr() {
        cusbday.setOnKeyTyped(event -> {
           String addr = cusaddr.getText().trim();
            try {
                cus.setAddress(addr);
                inaddr.setVisible(false);
            } catch (IOException e) {
                inaddr.setVisible(true);
            }
        });
    }

    private void checkPhone() {
        cusphone.setOnKeyTyped(event -> {
           String addr = cusphone.getText().trim();
            try {
                cus.setCusPhone(addr);
                inphone.setVisible(false);
            } catch (IOException e) {
                inphone.setVisible(true);
            }
        });
    }

    private void checkEmail() {
        cusemail.setOnKeyTyped(event -> {
           String email = cusemail.getText().trim();
            try {
                cus.setEmail(email);
                inemail.setVisible(false);
            } catch (IOException e) {
                inemail.setVisible(true);
            }
        });
    }

    private void checkPoints() {
        cuspoints.setOnKeyTyped(event -> {
           Integer points = Integer.parseInt(cuspoints.getText().trim());
           
            try {
                cus.setTotalPoints(points);
                inpoints.setVisible(false);
            } catch (IOException e) {
                inpoints.setVisible(true);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkName();
        checkBday();
        checkAddr();
        checkPhone();
        checkEmail();
        checkPoints();
    }

    
}
