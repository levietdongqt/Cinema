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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLSigupController implements Initializable {

    Employee em = new Employee();

    @FXML
    private Label errBirth;

    @FXML
    private Label errMail;

    @FXML
    private Label errName;

    @FXML
    private Label errPass;

    @FXML
    private Label errPhone;

    @FXML
    private Label errPosition;

    @FXML
    private Label errStatus;

    @FXML
    private Label errUser;

    @FXML
    private DatePicker suBirth;

    @FXML
    private Button suBtn;

    @FXML
    private TextField suMail;

    @FXML
    private TextField suName;

    @FXML
    private PasswordField suPass;

    @FXML
    private TextField suPhone;

    @FXML
    private ChoiceBox<String> suPosition;

    @FXML
    private CheckBox suStatus;

    @FXML
    private TextField suUser;


    public void checkUser() {
        suUser.setOnKeyTyped(event -> {
            String user = suUser.getText().trim();
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
        suName.setOnKeyTyped(event -> {
            String name = suName.getText().trim();
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
        suPhone.setOnKeyTyped(even -> {
            String phone = suPhone.getText().trim();
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
        suMail.setOnKeyTyped(even -> {
            String mail = suMail.getText().trim();
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
        suPass.setOnKeyTyped(even -> {
            String pass = suPass.getText().trim();
            try {
                em.setPassword(pass);
                errPass.setVisible(false);
            } catch (IOException ex) {
                errPass.setVisible(true);
                errPass.setText(ex.getMessage());
            }
        });
    }

    public void checkBirth() {
        suBirth.setOnAction(even -> {
            LocalDate birth = suBirth.getValue();
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
        suPosition.setItems(positions);
        suPosition.getSelectionModel().selectFirst();
        suPosition.setOnAction(even -> {
            String posi = suPosition.getValue();
            try {
                em.setPosition(posi);
                errPosition.setVisible(false);
            } catch (IOException ex) {
                errPosition.setVisible(true);
                errPosition.setText(ex.getMessage());
            }
        });
    }

    public void checkStaus() {
        boolean status = suStatus.isSelected();
        suStatus.setSelected(true);
        em.setStatus(status);
    }

    public void close() {
        System.exit(0);
    }

    public void submit(ActionEvent event) throws Exception {
        try {
            EmployeeDAO dao = new EmployeeDAO();
            dao.add(em);
            this.suMail.clear();
            this.suName.clear();
            this.suPass.clear();
            this.suPhone.clear();
            this.suUser.clear();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void initialize(URL url, ResourceBundle rb) {

        checkUser();
        checkName();
        checkPhone();
        checkMail();
        checkPass();
        checkBirth();
        checkPosi();
        checkStaus();

    }

}
