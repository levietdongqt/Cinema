package com.group2.cineme.sem2;

import DAO.FilmDAO;
import DAO.FilmGenreDAO;
import DAO.ScheduleDAO;
import POJO.Actors;
import POJO.Film;
import POJO.FilmGenre;
import POJO.Schedule;
import Utils.AlertUtils;
import Utils.SessionUtil;
import static com.group2.cineme.sem2.App.scene;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author thuhuytran
 */
public class FXMLFilmController implements Initializable {

    @FXML
    private TableView<Film> tableViewFilm;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<FilmGenre> comboGenres;

    @FXML
    private TextField txtName;
    List<Film> listFilm;
    List<FilmGenre> listGenre;

    public FXMLFilmController() {
         
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        loadTableView();
        loadDataTableView();
        loadDataCombobox();
    }

    //Khu vuc nut
    public void buttonAddNewHandler() throws IOException {
        App.setView("FXMLNewFilm");
    }

    public void buttonSearchByDate() {
        List<Film> listAfterStartDate = listFilm.stream().filter((t) -> t.getStartDate().after(Date.valueOf(this.datePicker.getValue()))).collect(Collectors.toList());
        if (listAfterStartDate.isEmpty()) {
            AlertUtils.getAlert("Don't have record you need", Alert.AlertType.WARNING).show();
            this.tableViewFilm.setItems(FXCollections.observableList(listFilm));
        } else {
            this.tableViewFilm.setItems(FXCollections.observableList(listAfterStartDate));
        }

    }

    public void buttonSearchByName() {
        List<Film> listByName = listFilm.stream().filter((t) -> t.getFilmName().toLowerCase().contains(this.txtName.getText().toLowerCase())).collect(Collectors.toList());
        if (listByName.isEmpty()) {
            AlertUtils.getAlert("Don't have record you need", Alert.AlertType.WARNING).show();
            this.tableViewFilm.setItems(FXCollections.observableList(listFilm));
        } else {
            this.tableViewFilm.setItems(FXCollections.observableList(listByName));
        }
    }

    public void buttonSearchByGenre() {
        List<Film> listByGenre = new ArrayList<>();
        listFilm.forEach((t) -> {
            for (FilmGenre filmGenre : t.getListGenre()) {
                if (filmGenre.getfGenreName().equals(this.comboGenres.getSelectionModel().getSelectedItem().getfGenreName())) {
                    listByGenre.add(t);
                }
            }
        });
        if (listByGenre.isEmpty()) {
            AlertUtils.getAlert("Don't have record you need", Alert.AlertType.WARNING).show();
            this.tableViewFilm.setItems(FXCollections.observableList(listFilm));
        } else {
            this.tableViewFilm.setItems(FXCollections.observableList(listByGenre));
        }
    }
    public void buttonNewFilm() throws IOException{
        App.setView("FXMLNewFilm");
    }

