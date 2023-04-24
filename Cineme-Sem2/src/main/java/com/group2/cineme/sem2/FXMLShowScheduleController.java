/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.FilmDAO;
import DAO.RoomSeatDetailDAO;
import DAO.ScheduleDAO;
import DAO.TicketDAO;
import POJO.Film;
import POJO.RoomType;
import POJO.Schedule;
import POJO.Ticket;
import Utils.AlertUtils;
import Utils.MyException;
import static com.group2.cineme.sem2.App.scene;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author thuhuytran
 */
public class FXMLShowScheduleController implements Initializable {

    @FXML
    private TableView<Film> tableViewSchedule;

    @FXML
    private ComboBox<LocalDateTime> comboBoxDay;
    @FXML
    private ComboBox<Film> comboBoxFilm;

    @FXML
    private VBox vboxShowSchedule;

    private Popup popup = new Popup();

    private List<Film> films = new ArrayList<>();
    private int second;
    private int max;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadViewComboBox();
        loadTableView();
        loadDataView(LocalDateTime.now());
        setActionForComboBox();

        loadViewComboBoxFilm();
        setActionForComboBoxFilm();

        setActionForTableView();
        popup.setOnHiding((t) -> {   // Hiện lại trang Home khi popUp tắt
            vboxShowSchedule.setDisable(false);
        });
    }

    //ButtonHandler
    @FXML
    public void bookTicketHandler() {
        loadDataView(LocalDateTime.now());
        loadViewComboBoxFilm();
    }

    @FXML
    public void buttonUndoHandler() {
        this.tableViewSchedule.setItems(FXCollections.observableList(films));
    }

    public void loadTableView() {

        TableColumn<Film, String> colDay = new TableColumn("DAY-SHOWTIME");
        colDay.setCellValueFactory((p) -> {
            Film sc = p.getValue();
            LocalDate a = LocalDate.now();
            for (Schedule schedule : sc.getListSchedule()) {
                a = schedule.getStartTime().toLocalDate();
            }
            return new SimpleObjectProperty<>(a.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });
        colDay.setPrefWidth(250);

        TableColumn<Film, ImageView> colFilmView = new TableColumn("IMAGE");
        colFilmView.setCellValueFactory((p) -> {
            Film sc = p.getValue();
            String view = sc.getImageUrl();
            Path path = Paths.get(view);
            String fileName = path.getFileName().toString();
            ImageView imageView = new ImageView();
//            File file = new File("C:\\1Study-Aptech\\"+filmImage);
////            ClassLoader classLoader = getClass().getClassLoader();
//            System.out.println(file.getName());
//            InputStream inputStream = getClass().getResourceAsStream("images/" + file.getName());
////            URL imageURL = classLoader.getResource("images\\"+file.getName());
//            System.out.println(inputStream.toString());
////            String imageUrlString = imageURL.toExternalForm();
            InputStream inputStream = getClass().getResourceAsStream("/images/"+fileName);
            Image image = new Image(inputStream);
            imageView.setImage(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(230);
            return new SimpleObjectProperty<>(imageView);
        });
        colFilmView.setPrefWidth(200);
        TableColumn<Film, String> colNameFilm = new TableColumn("NAME");
        colNameFilm.setCellValueFactory((o) -> {
            Film sc = o.getValue();
            String filmName = sc.getFilmName();
            return new SimpleObjectProperty<>(filmName);
        });
        colNameFilm.setPrefWidth(250);
        TableColumn colDuration = new TableColumn("DURATION");
        colDuration.setCellValueFactory(new PropertyValueFactory("duration"));
        colDuration.setPrefWidth(100);
        TableColumn<Film, HBox> colTime = new TableColumn();
        colTime.setCellValueFactory((p) -> {
            HBox hbox = new HBox();
            VBox vbox = new VBox();
            ComboBox<Schedule> cb = new ComboBox<>();
            Text text = new Text();
            cb.setUserData(hbox);
            Film film = p.getValue();
            Set<Schedule> lists = film.getListSchedule();
            List<Schedule> result = lists.stream().sorted((o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime())).collect(Collectors.toList());
            for (Schedule schedule : result) {
                if (schedule.getStartTime().toLocalDate().equals(LocalDate.now()) && (schedule.getStartTime().toLocalTime().toSecondOfDay() - (LocalTime.now().toSecondOfDay()) <= 300)) {
                    System.out.println(LocalTime.now().toSecondOfDay());
                    System.out.println(schedule.getStartTime().toLocalTime().toSecondOfDay());
                    text = changeColorTextFlow(schedule.getStartTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " IS COMING", Color.WHITE, Color.RED);
                }
            }
            cb.setItems(FXCollections.observableList(new ArrayList<>(result)));
            Button button = new Button("BUY-TICKET");
            button.setPrefHeight(50);
            button.setOnAction((t) -> {
                try {
                    Schedule sc = cb.getValue();
                    if (sc == null) {
                        throw new Error("You need choose Schedule");
                    } else {
                        HBox hboxWithComboBox = (HBox) button.getUserData();
                        FXMLLoader fxmlLoader1 = new FXMLLoader(App.class.getResource("FXMLTicket.fxml"));
                        fxmlLoader1.setControllerFactory(new Callback<Class<?>, Object>() {
                            @Override
                            public Object call(Class<?> param) {
                                return new FXMLTicketController(sc);
                            }

                        });
                        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLHome.fxml"));
                        scene.setRoot(fxmlLoader.load());
                        FXMLHomeController homeController = fxmlLoader.getController();
                        homeController.setCenter(fxmlLoader1.load());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(FXMLShowScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Error e) {
                    Alert alert = AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR);
                    alert.show();
                }
            });
            cb.setOnAction((t) -> {
                ScheduleDAO sd = new ScheduleDAO();
                RoomSeatDetailDAO rsdd = new RoomSeatDetailDAO();
                TicketDAO td = new TicketDAO();
                try {
                    Schedule schedule = sd.getById(cb.getSelectionModel().getSelectedItem().getScheduleID(), Schedule.class);
                    if (schedule.getStartTime().isBefore(LocalDateTime.now().minusSeconds(60))) {
                        schedule.setStatus(false);
                        sd.update(schedule);
                    }
                    if (schedule.isStatus() == false) {
                        throw new MyException("Time is end!!!!");
                    }
                    List<Ticket> tickets = td.getTicketBySchedule(schedule);
                    RoomType roomtype = schedule.getRoomTypeDetail().getRoomType();
                    int countSeatDetails = rsdd.countSeatBySchedule(roomtype.getrTypeID());
                    if (tickets.size() == countSeatDetails) {
                        schedule.setStatus(false);
                        sd.update(schedule);
                        throw new Error("Room is full");
                    }
                } catch (MyException | Error ex) {
                    String info = ex.getMessage() + "\n" + "Are you reload the table?";
                    Alert alert = AlertUtils.getAlert(info, Alert.AlertType.CONFIRMATION);
                    Optional<ButtonType> results = alert.showAndWait();
                    if (results.get().getText().equalsIgnoreCase("OK")) {
                        loadDataView(LocalDateTime.now());
                        loadViewComboBoxFilm();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(FXMLShowScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            vbox.getChildren().addAll(cb, text);
            hbox.getChildren().addAll(vbox, button);
            button.setUserData(hbox);
            hbox.setStyle("-fx-alignment: CENTER;");
            hbox.setSpacing(50);
            for (Node node : hbox.getChildren()) {
                node.setStyle("-fx-alignment: CENTER;");
            }
            return new SimpleObjectProperty<>(hbox);
        });
        colTime.setPrefWidth(550);

        TableColumn<Film, Integer> colView = new TableColumn<>();
        colView.setCellValueFactory(new PropertyValueFactory<>("viewFilm"));
        colView.setCellFactory((p) -> {
            return new TableCell<>() {
                @Override
                protected void updateItem(Integer t, boolean bln) {
                    super.updateItem(t, bln);
                    setAlignment(Pos.CENTER);
                    Text tf = new Text();
                    if (bln) {
                        setText(null);
                    } else if (t == 0) {
                        tf = changeColorTextFlow("NEW!!!", Color.WHITE, Color.BLUE);
                    } else if (t == max) {
                        tf = changeColorTextFlow("TOP 1!!!", Color.WHITE, Color.RED);
                    } else if (t == second) {
                        tf = changeColorTextFlow("TOP 2!!!", Color.WHITE, Color.ORANGE);
                    } else {
                        tf = changeColorTextFlow("HOT!!!", Color.WHITE, Color.CHARTREUSE);
                    }
                    setGraphic(tf);
                }
            };
        });
        this.tableViewSchedule.getColumns().addAll(colView, colDay, colFilmView, colNameFilm, colDuration, colTime);
        ObservableList<TableColumn<Film, ?>> columns = this.tableViewSchedule.getColumns();
        for (TableColumn<Film, ?> column : columns) {
            column.setStyle("-fx-alignment: CENTER;");
        }
        this.tableViewSchedule.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-style: solid;");
    }

    public void loadDataView(LocalDateTime dateTime) {
        ScheduleDAO sd = new ScheduleDAO();
        FilmDAO fd = new FilmDAO();
        films = fd.getScheduleByDateTime(dateTime);
        for (Film film : films) {
            film.setListSchedule(new HashSet<>(sd.getScheduleByDateTime(film.getFilmID(), dateTime)));
        }
        films.sort((o1, o2) -> o2.getViewFilm() - o1.getViewFilm());
        try {
            if (films.size() == 1) {
                max = films.get(0).getViewFilm();
            } else {
                max = films.get(0).getViewFilm();
                second = films.get(1).getViewFilm();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.tableViewSchedule.setItems(FXCollections.observableList(films));

    }

    public void loadViewComboBox() {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime startDate = currentDate.of(currentDate.toLocalDate(), LocalTime.MIN);
        List<LocalDateTime> listDateTime = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            listDateTime.add(startDate.plusDays(i));
        }
        this.comboBoxDay.setItems(FXCollections.observableList(listDateTime));
        comboBoxDay.setCellFactory(new Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>>() {
            @Override
            public ListCell<LocalDateTime> call(ListView<LocalDateTime> p) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(LocalDateTime t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            String formatter = t.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            setText(formatter);
                        } else {
                            setText("");
                        }
                    }

                };
            }
        });
        //Đoạn mã covert localdatetim ra localdate
        StringConverter<LocalDateTime> converter = new StringConverter<LocalDateTime>() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDateTime dateTime) {
                if (dateTime != null) {
                    return formatter.format(dateTime);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDateTime fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDateTime.parse(string, formatter);
                } else {
                    return null;
                }
            }
        };
        comboBoxDay.setConverter(converter);
    }

    public void setActionForComboBox() {
        this.comboBoxDay.setOnAction((t) -> {
            LocalDateTime choice = this.comboBoxDay.getSelectionModel().getSelectedItem();
            loadDataView(choice);
            loadViewComboBoxFilm();
        });
    }

    public void loadViewComboBoxFilm() {
        this.comboBoxFilm.setItems(FXCollections.observableList(films));
    }

    public void setActionForComboBoxFilm() {
        this.comboBoxFilm.setOnAction((o) -> {
            try {
                List<Film> searchFilm = films.stream().filter((t) -> t.getFilmName().equals(this.comboBoxFilm.getSelectionModel().getSelectedItem().getFilmName())).collect(Collectors.toList());
                this.tableViewSchedule.setItems(FXCollections.observableList(searchFilm));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        });

    }

    public void setActionForTableView() {
        this.tableViewSchedule.setOnMouseReleased((t) -> {
            try {
                Film p = this.tableViewSchedule.getSelectionModel().getSelectedItem();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLViewFilmDetails.fxml"));
                fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
                    @Override
                    public Object call(Class<?> param) {
                        return new FXMLViewFilmDetailsController(p);
                    }

                });
                AnchorPane popupContent = fxmlLoader.load();
                popup.getContent().add(popupContent);
                popupContent.setStyle("-fx-background-color:grey;-fx-text-fill: white;");
                popup.show(vboxShowSchedule.getScene().getWindow());
                vboxShowSchedule.setDisable(true);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    /*Thay đổi màu cho text chop chop */
    public Text changeColorTextFlow(String content, Color beginColor, Color endColor) {
        Text text = new Text(content);
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-weight: bold;");

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.0), new KeyValue(text.fillProperty(), beginColor)),
                new KeyFrame(Duration.seconds(2.0), new KeyValue(text.fillProperty(), endColor))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        return text;
    }

}
