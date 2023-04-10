/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import Utils.SessionUtil;
import DAO.EmployeeDAO;
import DAO.WorkSessionDAO;
import POJO.Employee;
import POJO.Product;
import POJO.WorkSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import static java.util.Locale.filter;
import java.util.Map;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
<<<<<<< HEAD
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
=======
>>>>>>> 264c38e989693df51d296563cad948886878dc40
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author BE BAU
 */
public class FXMLAdminController implements Initializable {
    WorkSession ws = new WorkSession();
    Employee em = new Employee();
    WorkSessionDAO workdao = new WorkSessionDAO();
    EmployeeDAO dao = new EmployeeDAO();
    List<Employee> emList;

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
    private TableColumn<Employee, Double> tcWorking;

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

    @FXML
    private TextField tfSearch;

    @FXML
    private DatePicker pdMonth;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

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

        tfPhone.setOnKeyTyped(even -> {
            String phone = tfPhone.getText().trim();
            try {
                em.setEmpPhone(phone);
//                empl.setEmpPhone(phone);
                errPhone.setVisible(false);
            } catch (IOException e) {
                errPhone.setVisible(true);
                errPhone.setText(e.getMessage());
            }
        });

    }

    public void checkPhone() {
        tfPhone.setOnKeyTyped(even -> {
            String phone = tfPhone.getText().trim();
            try {
                em.setEmpPhone(phone);
//                empl.setEmpPhone(phone);
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
//                empl.setEmail(mail);

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
//                empl.setBirthDate(birth);

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
//                empl.setPosition(posi);

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
//            empl.setGender(genderBitValue);
//            boolean gender = genderBitValue;

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
//            empl.setStatus(statusBit);

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
//                empl.setStartDate(start);

                errTimeSta.setVisible(false);
            } catch (IOException e) {
                errTimeSta.setVisible(true);
                errTimeSta.setText(e.getMessage());
            }
        });

    }

    // tạo mới nhân viên 
    public void submit(ActionEvent event) throws Exception {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm New Employee");
        alert.setHeaderText("Are you sure you want to add new employee ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                   String user = tfUser.getText();
                  em.setUserName(user);
                    ws.setEmployee(em);
                    ws.setStartTime(LocalDateTime.now());
                    ws.setEndTime(LocalDateTime.now());
                dao.add(em);
                workdao.add(ws);
                showEmployee();
                clear(event);

            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    // update thông tin nhân viên
    public void update(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Employee Information Update");
        alert.setHeaderText("Are you sure you want to update the employee information?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                em.setUserName(tfUser.getText().trim());
                em.setEmpName(tfName.getText().trim());
                em.setEmail(tfMail.getText().trim());
                em.setPosition(cbPosition.getValue().trim());
                em.setEmpPhone(tfPhone.getText().trim());

                dao.updateEmployee(em);

                showEmployee();
                clear(event);

            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    // đổi pass đăng nhập
    public void updatePass(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Update Password");
        alert.setHeaderText("Are you sure you want to update the password?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                Employee emp = new Employee();
                emp.setUserName(tfUser.getText().trim());
                dao.updatePassword(emp, tfRPass.getText().trim());
                String pass = tfPass.getText().trim();
                System.out.println(pass);
                showEmployee();
                clear(event);

            } catch (Exception e) {
                e.getMessage();
            }
        }

    }

    // Xoá nhân viên khỏi database
    public void delete(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Are you sure you want to delete the data?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                dao.delete(tfUser.getText().trim(), Employee.class);
                System.out.println(tfUser.getText().trim());

                showEmployee();
                clear(event);
            } catch (Exception e) {
                e.getMessage();
            }
        }

    }

    // xoá value trên form 
    public void clear(ActionEvent event) throws Exception {

        this.tfUser.clear();
        this.errUser.setVisible(false);
        this.tfName.clear();
        this.errName.setVisible(false);
        this.tfRPass.clear();
        this.errRPass.setVisible(false);
        this.tfPhone.clear();
        this.errPhone.setVisible(false);
        this.tfPass.clear();
        this.errPass.setVisible(false);
        this.tfMail.clear();
        this.errMail.setVisible(false);
        cbGender.getSelectionModel().selectFirst();
        this.errGender.setVisible(false);
        cbPosition.getSelectionModel().selectFirst();
        this.errPos.setVisible(false);
        cbStatus.getSelectionModel().selectFirst();
        this.errStatus.setVisible(false);
        this.errBirth.setVisible(false);

    }

    public void month() {
        pdMonth.setConverter(new StringConverter<LocalDate>() {
            String pattern = "MM/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                pdMonth.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

    }

    public void showEmployee() {
        String power = "Manager";
        String user = SessionUtil.getEmployee().getUserName();
        
        WorkSessionDAO wdao = new WorkSessionDAO();
        month();
        LocalDate selectedDate = pdMonth.getValue();
        int selectedMonth;
        if (selectedDate == null) {
            selectedMonth = LocalDate.now().getMonthValue();
        } else {
            selectedMonth = selectedDate.getMonthValue();
        }

        Map<String, Double> totalWorkTime = wdao.getTotalWorkTimeByUserAndMonth(selectedMonth);
        try {
            

            if (power.equals(SessionUtil.getEmployee().getPosition())) {
               emList = dao.getAll("Employee"); 
                
            }else{
                emList = dao.getById(user);
            }
               
                for (Employee employee : emList) {
                    for (String employee1 : totalWorkTime.keySet()) {
                        if (employee1.equals(employee.getUserName())) {
                            employee.setTotalWorkTime(totalWorkTime.get(employee1));
                        }
                    }
                }
           
            tcUser.setCellValueFactory(new PropertyValueFactory<>("userName"));
            tcName.setCellValueFactory(new PropertyValueFactory<>("empName"));
            tcGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tcPhone.setCellValueFactory(new PropertyValueFactory<>("empPhone"));
            tcPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
            tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            tcStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            tcWorking.setCellValueFactory(new PropertyValueFactory<>("totalWorkTime"));
            tvEmployee.setItems(FXCollections.observableList(emList));

// search 
            ObservableList<Employee> obsEmList = FXCollections.observableList(emList);
            FilteredList<Employee> filter = new FilteredList<>(obsEmList, event -> true);
            tfSearch.textProperty().addListener((Observable, oldValue, newValue) -> {
                filter.setPredicate(Employee -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String searchKey = newValue.toLowerCase();
                    String userName = Employee.getUserName();
                    String empName = Employee.getEmpName();
                    String empPhone = Employee.getEmpPhone();
                    String position = Employee.getPosition();
                    if (userName != null && userName.toLowerCase().contains(searchKey)) {
                        return true;
                    } else if (empPhone != null && empPhone.toLowerCase().contains(searchKey)) {
                        return true;
                    } else if (empName != null && empName.toLowerCase().contains(searchKey)) {
                        return true;
                    } else if (position != null && position.toLowerCase().contains(searchKey)) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });

            SortedList<Employee> sortedList = new SortedList<>(filter);
            sortedList.comparatorProperty().bind(tvEmployee.comparatorProperty());

            Platform.runLater(() -> {
                tvEmployee.setItems(sortedList);
            });
//end search 
            pdMonth.setOnAction(even -> {
                showEmployee();
            });
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
        tfPass.setText(String.valueOf(employee.getPassword()));
        tfRPass.setText(String.valueOf(employee.getPassword()));
        tfPhone.setText(String.valueOf(employee.getEmpPhone()));
        cbPosition.setValue(String.valueOf(employee.getPosition()));
        dpTimeSta.setValue(employee.getStartDate());
        cbGender.setValue(employee.isGender() ? "Male" : "Female");
        cbStatus.setValue(employee.isStatus() ? "Avaliable" : "Unavaliable");
        dpBirth.setValue(employee.getBirthDate());
        dpTimeSta.setValue(employee.getStartDate());

    }

    // hàm xuất file excel -- thả hàm vào button
    public void exportToExcel() throws Exception {
        WorkSessionDAO wdao = new WorkSessionDAO();
        month();
        LocalDate selectedDate = pdMonth.getValue();
        int selectedMonth;
        if (selectedDate == null) {
            selectedMonth = LocalDate.now().getMonthValue();
        } else {
            selectedMonth = selectedDate.getMonthValue();
        }
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Employee Data");

        Map<String, Double> totalWorkTime = wdao.getTotalWorkTimeByUserAndMonth(selectedMonth);
        try {
            emList = dao.getAll("Employee");
            for (Employee employee : emList) {
                for (String employee1 : totalWorkTime.keySet()) {
                    if (employee1.equals(employee.getUserName())) {
                        employee.setTotalWorkTime(totalWorkTime.get(employee1));
                    }
                }
            }
            // tạo tên cột excel
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("User Name");
            headerRow.createCell(1).setCellValue("Employee Name");
            headerRow.createCell(2).setCellValue("Gender");
            headerRow.createCell(3).setCellValue("Email");
            headerRow.createCell(4).setCellValue("Phone");
            headerRow.createCell(5).setCellValue("Position");
            headerRow.createCell(6).setCellValue("Status");
            headerRow.createCell(7).setCellValue("Start Date");
            headerRow.createCell(8).setCellValue("Total Work Time");

            // get dữ liệu vào cột 
            int rowNum = 1;
            for (Employee employee : emList) {
                XSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(employee.getUserName());
                row.createCell(1).setCellValue(employee.getEmpName());
                row.createCell(2).setCellValue(employee.isGender());
                row.createCell(3).setCellValue(employee.getEmail());
                row.createCell(4).setCellValue(employee.getEmpPhone());
                row.createCell(5).setCellValue(employee.getPosition());
                row.createCell(6).setCellValue(employee.isStatus());
                row.createCell(7).setCellValue(employee.getStartDate());
                row.createCell(8).setCellValue(employee.getTotalWorkTime());
            }

            // save file 
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Excel File");
            fileChooser.setInitialFileName("employee_data.xls");
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                FileOutputStream fileOut = new FileOutputStream(file);
                workbook.write(fileOut);
                fileOut.close();
                workbook.close();
                System.out.println("Export file Excel.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void power() {
        String pow = "Manager";
        if (pow.equals(SessionUtil.getEmployee().getPosition())) {
            btnDelete.setDisable(false);
            btnInsert.setDisable(false);
            btnUpdate.setDisable(false);
        } else {
            btnDelete.setDisable(true);
            btnInsert.setDisable(true);
            btnUpdate.setDisable(true);
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
        checkStatus();
        checkStart();
        checkGender();
        showEmployee();
        power();
    }

}
