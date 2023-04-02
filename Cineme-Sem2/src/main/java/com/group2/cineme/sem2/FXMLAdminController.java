/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.EmployeeDAO;
import POJO.Employee;
import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author BE BAU
 */
public class FXMLAdminController implements Initializable {

    @FXML
    private ComboBox<?> cbGender;

    @FXML
    private ComboBox<String> cbPosition;

    @FXML
    private ComboBox<String> cbStatus;

    @FXML
    private DatePicker dpBirth;

    @FXML
    private DatePicker dpTimeSta;

    @FXML
    private Label errBirth;

    @FXML
    private Label errGender;

    @FXML
    private Label errMail;

    @FXML
    private Label errName;

    @FXML
    private Label errPass;

    @FXML
    private Label errPhone;

    @FXML
    private Label errPos;

    @FXML
    private Label errRPass;

    @FXML
    private Label errStatus;

    @FXML
    private Label errTimeSta;

    @FXML
    private Label errUser;

    @FXML
    private TextField tfMail;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPass;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfRPass;

    @FXML
    private TextField tfUser;

    Employee em = new Employee();

    public void checkUser() {
        tfUser.setOnKeyTyped(event -> {
            String user = tfUser.getText().trim();
            try {
                errUser.setVisible(false);
                em.setUserName(user);
            } catch (IOException e) {
                errUser.setVisible(true);
                errUser.setText(e.getMessage());
            }
        });
    }

    public void checkName() {
        tfName.setOnKeyTyped(event -> {
            String name = tfName.getText().trim();
            try {
                em.setEmpName(name);
                errName.setVisible(false);
            } catch (IOException e) {
                errName.setVisible(true);
                errName.setText(e.getMessage());
            }
        });
    }

    public void checkPhone() {
        tfPhone.setOnKeyTyped(even -> {
            String phone = tfPhone.getText().trim();
            try {
                em.setEmpPhone(phone);
                errPhone.setVisible(false);

            } catch (IOException e) {
                errPhone.setVisible(true);
                errPhone.setText(e.getMessage());
            }
        });
    }

    public void checkMail() {
        tfMail.setOnKeyTyped(even -> {
            String mail = tfMail.getText().trim();
            try {
                em.setEmail(mail);
                errMail.setVisible(false);
            } catch (Exception e) {
                errMail.setVisible(true);
                errMail.setText(e.getMessage());
            }
        });
    }

    public void checkPass() {
        tfPass.setOnKeyTyped(even -> {
            String pass = tfPass.getText().trim();
            try {
                em.setPassword(pass);
                errPass.setVisible(false);
            } catch (IOException ex) {
                errPass.setVisible(true);
                errPass.setText(ex.getMessage());
            }
        });
    }

    public void checkRePass() {
        tfRPass.setOnKeyTyped(event -> {
            String rpass = tfRPass.getText().trim();
            String pass = tfPass.getText().trim();

            try {
                if (rpass.equals(pass)) {
                    em.setPassword(rpass);
                    errRPass.setVisible(false);
                } else {
                    errRPass.setVisible(true);
                    errRPass.setText("repass#pass");

                }
            } catch (IOException e) {
                errRPass.setVisible(true);
                errRPass.setText(e.getMessage());
            }
        });
    }

    public void checkBirth() {
        dpBirth.setOnAction(even -> {
            LocalDate birth = dpBirth.getValue();
            try {
                em.setBirthDate(birth);
                errBirth.setVisible(false);
            } catch (IOException ex) {
                errBirth.setVisible(true);
                errBirth.setText(ex.getMessage());
            }

        });
    }

    public void checkPosi() {
        ObservableList<String> positions = FXCollections.observableArrayList("Choose a position", "Staff", "Manager");
        cbPosition.setItems(positions);
        cbPosition.getSelectionModel().selectFirst();
        cbPosition.setOnAction(even -> {
            String posi = cbPosition.getValue();
            try {
                em.setPosition(posi);
                errPos.setVisible(false);
            } catch (IOException ex) {
                errPos.setVisible(true);
                errPos.setText(ex.getMessage());
            }
        });
    }

//    public void checkStaus() {
//         cbStatus.setSelected(true);
//        boolean status = cbStatus.isSelected();
//        em.setStatus(status);
//    }
    public void checkStart() {
        dpTimeSta.setValue(LocalDate.now());
        try {
            em.setStartDate(dpTimeSta.getValue());
        } catch (Exception e) {
            System.out.println("123");
        }

        dpTimeSta.setOnAction(even -> {
            LocalDate start = dpTimeSta.getValue();
            try {
                em.setStartDate(start);
                errTimeSta.setVisible(false);
            } catch (IOException e) {
                errTimeSta.setVisible(true);
                errTimeSta.setText(e.getMessage());
            }
        });

    }

    public void submit(ActionEvent event) throws Exception {
        try {
            System.out.println(em.isStatus());
            EmployeeDAO dao = new EmployeeDAO();
            dao.add(em);
            this.tfUser.clear();
            this.tfRPass.clear();
            this.tfPhone.clear();
            this.tfPass.clear();
            this.tfMail.clear();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkUser();
        checkName();
        checkPhone();
        checkMail();
        checkPass();
        checkRePass();
        checkBirth();
        checkPosi();
//        checkStaus();
        checkStart();

    }

}
