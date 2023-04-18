/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.FilmDAO;
import POJO.Film;
import Utils.AlertUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.util.Callback;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author thuhuytran
 */
public class FXMLFilmReportController implements Initializable {
    @FXML
    private VBox vboxFilmReport;
    @FXML
    private Text textReport;
    @FXML
    private TableView<Film> tableViewReport;
    @FXML
    private Label labelTotalPrice;
    @FXML
    private Label labelTotalTicket;
    
    @FXML
    private Label labelTotalFilm;
    @FXML
    private Label labelTotalSche;
    @FXML
    private Spinner<Integer> spinnerYear;
    @FXML
    private ChoiceBox<Month> choiceMonth;
    List<Film> lists;
    private long sumPriceTicket;
    private int countTicket;
    private int countSche;
    private Popup popup = new Popup();
    int month;
    int year;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        year = LocalDate.now().getYear();
        month = LocalDate.now().getMonthValue()-1;
        loadViewTable(year, month);
        loadDataTableView();
        setValueSpinner();
        setValueChoiceBox();
        setTextReport(year,month);
        setHandlerSpinner();
        setHandlerChoiceBox();
        popup.setOnHiding((t) -> {   // Hiện lại trang Home khi popUp tắt
            vboxFilmReport.setDisable(false);
        });
    }
    
    //Khu vuc xu ly nut
    @FXML
    public void DrawHandler(){
        year = this.spinnerYear.getValue();
        if(month==0){
            month=0;
        }else{
            month = this.choiceMonth.getValue().getValue();
        }
        try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLChartFilm.fxml"));
                    fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
                        @Override
                        public Object call(Class<?> param) {
                            return new FXMLChartFilmController(lists,year,month);
                        }

                    });
                    AnchorPane popupContent = fxmlLoader.load();
                    popup.getContent().add(popupContent);
                    popupContent.setStyle("-fx-background-color:white;-fx-text-fill: white;");
                    popup.show(vboxFilmReport.getScene().getWindow());
                    vboxFilmReport.setDisable(true);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }
    }
    @FXML
    public void exportButtonHandler(){
        int countRow = 1;
        XSSFWorkbook Film_Report = new XSSFWorkbook();
        try {
            XSSFSheet sheet;
            if(month==0){
                sheet = Film_Report.createSheet(String.valueOf(year));
            }else{
                sheet = Film_Report.createSheet(String.valueOf(month)+String.valueOf(year));
            }
            //Tao header
            XSSFRow headerRow = sheet.createRow(0);
            ObservableList<TableColumn<Film,?>> columns = tableViewReport.getColumns();
             for (int i = 0; i < columns.size(); i++) {
                headerRow.createCell(i).setCellValue(columns.get(i).getText());
            }
            //Lay data
            ObservableList<Film> data = tableViewReport.getItems();
            for (int i = 0; i < data.size(); i++) {
                XSSFRow dataRow = sheet.createRow(i + 1);
                for (int j = 0; j < columns.size(); j++) {
                    if (j == 0) {
                        dataRow.createCell(j).setCellValue(countRow++);
                        continue;
                    }
                    TableColumn<Film,?> column = columns.get(j);
                    String cellValue = String.valueOf(column.getCellData(i));
                    dataRow.createCell(j).setCellValue(cellValue);
                    
                }
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Excel File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx"));
            if (month == 0) {
                fileChooser.setInitialFileName("Film-Report"+String.valueOf(year));
            } else {
                fileChooser.setInitialFileName("Film-Report"+String.valueOf(month) + "-" + String.valueOf(year));
            }
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                FileOutputStream outputStream = new FileOutputStream(file);
                Film_Report.write(outputStream);
                Film_Report.close();
                outputStream.close();
                Alert alert = AlertUtils.getAlert("Save File Sussesfully", Alert.AlertType.INFORMATION);
                alert.show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void FullYearButtonHandler(){
        year = this.spinnerYear.getValue();
        month = 0;
        resetValue();
        loadViewTable(year, month);
        setTextReport(year, month);
    }

    public void loadDataTableView() {
        TableColumn colName = new TableColumn("NAME");
        colName.setCellValueFactory(new PropertyValueFactory("filmName"));
        colName.setPrefWidth(300);
        TableColumn colView = new TableColumn("TICKET");
        colView.setCellValueFactory(new PropertyValueFactory("viewFilm"));
        TableColumn colStatus = new TableColumn("STATUS");
        colStatus.setCellValueFactory(new PropertyValueFactory("status"));
        TableColumn colSchedule = new TableColumn("TOTAL SCHEDULE");
        colSchedule.setCellValueFactory(new PropertyValueFactory("countSchedule"));
        TableColumn colTicket = new TableColumn("REVENUE");
        colTicket.setCellValueFactory(new PropertyValueFactory("sumPriceTicket"));
        colTicket.setPrefWidth(250);
        TableColumn sttCol = new TableColumn("ID");
        sttCol.setCellFactory((column) -> {
            return new TableCell<Film, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (this.getTableRow() != null && !empty) {
                        int rowIndex = this.getTableRow().getIndex() + 1;
                        setText(String.valueOf(rowIndex));
                    } else {
                        setText("");
                    }
                }
            };
        });
        TableColumn<Film,Hyperlink> colDetails = new TableColumn<>();
        colDetails.setCellValueFactory((p) -> {
            Hyperlink hyperlink = new Hyperlink("DETAILS");
            return new SimpleObjectProperty<>(hyperlink);
        });
        this.tableViewReport.getColumns().addAll(sttCol,colName, colStatus, colView, colSchedule, colTicket,colDetails);
        
        ObservableList<TableColumn<Film, ?>> columns = this.tableViewReport.getColumns();
        for (TableColumn<Film, ?> column : columns) {
            column.setStyle("-fx-alignment: CENTER;");
        }
        this.tableViewReport.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-style: solid;");
    }

    public void loadViewTable(int year, int month) {
        FilmDAO fd = new FilmDAO();
        lists = fd.getFilmForReport(year, month);
        lists.forEach((t) -> {
            countTicket += t.getViewFilm();
            int cSche = fd.countScheduleByFilmID(t.getFilmID(), t.getStartDate().toLocalDate(), t.getEndDate().toLocalDate());
            t.setCountSchedule(cSche);
            countSche+=cSche;
            long price = fd.sumTicketByFilmID(t.getFilmID(), t.getStartDate().toLocalDate(), t.getEndDate().toLocalDate());
            t.setSumPriceTicket(price);
            sumPriceTicket += price;
        });
        this.tableViewReport.setItems(FXCollections.observableList(lists));
        
        this.labelTotalTicket.setText(String.format("%d (ticket)", countTicket));
        this.labelTotalPrice.setText(String.format("%d (VND)", sumPriceTicket));
        this.labelTotalFilm.setText(String.format("%d (films)", lists.size()));
        this.labelTotalSche.setText(String.format("%d (sches)", countSche));
    }

    public void setValueSpinner() {
        this.spinnerYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2022, LocalDate.now().getYear(),
                LocalDate.now().getYear(), 1));
    }

    public void setValueChoiceBox() {
        List<Month> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(Month.of(i));
        }
        this.choiceMonth.setItems(FXCollections.observableList(months));
        this.choiceMonth.setValue(months.get(LocalDate.now().getMonthValue() - 2));
    }

    public void setHandlerSpinner() {
        this.spinnerYear.setOnMousePressed((o) -> {
           
            year = this.spinnerYear.getValue();
            month = this.choiceMonth.getValue().getValue();
            resetValue();
            loadViewTable(year, month);
            setTextReport(year,month);
            
        });
    }

    public void setHandlerChoiceBox() {
        this.choiceMonth.setOnAction((t) -> {
           
            year = this.spinnerYear.getValue();
            month = this.choiceMonth.getValue().getValue();
            resetValue();
            loadViewTable(year, month);
            setTextReport(year,month);
           
        });
    }

    //Ham de reset gia tri
    public void resetValue() {
        countTicket = 0;
        sumPriceTicket = 0;
        countSche=0;
        this.labelTotalPrice.setText("");
        this.labelTotalTicket.setText("");
        this.labelTotalSche.setText("");
        this.labelTotalFilm.setText("");
        this.textReport.setText("");
    }
    public void setTextReport(int year,int month){
        String text="";
        if(month!=0){
            text = "FILM REPORT " + String.format("%d",month)+"/"+String.format("%d",year);
        }else{
            text = "FILM REPORT " +String.format("%d",year);
        }
        this.textReport.setText(text);     
    }

}
