/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.ProductDAO;
import POJO.Product;
import Utils.AlertUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author DONG
 */
public class FXMLFoodReportController implements Initializable {

    @FXML
    private TableColumn<Object[], String> nameCol;
    @FXML
    private TableColumn<Object[], Integer> sttCol;
    @FXML
    private TableColumn<Object[], Integer> priceCol;

    @FXML
    private TableColumn<Object[], Integer> quantityCol;

    @FXML
    private TableColumn<Object[], Integer> totalCol;

    @FXML
    private TableColumn<Object[], String> typeCol;
    @FXML
    private TableView tableView;
    @FXML
    private Spinner<Integer> year;
    @FXML
    private ComboBox<Month> month;
    @FXML
    private Label info;
    List<Product> productList = new ArrayList<>();
    ProductDAO proDAO = new ProductDAO();
    List<Object[]> list = new ArrayList<>();
    int selectedYear;
    int selectedMonth;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadLayout();
        creatTableView();
        try {
            productList = proDAO.getAll("Product");
        } catch (Exception ex) {
            Logger.getLogger(FXMLFoodReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadDataTableView();
    }

    @FXML
    private void setUpComboBoxMonth() {
        selectedMonth = month.getValue().getValue();
        info.setText(String.valueOf(selectedMonth) + "/" + String.valueOf(selectedYear));
        list.clear();
        list = proDAO.countOrderProduct(selectedYear, selectedMonth);
        loadDataTableView();
    }

    @FXML
    private void setUpSpinnerYear() {
        try {
            selectedYear = year.getValue();
            getMonthList();
            info.setText(String.valueOf(selectedMonth) + "/" + String.valueOf(selectedYear));
            list.clear();
            list = proDAO.countOrderProduct(selectedYear, selectedMonth);
            loadDataTableView();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void setUpBtnFullYear() {
        info.setText(String.valueOf(selectedYear));
        selectedMonth = 0;
        list.clear();
        list = proDAO.countOrderProduct(selectedYear, 0);
        loadDataTableView();
    }

    @FXML
    private void export() {
        int coutRow = 1;
        XSSFWorkbook Food_Report = new XSSFWorkbook();
        try {

            XSSFSheet sheet;
            if (selectedMonth == 0) {
                sheet = Food_Report.createSheet(String.valueOf(selectedYear));
            } else {
                sheet = Food_Report.createSheet(String.valueOf(selectedMonth) + String.valueOf(selectedYear));
            }

            XSSFRow headerRow = sheet.createRow(0);
            ObservableList<TableColumn<Object[], ?>> columns = tableView.getColumns();
            for (int i = 0; i < columns.size(); i++) {
                headerRow.createCell(i).setCellValue(columns.get(i).getText());
            }
            ObservableList<Object[]> dataList = tableView.getItems();
            for (int i = 0; i < dataList.size(); i++) {  //Lặp từng Row dữ liệu
                XSSFRow dataRow = sheet.createRow(i + 1);   //Tạo row dữ liệu
                for (int j = 0; j < columns.size(); j++) { //Lặp qua các column trong tableView
                    //Tạo riêng cho số thứ tự
                    if (j == 0) {
                        dataRow.createCell(j).setCellValue(coutRow++);
                        continue;
                    }
                    TableColumn<Object[], ?> column = columns.get(j);   //Lấy giá trị từng column theo index j
                    Object[] object = dataList.get(i);
                    String cellValue = String.valueOf(column.getCellData(i));
                    dataRow.createCell(j).setCellValue(cellValue);
                }
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Excel File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx"));
            if (selectedMonth == 0) {
                fileChooser.setInitialFileName(String.valueOf(selectedYear));
            } else {
                fileChooser.setInitialFileName(String.valueOf(selectedMonth) + "-" + String.valueOf(selectedYear));
            }
           File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                FileOutputStream outputStream = new FileOutputStream(file);
                Food_Report.write(outputStream);
                Food_Report.close();
                outputStream.close();
                Alert alert = AlertUtils.getAlert("Save File Sussesfully", Alert.AlertType.INFORMATION);
                alert.show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadLayout() {
        year.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2022, LocalDateTime.now().getYear(), LocalDateTime.now().getYear(), 1));
        selectedYear = year.getValue();
        getMonthList();
        selectedMonth = month.getValue().getValue();
        info.setText(String.valueOf(selectedMonth) + "/" + String.valueOf(selectedYear));
        list = proDAO.countOrderProduct(selectedYear, selectedMonth);
    }

    private void getMonthList() {
        if (selectedYear < LocalDateTime.now().getYear()) {
            try {
                month.setItems(FXCollections.observableList(List.of(Month.values())));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return;
        }
        Month[] fullMonths = Month.values();
        List<Month> listMonth = new ArrayList<>();
        Month currMonth = LocalDateTime.now().getMonth();
        for (Month month : fullMonths) {
            if (month.getValue() <= currMonth.getValue()) {
                listMonth.add(month);
            }
        }
        month.setItems(FXCollections.observableList(listMonth));
        month.setValue(LocalDateTime.now().getMonth());

    }

    private void loadDataTableView() {
        //Thêm các sản phẩm có quatity = 0 và tableView;
        for (Product product : productList) {
            int count = 0;
            for (Object[] objects : list) {
                if (product.getProductName().equalsIgnoreCase(objects[0].toString())) {
                    count++;
                    break;
                }
            }
            if (count == 0) {
                Object[] new1 = {product.getProductName(), product.getType(), String.valueOf(product.getPrice()), "0"};
                list.add(new1);
            }

        }
        tableView.setItems(FXCollections.observableList(list));
    }

    private void creatTableView() {
        sttCol.setCellFactory((column) -> {
            return new TableCell<Object[], Integer>() {
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
        nameCol.setCellValueFactory((p) -> {
            Object[] item = p.getValue();
            return new SimpleObjectProperty<>(item[0].toString());
        });
        typeCol.setCellValueFactory((p) -> {
            Object[] item = p.getValue();
            return new SimpleObjectProperty<>(item[1].toString());
        });
        priceCol.setCellValueFactory((p) -> {
            Object[] item = p.getValue();
            return new SimpleObjectProperty<>(Integer.parseInt(item[2].toString()));
        });
        quantityCol.setCellValueFactory((p) -> {
            Object[] item = p.getValue();
            return new SimpleObjectProperty<>(Integer.parseInt(item[3].toString()));
        });
        totalCol.setCellValueFactory((p) -> {
            Object[] item = p.getValue();
            return new SimpleObjectProperty<>(Integer.parseInt(item[2].toString()) * Integer.parseInt(item[3].toString()));
        });

    }

}
