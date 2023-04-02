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
import java.io.IOException;
import java.net.URL;
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
    Button btnClose;
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
    private ComboBox<Film> comboBoxFilm;
    @FXML
    private ComboBox<RoomTypeDetails> comboBoxRoomType;
    @FXML
    Button btnChange;
    private Schedule schedule;
    private Room room;
    private LocalTime selectedTime;
    ScheduleDAO scheduleDAO = new ScheduleDAO();
    List<RoomTypeDetails> rtDetailsList = new ArrayList<>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //setUpComboBoxFilm();

    }

    @FXML  //Cài đặt cho nút tắt Popup
    private void setUpBtnColse() {
        Popup popup = (Popup) btnClose.getScene().getWindow();
        popup.hide();
    }

    @FXML
    private void setUpBtnChange() {

        try {
            chechValidTime();
            schedule.setStartTime(schedule.getStartTime().toLocalDate().atTime(selectedTime));
            
            schedule.setEndTime(schedule.getStartTime().toLocalDate().atTime(selectedTime)
                    .plusMinutes(schedule.getFilm().getDuration()));
            schedule.setRoomTypeDetail(comboBoxRoomType.getValue());
            scheduleDAO.update(schedule);
        } catch (Exception ex) {
           Alert alert = AlertUtils.getAlert(ex.getMessage(), Alert.AlertType.ERROR);
           alert.showAndWait();
        }
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
        list = scheduleDAO.checkTime(schedule.getStartTime().toLocalDate().atTime(selectedTime),
                schedule.getStartTime().toLocalDate().
                        atTime(selectedTime.plusMinutes(comboBoxFilm.getValue().getDuration())),rtDetailsList );
        if (!list.isEmpty()) {
            System.out.println("Time đã có");
            throw new Exception("Time is conflict !! \n Please choose a nother time.");
        }

    }

    private void setUpComboBoxFilm() {
        comboBoxFilm.setItems(FXCollections.observableList(SessionUtil.getMapFilm()));
    }

    private void setUpComboBoxRoomType() {
        rtDetailsList.clear();
        room.getRoomTypeDetailList().forEach((t) -> {
            rtDetailsList.add(t);
        });
        comboBoxRoomType.setItems(FXCollections.observableList(rtDetailsList));

    }

    private void loadDataToView() {
        comboBoxFilm.setValue(schedule.getFilm());
        comboBoxRoomType.setValue(schedule.getRoomTypeDetail());
        roomLabel.setText(room.getRoomName());
        dateLabel.setText(schedule.getStartTime().toLocalDate().toString());
        activeCheckBox.setSelected(schedule.isStatus());
        hourText.setText(String.valueOf(schedule.getStartTime().toLocalTime().getHour()));
        minuteText.setText(String.valueOf(schedule.getStartTime().toLocalTime().getMinute()));
        hourTextEnd.setText(String.valueOf(schedule.getEndTime().toLocalTime().getHour()));
        minuteTextEnd.setText(String.valueOf(schedule.getEndTime().toLocalTime().getMinute()));
    }

    void getData(Schedule schedule, Room room) {
        this.schedule = schedule;
        this.room = room;
        setUpComboBoxRoomType();    
        loadDataToView();
    }

}
