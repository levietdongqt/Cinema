/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import POJO.Employee;
import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author BE BAU
 */
public class FXMLSigupController  {
    
    Employee em = new Employee();
    
    @FXML
    private Label ermail;

    @FXML
    private Label errbirth;

    @FXML
    private Label errname;

    @FXML
    private Label errpass;

    @FXML
    private Label errphone;

    @FXML
    private Label errposition;

    @FXML
    private Label errstaus;

    @FXML
    private Label erruser;

    @FXML
    private Button su_btn;

    @FXML
    private DatePicker subirth;

    @FXML
    private TextField sumail;

    @FXML
    private TextField suname;

    @FXML
    private PasswordField supass;

    @FXML
    private TextField suphone;

    @FXML
    private ChoiceBox<?> suposition;

    @FXML
    private ChoiceBox<?> sustaus;

    @FXML
    private TextField suuser;

//    public void checkName() {
//        String name = suname.getText().trim();
//
//        try {
////            em.setEmpName(name);
//            if (name == null) {
//                errname.setText("Employee name cannot be null");
//                errname.setVisible(true);
//
//            }
//            if (!name.matches("^[a-zA-Z]+$")) {
//                errname.setText("Employee name can only contain alphabetic characters");
//                errname.setVisible(true);
//
//            }
//            em.setEmpName(name);
//            errname.setVisible(false);
//        } catch (IOException e) {
//            errname.setVisible(true);
//            errname.setText(e.getMessage());
//        }
//    }
    
    
    @FXML
public void checkName() throws IOException {
    System.out.println("1111");
    String name = suname.getText().trim();
    System.out.println(name);
        if (name == null) {
            System.out.println("q456");
            errname.setText("Employee name cannot be null");
            errname.setVisible(true);
            return; // kết thúc phương thức khi xảy ra lỗi
        } // in thông báo lỗi trong console
        if (!name.matches("^[a-zA-Z]+$")) {
            System.out.println("789");
            errname.setText("Employee name can only contain alphabetic characters");
            errname.setVisible(true);
            return; // kết thúc phương thức khi xảy ra lỗi
        }
        em.setEmpName(name);
        errname.setVisible(false);
}


public void checkUser()throws IOException{
    String user = suuser.getText().trim();
    System.out.println(user);
    
}

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
