/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package com.group2.cineme.sem2;
import DAO.*;
import DAO.GenericDAO;
import POJO.Employee;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author BE BAU
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private Label cgv;

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField pass;

    @FXML
    private TextField user;

    @FXML
    private Label welcom;
    
    public void close(){
        System.exit(0);
    }
    
    
    public void login() throws Exception{
     EmployeeDAO dao = new EmployeeDAO();
        System.out.println("123");
        
    boolean a= dao.checkaccount( user.getText(), pass.getText());
             System.out.println(a);

    }
  
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

        
             //set vùng sáng mặc định cho 2 chữ khi vừa mới mở app lên 
        DropShadow original = new DropShadow(20, Color.valueOf("blue"));
        welcom.setEffect(original);
        cgv.setEffect(original);

        //set viền sáng vàng cho chữ cinemas
        welcom.setOnMouseEntered((MouseEvent event)
                -> {
            DropShadow shadow = new DropShadow(50, Color.valueOf("#F8FF1C"));

//            welcom.setStyle("-fx-text-fill:#ff2b00");
            welcom.setEffect(shadow);
            cgv.setEffect(shadow);

        });

        welcom.setOnMouseExited((MouseEvent event)
                -> {
            DropShadow shadow = new DropShadow(20, Color.valueOf("#F8FF1C"));
//            welcom.setStyle("-fx-text-fill:#ff2b00");
            welcom.setEffect(shadow);
            cgv.setEffect(shadow);

        });

        //set viền sáng vàng cho chữ cgv 
        cgv.setOnMouseEntered((MouseEvent event)
                -> {
            DropShadow shadow = new DropShadow(50, Color.valueOf("#F8FF1C"));

//            welcom.setStyle("-fx-text-fill:#ff2b00");
            welcom.setEffect(shadow);
            cgv.setEffect(shadow);

        });

        cgv.setOnMouseExited((MouseEvent event)
                -> {
            DropShadow shadow = new DropShadow(20, Color.valueOf("#F8FF1C"));
//            welcom.setStyle("-fx-text-fill:#ff2b00");
            welcom.setEffect(shadow);
            cgv.setEffect(shadow);

        });   
        
        
    }    
    
}
