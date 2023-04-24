/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.FilmDAO;
import POJO.Actors;
import POJO.Film;
import POJO.FilmGenre;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;

/**
 * FXML Controller class
 *
 * @author thuhuytran
 */
public class FXMLViewFilmDetailsController implements Initializable {

    @FXML
    private Text textName;
    @FXML
    private Label txtID;
    @FXML
    private Label txtDirector;
    @FXML
    private Label txtAge;
    @FXML
    private Label txtStartDate;
    @FXML
    private Label txtEndDate;

    @FXML
    private Label txtDescription;

    @FXML
    private ImageView imageFilm;
    @FXML
    private Button btnClose;
    @FXML
    private VBox VBoxGenres;

    @FXML
    private VBox VBoxActors;

    private Film film;

    public FXMLViewFilmDetailsController(Film film) {
        this.film = film;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();

    }

    public void loadData() {
        FilmDAO fd = new FilmDAO();
        this.textName.setText(film.getFilmName());
        this.txtID.setText(film.getFilmID());
        this.txtDirector.setText(film.getDirector());
        this.txtAge.setText(String.format("%s", film.getLimitAge()));
        this.txtStartDate.setText(film.getStartDate().toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        this.txtEndDate.setText(film.getEndDate().toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        this.txtDescription.setText(film.getDescription());

        String view = film.getImageUrl();
        String projectPath = System.getProperty("user.dir");
        if (!projectPath.endsWith("Cineme-sem2")) {
            projectPath = new File(projectPath).getParentFile().toString();
        }
        File f = new File(projectPath + "/" + view);
        Image image = new Image(f.toURI().toString());
        this.imageFilm.setImage(image);
        imageFilm.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        for (FilmGenre genre : fd.getFilmGenreByID(film.getFilmID())) {
            Label label = new Label(genre.getfGenreName());
            VBoxGenres.getChildren().add(label);

        }

        for (Actors actor : fd.getFilmActorsByID(film.getFilmID())) {
            Label label = new Label(actor.getActorName());
            VBoxActors.getChildren().add(label);
        }

    }

    public void setUpBtnColse() {
        Popup popup = (Popup) btnClose.getScene().getWindow();
        popup.hide();
    }

}
