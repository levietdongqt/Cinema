/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.*;
import DAO.GenericDAO;
import POJO.Employee;
import POJO.Film;
import Utils.HibernateUtils;
import Utils.SessionUtil;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

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

    public void close() {
        System.exit(0);
    }

    public void login(ActionEvent event) throws Exception {
        EmployeeDAO dao = new EmployeeDAO();
        boolean log = dao.checkaccount(user.getText(), pass.getText());
        if (log) {
            App.setView("FXMLHome");
             
//            Parent root = FXMLLoader.load(getClass().getResource("FXMLHome.fxml"));
//            Scene scene = new Scene(root);
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(scene);
//            stage.show();
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
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
    public void loadData() {
        FilmDAO f = new FilmDAO();
        List<Film> films;
        try {
            films = f.searchByDate("endDate");
             
        
        SessionUtil.setMapFilm(films);
        } catch (Exception ex) {
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

}
