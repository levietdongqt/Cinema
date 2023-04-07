/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.FilmDAO;
import DAO.ScheduleDAO;
import POJO.Film;
import POJO.Schedule;
import Utils.AlertUtils;
import static com.group2.cineme.sem2.App.scene;



import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;



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
    
    private List<Film> films; 
    
    


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
    public void bookTicketHandler() {
        loadDataView(LocalDateTime.now());
        loadViewComboBoxFilm();
    }
    public void buttonUndoHandler(){
        this.tableViewSchedule.setItems(FXCollections.observableList(films));
    }

    public void loadTableView() {

        TableColumn<Film, String> colDay = new TableColumn("Day");
        colDay.setCellValueFactory((p) -> {
            Film sc = p.getValue();
            LocalDate a = LocalDate.now();
            for (Schedule schedule : sc.getListSchedule()) {
                a = schedule.getStartTime().toLocalDate();
            }
            return new SimpleObjectProperty<>(a.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });
        colDay.setPrefWidth(250);

        TableColumn<Film, ImageView> colFilmView = new TableColumn("Image Film");
        colFilmView.setCellValueFactory((p) -> {
            Film sc = p.getValue();
            String filmImage = sc.getImageUrl();

            ImageView imageView = new ImageView();
            File file = new File(filmImage);
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(220);
            imageView.setFitHeight(250);
            return new SimpleObjectProperty<>(imageView);
        });
        colFilmView.setPrefWidth(220);

        TableColumn<Film, String> colNameFilm = new TableColumn("Film Name");
        colNameFilm.setCellValueFactory((o) -> {
            Film sc = o.getValue();
            String filmName = sc.getFilmName();
            return new SimpleObjectProperty<>(filmName);
        });
        colNameFilm.setPrefWidth(250);

        TableColumn colDuration = new TableColumn("Duration");
        colDuration.setCellValueFactory(new PropertyValueFactory("duration"));
        colDuration.setPrefWidth(200);

        TableColumn<Film, HBox> colTime = new TableColumn("Time");
        colTime.setCellValueFactory((p) -> {
            HBox hbox = new HBox();
            ComboBox<Schedule> cb = new ComboBox<>();
            cb.setUserData(hbox);
            Film film = p.getValue();
            Set<Schedule> lists = film.getListSchedule();
            List<Schedule> result = lists.stream().sorted((o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime())).collect(Collectors.toList());
            cb.setItems(FXCollections.observableList(new ArrayList<>(result)));
            Button button = new Button("Buy ticket");
            button.setOnAction((t) -> {
                try {
                    Schedule sc = cb.getValue();
                    if(sc==null){
                        throw new Error("You need choose Schedule");
                    }else{
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
                    
//                ComboBox<Schedule> cbInHbox = (ComboBox<Schedule>) hboxWithComboBox.getChildren().get(0);
                    /*lay gia tri cua sc tai day*/
                } catch (IOException ex) {
                    Logger.getLogger(FXMLShowScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Error e){
                    Alert alert = AlertUtils.getAlert(e.getMessage(), Alert.AlertType.ERROR);
                    alert.setTitle("Error!!!!!!");
                    
                    alert.show();
                    
                }
            });
            
            hbox.getChildren().addAll(cb, button);
            button.setUserData(hbox);
            hbox.setStyle("-fx-alignment: CENTER;");
            hbox.setSpacing(100);
            for (Node node : hbox.getChildren()) {
                node.setStyle("-fx-alignment: CENTER;");
            }
            return new SimpleObjectProperty<>(hbox);
        });
        colTime.setPrefWidth(550);
        this.tableViewSchedule.getColumns().addAll(colDay, colFilmView, colNameFilm, colDuration, colTime);
        ObservableList<TableColumn<Film, ?>> columns = this.tableViewSchedule.getColumns();
        for (TableColumn<Film, ?> column : columns) {
            column.setStyle("-fx-alignment: CENTER;");
        }
        this.tableViewSchedule.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-style: solid;");
    }

    public void loadDataView(LocalDateTime dateTime) {
        ScheduleDAO sd = new ScheduleDAO();
//        LocalDateTime currentDate = LocalDateTime.now();
        FilmDAO fd = new FilmDAO();
        films = fd.getScheduleByDateTime(dateTime);
        System.out.println(films);
        for (Film film : films) {
            film.setListSchedule(new HashSet<>(sd.getScheduleByDateTime(film.getFilmID(), dateTime)));
        }

        this.tableViewSchedule.setItems(FXCollections.observableList(films));

    }

    public void loadViewComboBox() {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime startDate = currentDate.of(currentDate.toLocalDate(), LocalTime.MIN);
//        System.out.println(startDate.plusDays(1));

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
            List<Film> searchFilm = films.stream().filter((t) -> t.getFilmName().equals(this.comboBoxFilm.getSelectionModel().getSelectedItem().getFilmName())).collect(Collectors.toList());
            this.tableViewSchedule.setItems(FXCollections.observableList(searchFilm));
        });

    }

    public void setActionForTableView(){
        this.tableViewSchedule.setOnMouseReleased((t) -> {
            Film p = this.tableViewSchedule.getSelectionModel().getSelectedItem();
            System.out.println(p.getFilmName());
            try {
                System.out.println(p.getFilmName());
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLViewFilmDetails.fxml"));
                 fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
                        @Override
                        public Object call(Class<?> param) {
                            return new FXMLViewFilmDetailsController(p);
                        }

                    });
                System.out.println(p.getFilmName());
                AnchorPane popupContent = fxmlLoader.load();
               
                popup.getContent().add(popupContent);
                System.out.println(p.getFilmName());
                popupContent.setStyle("-fx-background-color:grey;-fx-text-fill: white;");
                popup.show(vboxShowSchedule.getScene().getWindow());
                System.out.println(p.getFilmName());
                vboxShowSchedule.setDisable(true);
            } catch (IOException ex) {
                Logger.getLogger(FXMLShowScheduleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
   
}
