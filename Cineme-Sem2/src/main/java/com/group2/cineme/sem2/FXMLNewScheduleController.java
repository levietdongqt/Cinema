/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.FilmDAO;
import DAO.RoomDAO;
import DAO.RoomTypeDetailsDAO;
import DAO.ScheduleDAO;
import DAO.ScheduleDAO;
import POJO.Film;
import POJO.Room;
import POJO.RoomSeatDetail;
import POJO.RoomType;
import POJO.RoomTypeDetails;
import POJO.Schedule;
import POJO.Schedule;
import Utils.AlertUtils;
import Utils.HibernateUtils;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author DONG
 */
public class FXMLNewScheduleController implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private DatePicker date;

    @FXML
    private TextField hourText;

    @FXML
    private TextField minuteText;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox comboBoxRoom;

    @FXML
    private ComboBox<RoomTypeDetails> comboBoxRoomType;

    @FXML
    private TableView<Schedule> tableViewTime;

    @FXML
    private CheckBox checkBoxYesNo;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtID;
    @FXML
    private Button btnCheck;
    @FXML
    private Label infoTableLabel;
    private Schedule newSchedule = new Schedule();
    private ScheduleDAO scheduleDAO = new ScheduleDAO();
    private RoomTypeDetailsDAO rtDetailDAO = new RoomTypeDetailsDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private RoomType selectedRoomType = new RoomType();
    private RoomTypeDetails selectedRtDetail = new RoomTypeDetails();
    private LocalDate selectedDate;
    private LocalTime selectedTime = LocalTime.of(8, 0);
    private Room selectedRoom;
    private Film selectedFilm;
    private String IdSchedule;
    private boolean activeNow = true;
    int count = 3;
    List<RoomTypeDetails> rtDetailList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkValidDate();
        comboBoxRoomHanlder();
        yesNoHanlder();
        creatTableView();
        FilmDAO filmDAO = new FilmDAO();
        try {
            selectedFilm = filmDAO.getById("PA729525400", Film.class);
        } catch (Exception ex) {
            Logger.getLogger(FXMLNewScheduleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtName.setText(selectedFilm.getFilmName());
        txtName.disableProperty();

    }

    private void yesNoHanlder() {
        checkBoxYesNo.setSelected(true);
        checkBoxYesNo.setOnAction((t) -> {
            activeNow = checkBoxYesNo.isSelected();
        });
    }

    @FXML
    private void btnCheckHanlder() {
        try {
            chechValidTime();
            Alert alert = AlertUtils.getAlert("No conflict, Time is valid", Alert.AlertType.INFORMATION);
            alert.show();
        } catch (Exception ex) {
            Alert alert = AlertUtils.getAlert(ex.getMessage(), Alert.AlertType.ERROR);
            alert.show();
        }
    }

    private void fixID() throws Exception {
        try {
            IdSchedule = "SC" + selectedRoom.getRoomID().substring(1) + selectedDate.getYear() + selectedDate.getDayOfYear() + selectedTime.getHour();
            txtID.setText(IdSchedule);
        } catch (Exception e) {
            throw new Exception("Choose Room, Please!!");
        }

    }

    private void comboBoxRoomHanlder() {
        try {
            List<Room> roomList = new ArrayList<>();
            roomDAO.getAll("Room").forEach(p -> roomList.add(p));
            ObservableList<Room> items = comboBoxRoom.getItems();
            items.addAll(roomList);
            comboBoxRoom.setValue("Choose Room");
            comboBoxRoom.setOnAction((t) -> {
                try {
                    selectedRoom = (Room) comboBoxRoom.getValue();
                    rtDetailList.clear();

                    selectedRoom.getRoomTypeDetailList().forEach((p) -> {
                        rtDetailList.add(p);
                    });

                    loadTableView();
                    comboBoxRoomTypeHanlder();
                    fixID();
                } catch (Exception ex) {
                    Logger.getLogger(FXMLNewScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(FXMLNewScheduleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void comboBoxRoomTypeHanlder() {
        comboBoxRoomType.setItems(FXCollections.observableArrayList(rtDetailList));
        comboBoxRoomType.getItems().sort((o1, o2) -> -o1.getRoomType().getrTypeName().length() + o2.getRoomType().getrTypeName().length());
        comboBoxRoomType.setValue(comboBoxRoomType.getItems().get(0));
        selectedRtDetail = comboBoxRoomType.getValue();
        comboBoxRoomType.setOnAction((t) -> {
            selectedRtDetail = comboBoxRoomType.getValue();

        });
    }

    private void checkValidDate() {
        LocalDate minDate = LocalDate.now();
        date.setValue(minDate);
        selectedDate = date.getValue();
        LocalDate maxDate = minDate.plusDays(30);
//        date.setDayCellFactory(picker -> new DateCell() {
//            @Override
//            public void updateItem(LocalDate date, boolean empty) {
//                super.updateItem(date, empty);
//                // Nếu ngày nằm ngoài khoảng thời gian cụ thể thì vô hiệu hóa
//                if (date.isBefore(minDate) || date.isAfter(maxDate)) {
//                    setDisable(true);
//                    setStyle("-fx-background-color: #C0C0C0;"); // Thiết lập màu nền khác cho ngày bị vô hiệu hóa
//                }
//            }
//        });
        date.setOnAction((t) -> {
            selectedDate = date.getValue();
            loadTableView();
            try {
                fixID();
            } catch (Exception ex) {
                Logger.getLogger(FXMLNewScheduleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private void chechValidTime() throws Exception {
        try {
            int hour = Integer.parseInt(hourText.getText());
            int minute = Integer.parseInt(minuteText.getText());
            selectedTime = LocalTime.of(hour, minute);
        } catch (Exception e) {
            throw new Exception("Format of time is error !");
        }
        List<Schedule> list = new ArrayList<>();
        fixID();
        list = scheduleDAO.checkTime(selectedDate.atTime(selectedTime),
                selectedDate.atTime(selectedTime.plusMinutes(selectedFilm.getDuration())), rtDetailList);
        if (!list.isEmpty()) {
            System.out.println("Time đã có");
            throw new Exception("Time is conflict !! \n Please choose a nother time.");
        }

    }

    private void creatTableView() {
        TableColumn<Schedule, String> colRoom = new TableColumn("Room");
        colRoom.setCellValueFactory((p) -> {
            return new SimpleObjectProperty<>(selectedRoom.getRoomName());
        });
        colRoom.setPrefWidth(80);

        TableColumn<Schedule, String> colFilm = new TableColumn("Film");
        colFilm.setCellValueFactory((p) -> {
            Schedule schedule = p.getValue();
            return new SimpleObjectProperty<>(schedule.getFilm().getFilmName());
        });
        colFilm.setPrefWidth(170);
        TableColumn<Schedule, String> colRoomType = new TableColumn("Room's Type");
        colRoomType.setCellValueFactory((p) -> {
            Schedule schedule = p.getValue();
            return new SimpleObjectProperty<>(schedule.getRoomTypeDetail().getRoomType().getrTypeName());
        });
        colRoomType.setPrefWidth(190);

        TableColumn<Schedule, String> colTime = new TableColumn("Time");
        colTime.setCellValueFactory((p) -> {
            Schedule schedule = p.getValue();
            LocalTime localTime = schedule.getStartTime().toLocalTime();
            String time = localTime.toString() + "- "
                    + schedule.getEndTime().toLocalTime();
            return new SimpleObjectProperty<>(time);
        });
        colTime.setPrefWidth(130);
        colTime.setStyle("-fx-text-alignment: center;");
        TableColumn<Schedule, Boolean> colStatus = new TableColumn("Status");
        colStatus.setCellValueFactory((p) -> {
            Schedule schedule = p.getValue();
            return new SimpleObjectProperty<>(schedule.isStatus());
        });
        colStatus.setPrefWidth(100);
        TableColumn<Schedule, Button> colBtnEdit = new TableColumn();
        colBtnEdit.setCellValueFactory((p) -> {
            Schedule schedule = p.getValue();
            Button btn = new Button("Edit");
            btn.setOnAction((t) -> {
            });
            return new SimpleObjectProperty<>(btn);
        });
        colBtnEdit.setPrefWidth(50);
        TableColumn<Schedule, Button> colBtnDelete = new TableColumn();
        colBtnDelete.setCellValueFactory((p) -> {
            Schedule schedule = p.getValue();
            Button btn = new Button("Delete");
            btn.setOnAction((t) -> {
                try {
                    String info = "Are you sure? \n Delete " + schedule.getStartTime().toLocalTime() + " - " + schedule.getEndTime().toLocalTime();
                    Alert alert = AlertUtils.getAlert(info, Alert.AlertType.CONFIRMATION);
                   Optional<ButtonType> result = alert.showAndWait();
                   if(result.get().getText().equalsIgnoreCase("OK")){
                       scheduleDAO.delete(schedule.getScheduleID(), Schedule.class);
                       loadTableView();
                   }
                } catch (Exception ex) {
                    Logger.getLogger(FXMLNewScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return new SimpleObjectProperty<>(btn);
        });
        colBtnDelete.setPrefWidth(70);
        this.tableViewTime.getColumns().addAll(colRoom, colRoomType, colFilm, colTime, colStatus, colBtnEdit, colBtnDelete);
    }

    private void loadTableView() {
        infoTableLabel.setText("Infomation for " + selectedDate.toString());
        List<Schedule> scheduleList = new ArrayList<>();
        try {
            //Lấy dữ liệu trong 1 ngày selectedDate
            scheduleList = scheduleDAO.
                    getToView(selectedDate.atStartOfDay(), selectedDate.plusDays(1).atStartOfDay(), rtDetailList);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        this.tableViewTime.setItems(FXCollections.observableList(scheduleList));
    }

    @FXML
    private void buttonSaveHanlder() {
        try {
            chechValidTime();
            newSchedule.setStatus(true);
            newSchedule.setRoomTypeDetail(selectedRtDetail);
            newSchedule.setStartTime(selectedDate.atTime(selectedTime));
            newSchedule.setEndTime(selectedDate.atTime(selectedTime.plusMinutes(selectedFilm.getDuration())));
            newSchedule.setScheduleID(IdSchedule);
            newSchedule.setStatus(activeNow);
            newSchedule.setFilm(selectedFilm);
            scheduleDAO.add(newSchedule);
            Alert alert = AlertUtils.getAlert("Process successfully!", Alert.AlertType.INFORMATION);
            alert.show();
        } catch (Exception e) {
            Alert alert = AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR);
            alert.show();
        }
    }

}
