/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.CustomerDAO;
import POJO.Bill;
import POJO.Customer;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author thuhuytran
 */
public class FXMLCustomerManagerController implements Initializable {
    
    @FXML
    private TableView<Customer> tableCus;
    @FXML
    private TextField txtPhone;
    private List<Customer> lists;
    @FXML
    private VBox vBox;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDataTableCus();
        loadViewTable();
        searchField();
        
    }
    
    public void loadDataTableCus() {
        CustomerDAO cd = new CustomerDAO();
        try {
            lists = cd.getAll("Customer");
            this.tableCus.setItems(FXCollections.observableList(lists));
        } catch (Exception ex) {
            Logger.getLogger(FXMLCustomerManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadViewTable() {
        TableColumn colPhone = new TableColumn("PHONE");
        colPhone.setCellValueFactory(new PropertyValueFactory("cusPhone"));
        TableColumn colName = new TableColumn("NAME");
        colName.setCellValueFactory(new PropertyValueFactory("customerName"));
        TableColumn colBD = new TableColumn("BIRTHDATE");
        colBD.setCellValueFactory(new PropertyValueFactory("birthDate"));
        TableColumn colAddress = new TableColumn("ADDRESS");
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        TableColumn colEmail = new TableColumn("EMAIL");
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        TableColumn colPoint = new TableColumn("POINT");
        colPoint.setCellValueFactory(new PropertyValueFactory("totalPoints"));
        TableColumn<Customer, Button> colEdit = new TableColumn<>();
        colEdit.setCellValueFactory((p) -> {
            Button button = new Button("EDIT");
            Customer cus = p.getValue();
            button.setOnAction((t) -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCustomer.fxml"));
                loader.setControllerFactory(new Callback<Class<?>, Object>() {
                    @Override
                    public Object call(Class<?> p) {
                        return new FXMLCustomerController(cus,true);
                    }
                });
                Stage stage = new Stage();
                try {
                    stage.setScene(new Scene(loader.load()));
                } catch (IOException ex) {
                    Logger.getLogger(FXMLCustomerManagerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                stage.show();
                vBox.setDisable(true);
                stage.setOnHiding((o) -> {
                    vBox.setDisable(false);
                });
            });
            return new SimpleObjectProperty<>(button);
        });
        this.tableCus.getColumns().addAll(colPhone, colName, colBD, colAddress, colEmail, colPoint,colEdit);
         ObservableList<TableColumn<Customer, ?>> columns = this.tableCus.getColumns();
        for (TableColumn<Customer, ?> column : columns) {
            column.setStyle("-fx-alignment: CENTER;");
        }
        this.tableCus.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-style: solid;");    
    }
    public void searchField(){
        this.txtPhone.textProperty().addListener((o) -> {
            List<Customer> listPhone = lists.stream().filter((t) -> t.getCusPhone().contains(this.txtPhone.getText())).collect(Collectors.toList());
            this.tableCus.setItems(FXCollections.observableList(listPhone));
        });
    }
    @FXML
    public void undoButtonHandler(){
        loadDataTableCus();
    }
    
}
