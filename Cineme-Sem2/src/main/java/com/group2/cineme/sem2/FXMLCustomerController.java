package com.group2.cineme.sem2;

import DAO.CustomerDAO;
import POJO.Customer;
import Utils.AlertUtils;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.hibernate.sql.Update;

public class FXMLCustomerController implements Initializable {

    private Customer cus = new Customer();
    CustomerDAO cusDAO = new CustomerDAO();

    @FXML
    private AnchorPane errpane;

    @FXML
    private Label errlabel;

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

    private String phone;

    private boolean upDate;

    public FXMLCustomerController(String phone) {
        this.phone = phone;
    }

    public FXMLCustomerController(Customer customer, boolean update) {
        this.cus = customer;
        this.upDate = update;
    }

    private void checkName() {
        cusname.setOnKeyTyped(event -> {
            String name = cusname.getText().trim();
            try {
                errpane.setVisible(false);
                cus.setCustomerName(name);
            } catch (Exception e) {
                errpane.setVisible(true);
                errlabel.setText("Invalid Name!\n" + e.getMessage());
            }
        });
    }

    private void checkBday() {
        cusbday.setOnAction(event -> {
            LocalDate birth = cusbday.getValue();
            try {
                cus.setBirthDate(birth);
                errpane.setVisible(false);
            } catch (Exception e) {
                errpane.setVisible(true);
                errlabel.setText("Invalid Birth Day!\n" + e.getMessage());
            }
        });
    }

    private void checkAddr() {
        cusaddr.setOnKeyTyped(event -> {
            try {
                cus.setAddress(cusaddr.getText().trim());
                errpane.setVisible(false);
            } catch (Exception e) {
                errpane.setVisible(true);
                errlabel.setText("Invalid Address!\n" + e.getMessage());
            }
        });
    }

    private void checkPhone() {
        cusphone.setOnKeyTyped(event -> {
            try {
                cus.setCusPhone(cusphone.getText().trim());
                errpane.setVisible(false);
            } catch (Exception e) {
                errpane.setVisible(true);
                errlabel.setText("Invalid Phone!\n" + e.getMessage());
            }
        });
    }

    private void checkEmail() {
        cusemail.setOnKeyTyped(event -> {
            try {
                cus.setEmail(cusemail.getText().trim());
                errpane.setVisible(false);
            } catch (Exception e) {
                errpane.setVisible(true);
                errlabel.setText("Invalid Email!\n" + e.getMessage());
            }
        });
    }

//    private void checkPoints() {
//        cuspoints.setOnKeyTyped(event -> {
//
//            try {
//                Integer points = Integer.parseInt(cuspoints.getText().trim());
//                cus.setTotalPoints(points);
//                errpane.setVisible(false);
//            } catch (Exception e) {
//                errpane.setVisible(true);
//                errlabel.setText("Invalid Total Points!\n" + e);
//            }
//        });
//    }
    public void reset(ActionEvent event) {
        cusname.clear();
        cusaddr.clear();
        cusbday.getEditor().clear();
        cusemail.clear();
        cusphone.clear();
//        cuspoints.clear();
    }

    public void submit(ActionEvent event) throws Exception {
        try {
            if (upDate == true) {
                checkEmptyWhenClickButton();
                if (errpane.isVisible() == false) {
                    cusDAO.update(cus);
                    cusname.clear();
                    cusaddr.clear();
                    cusbday.getEditor().clear();
                    cusemail.clear();
                    cusphone.clear();
                }
            }else {
                checkEmptyWhenClickButton();
                if (errpane.isVisible() == false) {
                    cusDAO.add(cus);
                    cusname.clear();
                    cusaddr.clear();
                    cusbday.getEditor().clear();
                    cusemail.clear();
                    cusphone.clear();
                }
            }
        } catch (Exception ex) {
            AlertUtils.getAlert(cusDAO.getMessAdd(), Alert.AlertType.ERROR).show();
        }
    }

    public void checkEmptyWhenClickButton() {
        String error = "Fields can't be empty";
        if (cusname.getText().isEmpty()) {
            errpane.setVisible(true);
            errlabel.setText(error);
//            return;
        } else {
            errpane.setVisible(false);
        }
        if (cusaddr.getText().isEmpty()) {
            errpane.setVisible(true);
            errlabel.setText(error);
//            return;
        } else {
            errpane.setVisible(false);
        }
        if (cusbday.getValue() == null) {
            errpane.setVisible(true);
            errlabel.setText(error);
//            return;
        } else {
            errpane.setVisible(false);
        }
        if (cusphone.getText().isEmpty()) {
            errpane.setVisible(true);
            errlabel.setText(error);
//            return;
        } else {
            errpane.setVisible(false);
        }
        if (cusemail.getText().isEmpty()) {
            errpane.setVisible(true);
            errlabel.setText(error);
//            return;
        } else {
            errpane.setVisible(false);
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        if (upDate) {
            this.cusphone.setText(cus.getCusPhone());
            this.cusaddr.setText(cus.getAddress());
            this.cusbday.setValue(cus.getBirthDate());
            this.cusemail.setText(cus.getEmail());
            this.cusname.setText(cus.getCustomerName());
        } else {
            this.cusphone.setText(phone);
            try {
                cus.setCusPhone(cusphone.getText().trim());
            } catch (Exception ex) {
                Logger.getLogger(FXMLCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        checkName();
        checkBday();
        checkAddr();
        checkPhone();
        checkEmail();
    }

}
