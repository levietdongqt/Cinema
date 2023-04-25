/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import POJO.Employee;
import POJO.WorkSession;
import DAO.EmployeeDAO;
import DAO.WorkSessionDAO;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author BE BAU
 */
public class FXMLReportEmployeeController implements Initializable {

    List<Employee> emList;
    EmployeeDAO emdao = new EmployeeDAO();
    List<WorkSession> wList;
    WorkSessionDAO wdao = new WorkSessionDAO();
    @FXML
    private ComboBox<String> cbName;

    @FXML
    private TableColumn<WorkSession, LocalDateTime> colDay11;

    @FXML
    private TableColumn<WorkSession, LocalDateTime> colEndTime11;

    @FXML
    private TableColumn<WorkSession, LocalDateTime> colStartTime11;

    @FXML
    private TableColumn<WorkSession, Double> colWorking11;

    @FXML
    private DatePicker pdMoth;

    @FXML
    private TextField tfMoth1;

    @FXML
    private TextField tfTotalDay11;

    @FXML
    private TextField tfTotalHours11;

    @FXML
    private TextField tfUser1;

    @FXML
    private TextField tfUser11;

    @FXML
    private TableView<WorkSession> tvTime11;

    //load danh sách cho box name và set text cho user
    public void loadViewName() {
        List<String> emNames = new ArrayList<>();
        try {
            emList = emdao.getAll("Employee");
            for (Employee employee : emList) {
                String emName = employee.getEmpName();
                emNames.add(emName);
            }

            cbName.setItems(FXCollections.observableArrayList(emNames));

            //tạo sự kiện listen cho box name
            cbName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    Employee selecEm = null;
                    for (Employee employee : emList) {
                        if (employee.getEmpName().equals(t1)) {
                            selecEm = employee;
                            break;
                        }
                    }
                    if (selecEm != null) {
                        tfUser11.setText(selecEm.getUserName());
                    }
//                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void month() {
        pdMoth.setConverter(new StringConverter<LocalDate>() {
            String pattern = "MM/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                pdMoth.setPromptText(pattern.toLowerCase());
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
                    // Sử dụng định dạng "MM/yyyy" để parse chuỗi ngày tháng
                    return YearMonth.parse(string, dateFormatter).atDay(1);
                } else {
                    return null;
                }
            }
        });
    }

    public void showWS() {
        month();
        String user = tfUser11.getText();
        LocalDate selectedDate = pdMoth.getValue();
        int selectedMonth;
        if (selectedDate == null) {

            selectedMonth = LocalDate.now().getMonthValue();
        } else {
            selectedMonth = selectedDate.getMonthValue();
        }

        try {
            wList = wdao.getTime(user, selectedMonth);
            for (WorkSession session : wList) {
                Duration duration = Duration.between(session.getStartTime(), session.getEndTime());
                double totalHours = (double) duration.toHours();
                session.setTotalWorkTime(totalHours);
            }
            double totalHours = 0;
            for (WorkSession session : wList) {
                Duration duration = Duration.between(session.getStartTime(), session.getEndTime());
                double hours = (double) duration.toMinutes() / 60;
                totalHours += hours;
            }

            tfTotalHours11.setText(String.format("%.2f", totalHours));
            tfTotalDay11.setText(String.format("%.2f", totalHours / 8));

//            colDay11.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            colDay11.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            colDay11.setCellFactory(column -> {
                return new TableCell<WorkSession, LocalDateTime>() {
                    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                    @Override
                    protected void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            LocalDate date = item.toLocalDate();
                            setText(formatter.format(date));
                        }
                    }
                };
            });

            colStartTime11.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            colStartTime11.setCellFactory(column -> {
                return new TableCell<WorkSession, LocalDateTime>() {
                    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                    @Override
                    protected void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            LocalTime time = item.toLocalTime();
                            setText(formatter.format(time));
                        }
                    }
                };
            });
            colEndTime11.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
            colEndTime11.setCellFactory(column -> {
                return new TableCell<WorkSession, LocalDateTime>() {
                    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                    @Override
                    protected void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            LocalTime time = item.toLocalTime();
                            setText(formatter.format(time));
                        }
                    }
                };
            });
            colWorking11.setCellValueFactory(new PropertyValueFactory<>("totalWorkTime"));
            tvTime11.setItems(FXCollections.observableList(wList));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void action() {
        pdMoth.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                showWS();
            }
        });

        cbName.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                showWS();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadViewName();
        showWS();
        action();
    }

}
