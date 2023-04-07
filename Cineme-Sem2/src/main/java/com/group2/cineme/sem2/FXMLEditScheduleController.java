/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.ScheduleDAO;
import POJO.Film;
import POJO.Room;
import POJO.RoomTypeDetails;
import POJO.Schedule;
import Utils.AlertUtils;
import Utils.SessionUtil;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DONG
 */
public class FXMLEditScheduleController implements Initializable {

    @FXML
    private TextField hourText;
    @FXML
    private TextField minuteText;
    @FXML
    private TextField hourTextEnd;
    @FXML
    private TextField minuteTextEnd;
    @FXML
    private Label roomLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private CheckBox activeCheckBox;
    @FXML
    private Label filmLabel;
    @FXML
    private ComboBox<RoomTypeDetails> comboBoxRoomType;
    @FXML
    Button btnChange;
    @FXML
    Label errHour;
    @FXML
    private FontAwesomeIcon invalidIcon;
    @FXML
    private FontAwesomeIcon validIcon;
    @FXML
    private TextField noteFeild;
    @FXML
    private Label errNote;
    private Schedule schedule;
    private Room room;
    private LocalTime selectedTime;
    ScheduleDAO scheduleDAO = new ScheduleDAO();
    List<RoomTypeDetails> rtDetailsList;
    List<Schedule> scheduleList;
    RoomTypeDetails selectedRtDetail;
    LocalDate selectedDate;
    public FXMLEditScheduleController() {
    }

    public FXMLEditScheduleController(LocalDate selectedDate,Schedule schdule, List<Schedule> scheduleList, Room room, RoomTypeDetails selectedRtDetail, List<RoomTypeDetails> rtDetailList) {
        this.schedule = schdule;
        this.room = room;
        this.scheduleList = scheduleList;
        this.rtDetailsList = rtDetailList;
        this.selectedRtDetail = selectedRtDetail;
        this.selectedDate= selectedDate;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpComboBoxRoomType();
        loadDataToView();
    }

    private void loadDataToView() {
        noteFeild.setText(schedule.getNote());
        filmLabel.setText(schedule.getFilm().toString());
        roomLabel.setText(room.getRoomName());
        dateLabel.setText(schedule.getStartTime().toLocalDate().toString());
        activeCheckBox.setSelected(schedule.isStatus());
        hourText.setText(String.valueOf(schedule.getStartTime().toLocalTime().getHour()));
        minuteText.setText(String.valueOf(schedule.getStartTime().toLocalTime().getMinute()));
        hourTextEnd.setText(String.valueOf(schedule.getEndTime().toLocalTime().getHour()));
        minuteTextEnd.setText(String.valueOf(schedule.getEndTime().toLocalTime().getMinute()));
        comboBoxRoomType.setValue(selectedRtDetail);
        selectedTime = schedule.getStartTime().toLocalTime();

    }

    @FXML
    private void setUpNote() {
        errNote.setVisible(false);
        if (noteFeild.getText().length() > 30) {
            errNote.setVisible(true);
            noteFeild.setText(noteFeild.getText().substring(0, 30));
        }
        schedule.setNote(noteFeild.getText());
        System.out.println(schedule.getNote());
    }

    @FXML
    private void setUpActiveCheckBox() {

    }

    @FXML
    private void setUpBtnChange() {

        try {
            if (errHour.isVisible()) {
                return;
            };
            if (errNote.isVisible()) {
                return;
            }
            chechValidTime();
            schedule.setStartTime(schedule.getStartTime().toLocalDate().atTime(selectedTime));
            schedule.setEndTime(schedule.getStartTime().toLocalDate().atTime(selectedTime)
                    .plusMinutes(schedule.getFilm().getDuration()));
            schedule.setRoomTypeDetail(comboBoxRoomType.getValue());
            schedule.setStatus(activeCheckBox.isSelected());
            scheduleDAO.update(schedule);
            Alert alert = AlertUtils.getAlert("Update successfully!", Alert.AlertType.INFORMATION);
            alert.show();
            Stage stage = (Stage) btnChange.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            Alert alert = AlertUtils.getAlert(ex.getMessage(), Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @FXML
    private void setUpTextFeildTime() {
        try {
            int hour = Integer.parseInt(hourText.getText());
            int minute = Integer.parseInt(minuteText.getText());
            selectedTime = LocalTime.of(hour, minute);
            errHour.setVisible(false);
            hourTextEnd.setText(String.valueOf(selectedTime.plusMinutes(schedule.getFilm().getDuration()).getHour()));
            minuteTextEnd.setText(String.valueOf(selectedTime.plusMinutes(schedule.getFilm().getDuration()).getMinute()));
            try {
                chechValidTime();
                validIcon.setVisible(true);
                invalidIcon.setVisible(false);
            } catch (Exception e) {
                validIcon.setVisible(false);
                invalidIcon.setVisible(true);
            }

        } catch (Exception e) {
            errHour.setVisible(true);
            validIcon.setVisible(false);
            invalidIcon.setVisible(true);
        }

    }

    private void chechValidTime() throws Exception {
        List<Schedule> list = new ArrayList<>();
        LocalDateTime selectedStartTime = selectedTime.atDate(selectedDate);
        LocalDateTime selectedEndTime = selectedTime.atDate(selectedDate).plusMinutes(schedule.getFilm().getDuration());
        for (Schedule schedule : scheduleList) {
            if (schedule.getStartTime().isEqual(this.schedule.getStartTime())) {
                continue;
            }
            LocalDateTime startTime = schedule.getStartTime();
            LocalDateTime endTime = schedule.getEndTime();

            if (!((selectedStartTime.isAfter(endTime) && selectedEndTime.isAfter(endTime))
                    || (selectedStartTime.isBefore(startTime) && selectedEndTime.isBefore(startTime)))) {
                throw new Exception("Time is conflict !! \n Please choose a nother time.");
            }
        };
//        if (!list.isEmpty()) {
//            if (list.size() == 1 && list.get(0).getStartTime().isEqual(schedule.getStartTime())) {
//                return;
//            };
//            System.out.println("Time đã có");
//            throw new Exception("Time is conflict !! \n Please choose a nother time.");
//        }

    }

    private void setUpComboBoxRoomType() {
        comboBoxRoomType.setItems(FXCollections.observableList(rtDetailsList));
        comboBoxRoomType.setOnAction((t) -> {
            selectedRtDetail = comboBoxRoomType.getValue();
        });

    }

//    void getData(Schedule schedule, Room room) {
//        this.schedule = schedule;
//        this.room = room;
//        setUpComboBoxRoomType();
//        loadDataToView();
//    }
}