    //Load Data
    public void loadTableView() {
        TableColumn colFilmID = new TableColumn("ID");
        colFilmID.setCellValueFactory(new PropertyValueFactory("filmID"));
        colFilmID.setPrefWidth(100);

        TableColumn colFilmName = new TableColumn("Name");
        colFilmName.setCellValueFactory(new PropertyValueFactory("filmName"));
        colFilmName.setPrefWidth(150);

        TableColumn colDirector = new TableColumn("Director");
        colDirector.setCellValueFactory(new PropertyValueFactory("director"));
        colDirector.setPrefWidth(100);

        TableColumn colDuration = new TableColumn("Duration");
        colDuration.setCellValueFactory(new PropertyValueFactory("duration"));
        colDuration.setPrefWidth(80);

        TableColumn colStartDate = new TableColumn("Start Date");
        colStartDate.setCellValueFactory(new PropertyValueFactory("startDate"));
        colStartDate.setPrefWidth(100);

        TableColumn colEndDate = new TableColumn("End Date");
        colEndDate.setCellValueFactory(new PropertyValueFactory("endDate"));
        colEndDate.setPrefWidth(100);

        TableColumn colLimitAge = new TableColumn("Age");
        colLimitAge.setCellValueFactory(new PropertyValueFactory("limitAge"));
        colLimitAge.setPrefWidth(50);

//        TableColumn colViewFilm = new TableColumn("View");
//        colViewFilm.setCellValueFactory(new PropertyValueFactory("viewFilm"));
//        colViewFilm.setPrefWidth(50);

        TableColumn<Film, ImageView> colImageURL = new TableColumn("Image");
        colImageURL.setCellValueFactory((column) -> {
            ImageView imageView = new ImageView();
            Film p = column.getValue();
            File file = new File(p.getImageUrl());
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(120);
            imageView.setFitHeight(150);
            return new SimpleObjectProperty<>(imageView);
        });
        colImageURL.setPrefWidth(120);

//        TableColumn colDescription = new TableColumn("Description");
//        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
//        colDescription.setPrefWidth(200);

        TableColumn<Film, String> colGenre = new TableColumn("Genre");
        colGenre.setCellValueFactory((column) -> {
            Film p = column.getValue();
            Set<FilmGenre> setGenres = p.getListGenre();
            String genres = "";
            for (FilmGenre setGenre : setGenres) {
                genres += setGenre.getfGenreName() + "\n";
            }
            return new SimpleObjectProperty<>(genres);
        });
        colGenre.setPrefWidth(100);

        TableColumn<Film, String> colActors = new TableColumn("Actors");
        colActors.setCellValueFactory((column) -> {
            Film p = column.getValue();
            Set<Actors> setActors = p.getListActors();
            String actors = "";
            for (Actors setActor : setActors) {
                actors += setActor.getActorName() + "\n";
            }
            return new SimpleObjectProperty<>(actors);
        });
        colActors.setPrefWidth(100);

        TableColumn<Film, Button> colButtonEdit = new TableColumn<>("Edit");
        colButtonEdit.setCellValueFactory((o) -> {
            Film p = o.getValue();
            Button button = new Button("Edit");

            button.setOnAction((t) -> {
                try {
                    FXMLLoader fxmlLoader1 = new FXMLLoader(App.class.getResource("FXMLEditFilm.fxml"));
                    fxmlLoader1.setControllerFactory(new Callback<Class<?>, Object>() {
                        @Override
                        public Object call(Class<?> param) {
                            return new FXMLEditFilmController(p);
                        }

                    });
                    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLHome.fxml"));
                    scene.setRoot(fxmlLoader.load());
                    FXMLHomeController homeController = fxmlLoader.getController();
                    homeController.setCenter(fxmlLoader1.load());
                } catch (Exception ex) {
                    Logger.getLogger(FXMLFilmController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return new SimpleObjectProperty<>(button);
        });


        TableColumn<Film,Button> colNewSchedule = new TableColumn<>("Schedule"); 
        colNewSchedule.setCellValueFactory((o) -> {
            Film p = o.getValue();
            Button button =new Button("New Schedule");       
            button.setOnAction((t) -> {
                try {
                   FXMLLoader fxmlLoader = App.setView("FXMLNewSchedule");
                    FXMLNewScheduleController newScheduleControl = fxmlLoader.getController();
                    newScheduleControl.getFilm(p);
                } catch (Exception ex) {
                    Logger.getLogger(FXMLFilmController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return new SimpleObjectProperty<>(button);
        });
        
        TableColumn<Film,Button> colDelete = new TableColumn<>("Delete"); 
        colDelete.setCellValueFactory((o) -> {
            Film p = o.getValue(); 
            Button button =new Button("Delete");       
            button.setOnAction((t) -> {
                try {
                    
                    FilmDAO f = new FilmDAO();
                    ScheduleDAO sd = new ScheduleDAO();
                    List<Schedule> listSchedule = sd.checkStatus(p);
                    String info = "Are you sure? \n Delete ";
                    if(!listSchedule.isEmpty()){
                        info += listSchedule + " are active in Schedule";
                    }
                    Alert alert = AlertUtils.getAlert(info, Alert.AlertType.CONFIRMATION);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get().getText().equalsIgnoreCase("OK")) {
                        f.delete(p.getFilmID(), Film.class);
                        SessionUtil.getMapFilm().remove(p);
                        loadDataTableView();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(FXMLNewScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            return new SimpleObjectProperty<>(button);
        });
        
        TableColumn<Film,Integer> indexColumn = new TableColumn<>("Index");
        indexColumn.setCellFactory((o) -> new TableCell<Film,Integer>(){
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null) {
                    int rowIndex = this.getTableRow().getIndex();
                    if (rowIndex < getTableView().getItems().size()) {
                        setText(String.valueOf(rowIndex + 1));
                    } else {
                        setText("");
                    }
                } else {
                    setText("");
                }
            }
        
        });
        indexColumn.setPrefWidth(50);
        indexColumn.setStyle("-fx-font-weight: bold;");
        this.tableViewFilm.getColumns().addAll(indexColumn,colFilmID,colImageURL,colFilmName,colGenre,colDirector,colActors,colDuration,colStartDate,colEndDate,
                colLimitAge,colButtonEdit,colDelete,colNewSchedule);
        

        ObservableList<TableColumn<Film, ?>> columns = this.tableViewFilm.getColumns();
        for (TableColumn<Film, ?> column : columns) {
            column.setStyle("-fx-alignment: CENTER;");
        }
        this.tableViewFilm.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-style: solid;");
    }

    public void loadDataTableView() {
//        FilmDAO fd = new FilmDAO();
//        try {
//            listFilm = fd.searchByDate("endDate");
//            this.tableViewFilm.setItems(FXCollections.observableList(listFilm));
//        } catch (Exception ex) {
//            Logger.getLogger(FXMLFilmController.class.getName()).log(Level.SEVERE, null, ex);
//        }
          this.listFilm = SessionUtil.getMapFilm();
          this.tableViewFilm.setItems(FXCollections.observableList(listFilm));
    }

    public void loadDataCombobox() {
        FilmGenreDAO fg = new FilmGenreDAO();
        try {
            listGenre = fg.getAll("FilmGenre");
            this.comboGenres.setItems(FXCollections.observableList(listGenre));
        } catch (Exception ex) {
            AlertUtils.getAlert(ex.getMessage(), Alert.AlertType.ERROR).show();
        }
    }


}
