/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.BillDAO;
import POJO.Bill;
import POJO.Customer;
import POJO.Film;
import POJO.Product;
import POJO.ProductBill;
import POJO.Promotion;
import POJO.Schedule;
import POJO.Ticket;
import Utils.AlertUtils;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import tornadofx.ExpanderColumn;

/**
 * FXML Controller class
 *
 * @author thuhuytran
 */
public class FXMLBillManagerController implements Initializable {

    private DecimalFormat formatter = new DecimalFormat("#,##0");
    @FXML
    private ComboBox<LocalDate> comboBoxDate;
    @FXML
    private ComboBox<LocalDateTime> comboBoxDateTime;
    @FXML
    private TableView<Bill> tableViewBill;
    @FXML
    private TextField txtFilm;
    private List<Bill> lists;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDataComboBoxPDate();
        loadDataTable(LocalDate.now());
        loadTableView();
        setActionForComBoBoxPDate();
        loadTextFiled();
        setActionForDateTime();
    }
    @FXML
    public void undoButtonHandler(){
        this.tableViewBill.setItems(FXCollections.observableList(lists));
    }
    public void loadTableView() {
        TableColumn colID = new TableColumn("ID");
        colID.setCellValueFactory(new PropertyValueFactory("billID"));
        colID.setPrefWidth(30);

        TableColumn<Bill, String> colEmp = new TableColumn("EMPLOYEE");
        colEmp.setCellValueFactory((p) -> {
            Bill b = p.getValue();
            String nameEmp = b.getEmployee().getEmpName();
            return new SimpleObjectProperty<>(nameEmp);
        });
        colEmp.setPrefWidth(100);
        TableColumn<Bill, String> colFilm = new TableColumn<>("FILM");
        colFilm.setCellValueFactory((p) -> {
            Bill b = p.getValue();
            String nameFilm = "";
            Set<Ticket> tickets = b.getTickets();
            if (!tickets.isEmpty()) {
                for (Ticket ticket : tickets) {
                    nameFilm = ticket.getSchedule().getFilm().getFilmName();
                }
            }
            return new SimpleObjectProperty<>(nameFilm);
        });
        colFilm.setPrefWidth(250);
        TableColumn<Bill, String> colCus = new TableColumn<>("PHONE-CUS");
        colCus.setCellValueFactory((p) -> {
            Bill b = p.getValue();
            String phone = "";
            Customer cus = b.getCustomer();
            if (cus != null) {
                phone = cus.getCusPhone();
            }
            return new SimpleObjectProperty<>(phone);

        });
        colCus.setPrefWidth(100);
        TableColumn<Bill, String> colTicket = new TableColumn<>("TICKET");
        colTicket.setCellValueFactory((p) -> {
            Bill b = p.getValue();
            String mapTicket = "";
            Set<Ticket> tickets = b.getTickets();
            int count = 0;
            for (Ticket ticket : tickets) {
                if (count == (tickets.size() - 1)) {
                    mapTicket += ticket.getSeatMap();
                } else {
                    mapTicket += ticket.getSeatMap() + ", ";
                }
                count++;
            }
            return new SimpleObjectProperty<>(mapTicket);
        });
        colTicket.setPrefWidth(150);
        TableColumn<Bill, String> colPro = new TableColumn<>("PRODUCT");
        colPro.setCellValueFactory((p) -> {
            Bill b = p.getValue();
            String pro = "";
            Set<ProductBill> productBills = b.getProductBills();
            int count = 0;
            for (ProductBill productBill : productBills) {
                if (count == (productBills.size() - 1)) {
                    pro += productBill.getProduct().getProductName();
                } else {
                    pro += productBill.getProduct().getProductName() + ", ";
                }
                count++;
            }
            return new SimpleObjectProperty<>(pro);
        });
        colPro.setPrefWidth(150);
        TableColumn<Bill, String> colBill = new TableColumn<>("BILL");
        colBill.setCellValueFactory((p) -> {
            Bill b = p.getValue();
            Float bill = b.getExchangePoints() * 1000;
            BigDecimal t = BigDecimal.valueOf(bill);
            String money = formatter.format(t) + " VND";
            return new SimpleObjectProperty<>(money);
        });
        colBill.setPrefWidth(200);
        TableColumn<Bill, String> colprintDate = new TableColumn("PRINT-DATE");
        colprintDate.setCellValueFactory((p) -> {
            Bill b = p.getValue();
            String dateTimeFormat = b.getPrintDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            return new SimpleObjectProperty<>(dateTimeFormat);
        });
        colprintDate.setPrefWidth(150);
        TableColumn<Bill, String> colTimeSche = new TableColumn<>("SCHEDULE");
        colTimeSche.setCellValueFactory((p) -> {
            Bill b = p.getValue();
            String timeSche = "";
            Set<Ticket> tickets = b.getTickets();
            if (!tickets.isEmpty()) {
                for (Ticket ticket : tickets) {
                    timeSche = ticket.getSchedule().getStartTime().format(DateTimeFormatter.ofPattern("dd/MM HH:mm"));
                }
            }
            return new SimpleObjectProperty<>(timeSche);
        });
        colTimeSche.setPrefWidth(150);
        TableColumn<Bill,String> colPromo = new TableColumn<>("PROMOTION");
        colPromo.setCellValueFactory((p) -> {
            Bill b = p.getValue();
            String namePromo = "";
            Promotion pro = b.getPromotion();
            if(pro!=null){
                namePromo = pro.getPromoName();
            }
            return new SimpleObjectProperty<>(namePromo);
        });
        colPromo.setPrefWidth(150);
        this.tableViewBill.getColumns().addAll(colID, colprintDate, colEmp, colFilm, colCus, colTicket, colPro, colTimeSche,colPromo, colBill);
         ObservableList<TableColumn<Bill, ?>> columns = this.tableViewBill.getColumns();
        for (TableColumn<Bill, ?> column : columns) {
            column.setStyle("-fx-alignment: CENTER;");
        }
        this.tableViewBill.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-style: solid;");
    }

    public void loadDataTable(LocalDate date) {
        BillDAO bd = new BillDAO();
        lists = bd.getBillByDate(date);
        this.tableViewBill.setItems(FXCollections.observableList(lists));
        loadComboBoxDateTime();
    }

    public void loadDataComboBoxPDate() {
        List<LocalDate> listDate = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            listDate.add(LocalDate.now().minusDays(i));
        }
        this.comboBoxDate.setItems(FXCollections.observableList(listDate));
        this.comboBoxDate.setCellFactory((p) -> new ComboBoxListCell<>() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    String formatter = item.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    setText(formatter);
                } else {
                    setText("");
                }
            }

        });
    }

    public void setActionForComBoBoxPDate() {
        this.comboBoxDate.setOnAction((t) -> {
            loadDataTable(this.comboBoxDate.getSelectionModel().getSelectedItem());
        });
    }

    public void loadTextFiled() {
        this.txtFilm.textProperty().addListener((o) -> {
            if(Pattern.matches("[0-9 ]+", this.txtFilm.getText())){
                List<Bill> listPhone = lists.stream().filter((t) -> {
                    Customer cusCheck = t.getCustomer();
                    String phone = "";
                    if(cusCheck!=null){
                        phone = cusCheck.getCusPhone();
                    }
                    return phone.contains(this.txtFilm.getText().trim());
                }).collect(Collectors.toList());
                this.tableViewBill.setItems(FXCollections.observableList(listPhone));
            }else{
                List<Bill> listFilm = lists.stream().filter((t) -> {
                String nameFilm = "";
                Set<Ticket> tickets = t.getTickets();
                if (!tickets.isEmpty()) {
                    for (Ticket ticket : tickets) {
                        nameFilm = ticket.getSchedule().getFilm().getFilmName();
                    }
                }
                return nameFilm.toLowerCase().contains(this.txtFilm.getText().toLowerCase());     
            }).collect(Collectors.toList());
            this.tableViewBill.setItems(FXCollections.observableList(listFilm));
            }    
        });
    }
    public void loadComboBoxDateTime(){
        List<LocalDateTime> listTemp = new ArrayList<>();
        for (Bill bill : lists) {
            bill.getTickets().forEach((t) -> {
                listTemp.add(t.getSchedule().getStartTime());
            });
        }
        List<LocalDateTime> listCombo = new ArrayList<>(new HashSet<>(listTemp));
        this.comboBoxDateTime.setItems(FXCollections.observableList(listCombo));
        this.comboBoxDateTime.setCellFactory((p) -> new ComboBoxListCell<>() {
            @Override
            public void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    String formatter = item.format(DateTimeFormatter.ofPattern("dd/MM HH:mm"));
                    setText(formatter);
                } else {
                    setText("");
                }
            }

        });
        AlertUtils.formatLocalDateTimeInComboBox(comboBoxDateTime,"dd/MM HH:mm");
          
    }
    public void setActionForDateTime(){
        this.comboBoxDateTime.setOnAction((t) -> {
            Set<Bill> listDateTime = new HashSet<>();
            for (Bill list : lists) {
                list.getTickets().forEach((o) -> {
                    if(o.getSchedule().getStartTime()==this.comboBoxDateTime.getSelectionModel().getSelectedItem()){
                        listDateTime.add(list);
                    }
                });
            }
            this.tableViewBill.setItems(FXCollections.observableList(new ArrayList<>(listDateTime)));
        });
    }
    
    

}
