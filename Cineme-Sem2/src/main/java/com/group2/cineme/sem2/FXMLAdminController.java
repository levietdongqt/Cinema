/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import Utils.SessionUtil;
import DAO.EmployeeDAO;
import DAO.WorkSessionDAO;
import POJO.Employee;
import POJO.WorkSession;
import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import static org.hibernate.criterion.Projections.id;

/**
 * FXML Controller class
 *
 * @author BE BAU
 */
public class FXMLAdminController implements Initializable {

    @FXML
    private ComboBox<String> cbGender;

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
    private TableColumn<Employee, String> tcEmail;

    @FXML
    private TableColumn<Employee, String> tcGender;

    @FXML
    private TableColumn<Employee, String> tcName;

    @FXML
    private TableColumn<Employee, String> tcPhone;

    @FXML
    private TableColumn<Employee, String> tcPosition;

    @FXML
    private TableColumn<Employee, String> tcStartDate;

    @FXML
    private TableColumn<Employee, String> tcStatus;

    @FXML
    private TableColumn<Employee, String> tcUser;

    @FXML
    private TableColumn<Employee, Integer> tcWorking;

    @FXML
    private TextField tfMail;

    @FXML
    private TextField tfName;

    @FXML
    private PasswordField tfPass;

    @FXML
    private TextField tfPhone;

    @FXML
    private PasswordField tfRPass;

    @FXML
    private TextField tfUser;

    @FXML
    private TableView<Employee> tvEmployee;

//    List<Employee> listEm;
    Employee em = new Employee();
    EmployeeDAO dao = new EmployeeDAO();
    List<Employee> emList;

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

    public void checkGender() {
        ObservableList<String> genders = FXCollections.observableArrayList("Choose a Gender", "Male", "Female");
        cbGender.setItems(genders);
        cbGender.getSelectionModel().selectFirst();

        cbGender.setOnAction(event -> {

            String selectedGender = cbGender.getValue();
            boolean genderBitValue = false; // Khởi tạo giá trị mặc định là false

            if (selectedGender.equals("Male")) {
                genderBitValue = true;
            } else {
                genderBitValue = false;
            }
            em.setGender(genderBitValue);

        });

    }

    public void checkStatus() {
        ObservableList<String> statuss = FXCollections.observableArrayList("Choose a Status", "Avaliable", "Unavaliable");
        cbStatus.setItems(statuss);
        cbStatus.getSelectionModel().selectFirst();
        cbStatus.setOnAction(event -> {
            String selectStatus = cbStatus.getValue();
            boolean statusBit = false;
            if (selectStatus.equals("Avaliable")) {
                statusBit = true;

            } else {
                statusBit = false;
            }
            em.setStatus(statusBit);
        });

    }

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
            dao.add(em);

            showEmployee();
            this.tfUser.clear();
            this.tfName.clear();
            this.tfRPass.clear();
            this.tfPhone.clear();
            this.tfPass.clear();
            this.tfMail.clear();
            cbGender.getSelectionModel().selectFirst();
            cbPosition.getSelectionModel().selectFirst();
            cbStatus.getSelectionModel().selectFirst();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void update(ActionEvent event) throws Exception {

        try {

            em.setUserName(tfUser.getText().trim());
            em.setEmpName(tfName.getText().trim());
            em.setEmail(tfMail.getText().trim());
            em.setEmpPhone(tfPhone.getText().trim());
            em.setPassword(tfPass.getText().trim());
            em.setPosition(cbPosition.getValue().trim());

            dao.update(em);
//            System.out.println(em.setUserName());

            showEmployee();
            this.tfUser.clear();
            this.tfName.clear();
            this.tfRPass.clear();
            this.tfPhone.clear();
            this.tfPass.clear();
            this.tfMail.clear();
            cbGender.getSelectionModel().selectFirst();
            cbPosition.getSelectionModel().selectFirst();
            cbStatus.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void delete(ActionEvent event) throws Exception {
        try {
            dao.delete(tfUser.getText().trim(), Employee.class);
            System.out.println(tfUser.getText().trim());

            showEmployee();
            this.tfUser.clear();
            this.tfName.clear();
            this.tfRPass.clear();
            this.tfPhone.clear();
            this.tfPass.clear();
            this.tfMail.clear();
            cbGender.getSelectionModel().selectFirst();
            cbPosition.getSelectionModel().selectFirst();
            cbStatus.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void showEmployee() {
        
//        WorkSessionDAO wdao = new WorkSessionDAO();
//         Map<String, Long> totalWorkTime =wdao.getTotalWorkTimeByUserAndMonth(4);
                
        
        try {
            emList = dao.getAll("Employee");
//            emList = dao.getA(4);
        
           
          
            tcUser.setCellValueFactory(new PropertyValueFactory<>("userName"));
            tcName.setCellValueFactory(new PropertyValueFactory<>("empName"));
            tcGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tcPhone.setCellValueFactory(new PropertyValueFactory<>("empPhone"));
            tcPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
            tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            tcStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
//            tcWorking.setCellValueFactory(new PropertyValueFactory<>("totalWorkTime"));
            tvEmployee.setItems(FXCollections.observableList(emList));

        } catch (Exception ex) {
            Logger.getLogger(FXMLAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void selectEmployee() {
        Employee employee = tvEmployee.getSelectionModel().getSelectedItem();
        int num = tvEmployee.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        tfUser.setText(String.valueOf(employee.getUserName()));
        tfName.setText(String.valueOf(employee.getEmpName()));
        tfMail.setText(String.valueOf(employee.getEmail()));
        tfPhone.setText(String.valueOf(employee.getEmpPhone()));
        cbPosition.setValue(String.valueOf(employee.getPosition()));
        dpTimeSta.setValue(employee.getStartDate());
        cbGender.setValue(employee.isGender() ? "Male" : "Female");
        cbStatus.setValue(employee.isStatus() ? "Avaliable" : "Unavaliable");
        dpBirth.setValue(employee.getBirthDate());
        dpTimeSta.setValue(employee.getStartDate());

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
        checkStatus();
        checkStart();
        checkGender();
        showEmployee();
        
        


    }

}
