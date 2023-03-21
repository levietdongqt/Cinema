/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.ActorsDAO;
import DAO.FilmGenreDAO;
import POJO.Actors;
import POJO.FilmGenre;
import Utils.AlertUtils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author thuhuytran
 */
public class FXMLNewFilmController implements Initializable {
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextField txtDuration;
    
    @FXML
    private TextField txtGender;
    @FXML
    private TextField txtActor;
    @FXML
    private DatePicker txtStart;
    @FXML
    private DatePicker txtEnd;
    @FXML
    private TextField txtImage;
    
    
    @FXML
    private ChoiceBox<Integer> limitAge;
//    @FXML
//    private ComboBox<FilmGenre> gender;
    @FXML
    private ListView<FilmGenre> listViewGender;
    @FXML
    private ListView<Actors> listActors;
    
    @FXML
    private ImageView imageViewFilm;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        List<Integer> limitAgeList = List.of(13, 16, 18);
        limitAge.setItems(FXCollections.observableList(limitAgeList));
        
        FilmGenreDAO fgd = new FilmGenreDAO();
        ActorsDAO ad = new ActorsDAO();
        List<FilmGenre> listGenre;
        List<Actors> listActor;
        try {
            listGenre = fgd.getAll("FilmGenre");
            listActor = ad.getAll("Actors");
            listGenre.sort((o1, o2) -> o1.getfGenreName().compareTo(o2.getfGenreName()));
            listActor.sort((o1, o2) -> o1.getActorName().compareTo(o2.getActorName()));
            listViewGender.setItems(FXCollections.observableList(listGenre));
            listViewGender.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            listActors.setItems(FXCollections.observableList(listActor));
            listActors.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (Exception ex) {
            Logger.getLogger(FXMLNewFilmController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.txtGender.textProperty().addListener((observable) -> {
        });
        
    }    
    
    
    public void uploadImageHandler(ActionEvent event){
        File f = null;
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Chon anh");       
        filechooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png","*.jpg", "*.jpeg"));       
        File selectedFile = filechooser.showOpenDialog(null);
        if(selectedFile!=null){
            Path copied = Paths.get(selectedFile.getPath());
            String s = copied.getFileName().toString();          
            f = new File("src\\main\\resources\\images\\" + s);
            Path target = f.toPath();
            try {
                Files.copy(copied, target, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                AlertUtils.getAlert(ex.getMessage(), Alert.AlertType.ERROR);
            }
        }
        if(f!=null){
            this.txtImage.setText(f.getPath());
            Image imageFilm = new Image(f.toURI().toString());
            imageViewFilm.setImage(imageFilm);
        }else{
            this.txtImage.setText("");
        }
        
    }
    
    public void saveButtonHandler(ActionEvent event) throws IOException{
//        FXMLLoader fxmlLoader1 = new FXMLLoader(App.class.getResource("FXMLSeatMap.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLHome.fxml"));
//        App.scene.setRoot(fxmlLoader.load());
//        FXMLHomeController home11 = fxmlLoader.getController();
//        
//             
//         home11.hienThi(fxmlLoader1.load());
        App.setView("FXMLSeatMap");

       
        
    }
    
    public void resetButtonHandler(ActionEvent event){
        this.txtID.clear();
        this.txtName.clear();
        this.txtDuration.clear();
        this.txtImage.clear();
        this.txtDescription.clear();
        this.txtStart.getEditor().clear();
        this.txtEnd.getEditor().clear();
        this.imageViewFilm.setImage(null);
        this.listViewGender.getSelectionModel().clearSelection();
    }
    
}
