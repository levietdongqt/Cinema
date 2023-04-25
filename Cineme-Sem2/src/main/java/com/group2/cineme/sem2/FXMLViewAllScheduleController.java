/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.FilmDAO;
import POJO.Film;
import POJO.Room;
import POJO.Schedule;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author DONG
 */
public class FXMLViewAllScheduleController implements Initializable {

    public FXMLViewAllScheduleController() {
    }

    public FXMLViewAllScheduleController(LocalDateTime selectedDate, Film film) {
        this.selectedDate = selectedDate;
        this.film = film;
    }

    @FXML
    private TableView tableView;
    @FXML
    private Label filmLabel;
    @FXML
    private DatePicker datePicker;
    LocalDateTime selectedDate;
    Film film;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        creatTableView();
        loadDataTableView();
        setUpStartPage();

    }

    private void setUpStartPage() {
        filmLabel.setText(film.getFilmName());
        datePicker.setValue(selectedDate.toLocalDate());
        LocalDate minDate = film.getStartDate().toLocalDate();
        selectedDate = datePicker.getValue().atStartOfDay();
        LocalDate maxDate = film.getEndDate().toLocalDate();

        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                // Nếu ngày nằm ngoài khoảng thời gian cụ thể thì vô hiệu hóa
                if (date.isBefore(minDate) || date.isAfter(maxDate)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #C0C0C0;"); // Thiết lập màu nền khác cho ngày bị vô hiệu hóa
                }
            }
        });
    }

    @FXML
    private void setUpDatePicker() {
                selectedDate = datePicker.getValue().atStartOfDay();

                loadDataTableView();
    }

    private void creatTableView() {
        TableColumn<Schedule, Room> colRoom = new TableColumn("Room");
        colRoom.setCellValueFactory((p) -> {
            Schedule schedule = p.getValue();
            return new SimpleObjectProperty<>(schedule.getRoomTypeDetail().getRoom());
        });
        colRoom.setPrefWidth(90);
        TableColumn<Schedule, String> colRoomType = new TableColumn("Room's Type");
        colRoomType.setCellValueFactory((p) -> {
            Schedule schedule = p.getValue();
            return new SimpleObjectProperty<>(schedule.getRoomTypeDetail().getRoomType().getrTypeName());
        });
        colRoomType.setPrefWidth(200);

        TableColumn<Schedule, String> colTime = new TableColumn("Time");
        colTime.setCellValueFactory((p) -> {
            Schedule schedule = p.getValue();
            LocalTime localTime = schedule.getStartTime().toLocalTime();
            String time = localTime.toString() + "- "
                    + schedule.getEndTime().toLocalTime();
            return new SimpleObjectProperty<>(time);
        });
        colTime.setPrefWidth(100);
        TableColumn<Schedule, Boolean> colNote = new TableColumn("Note");
        colNote.setCellValueFactory(new PropertyValueFactory("note"));
        colNote.setPrefWidth(100);
        TableColumn<Schedule, Boolean> colStatus = new TableColumn("Status");
        colStatus.setCellValueFactory((p) -> {
            Schedule schedule = p.getValue();
            return new SimpleObjectProperty<>(schedule.isStatus());
        });
        colStatus.setPrefWidth(100);
        tableView.getColumns().addAll(colRoom, colRoomType, colTime, colNote, colStatus);
    }

    private void loadDataTableView() {
        FilmDAO filmDAO = new FilmDAO();
        tableView.setItems(FXCollections.observableArrayList(filmDAO.getScheduleByFilmDate(film, selectedDate)));
    }

}
