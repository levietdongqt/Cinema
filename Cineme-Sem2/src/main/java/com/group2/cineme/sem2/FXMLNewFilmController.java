/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.ActorsDAO;
import DAO.FilmGenreDAO;
import POJO.Actors;
import POJO.Film;
import POJO.FilmGenre;
import Utils.AlertUtils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;


/**
 * FXML Controller class
 *
 * @author thuhuytran
 */
public class FXMLNewFilmController implements Initializable {
    Set<FilmGenre> setFilmGenre = new HashSet<>();
    Set<Actors> setActors = new HashSet<>();
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextField txtDuration;   
    @FXML
    private TextField keywordGender;
    @FXML
    private TextField keywordActor;
    @FXML
    private DatePicker txtStart;
    @FXML
    private DatePicker txtEnd;
    @FXML
    private TextField txtImage;
    @FXML
    private ChoiceBox<Integer> limitAge;
    @FXML
    private ListView<FilmGenre> listViewGender;
    @FXML
    private ListView<Actors> listActors;
    
    @FXML
    private ImageView imageViewFilm;
    
    @FXML
    private HBox hboxShowInfor;
    
    @FXML
    private VBox vboxShowInfor;
    
    @FXML
    private Label labelName;
    @FXML
    private Label labelAge;
    @FXML
    private Label labelStartDate;
    @FXML
    private Label labelEndDate;
    @FXML
    private Label labelDuration;
    @FXML
    private Label labelImageURL;
    @FXML
    private Label labelGender;
    @FXML
    private Label labelActor;
    
    @FXML
    private Label errorFilmName;
    @FXML
    private Label errorFilmDes;
    @FXML
    private Label errorStart;
    @FXML
    private Label errorEnd;
    @FXML
    private Label errorImage;
    @FXML
    private Label errorDuration;
    
    
    @FXML
    private VBox vBoxActors;
    private TextField txtActorsName;
    private Label errActorsName;
    private DatePicker txtBirthDay;
    private Label errBirthDay;
    private TextField txtHomeTown;
    private Label errHomeTown;
    private Button buttonSave;
    private Button buttonClear;
    
    private Film film;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set du lieu cho Label
        loadNullLabel();
        loadDataLabel();
        //Set du lieu cho LimitAge
        loadDataOrSetDefault();
        //Set du lieu cho ListView Gender Actor
        loadDataActors("");
        loadDataGender("");
        
        //Kiem tra su thay doi cua theo TextField cua Gender va Actor
        checkKeyword();
        
        //Actors 
        createVBoxActors();
        validateActors();
        buttonSaveHandlerActor(validateActors());
        buttonResetHandlerActor();
        film = validateFilm();
        
        
        
    }    
    
    //Khu vuc xu ly su kien
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
                AlertUtils.getAlert(ex.getMessage(), Alert.AlertType.ERROR).show();
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
        if((errorFilmName.isVisible()==true) || (errorImage.isVisible()==true) || (errorDuration.isVisible()==true) || (errorStart.isVisible()==true) || (errorEnd.isVisible()==true)){
            System.out.println("false");  
        }else{
            System.out.println(film.getFilmID());
        }
 
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
        loadNullLabel();
        setFilmGenre.clear();
        setActors.clear();
        this.errorFilmName.setText("");
        this.errorDuration.setText("");
        this.errorEnd.setText("");
        this.errorStart.setText("");
        this.errorImage.setText("");
        this.errorFilmDes.setText("");
    }
    public void showInformationButtonHandler(){
       if(this.hboxShowInfor.isVisible()==false && this.vboxShowInfor.isVisible()==false){
           this.hboxShowInfor.setVisible(true);
           this.vboxShowInfor.setVisible(true);
       }else{
           this.hboxShowInfor.setVisible(false);
           this.vboxShowInfor.setVisible(false);
       }    
    }
   
    public void addActorButtonHandler(){
        if(this.vBoxActors.isVisible()==false){
            this.vBoxActors.setVisible(true);
        }else{
            this.vBoxActors.setVisible(false);
        }
    }
     public void buttonSaveHandlerActor(Actors actor){
        buttonSave.setOnAction((event) -> {
           if(errActorsName.isVisible()==true || errBirthDay.isVisible()==true){
               AlertUtils.getAlert("Actor's Name or Actor's BirthDay wrong!!!", Alert.AlertType.WARNING).show();
           }else{
               ActorsDAO ad = new ActorsDAO();
               try {                 
                   ad.add(actor);
                   AlertUtils.getAlert(ad.getMessAdd(), Alert.AlertType.INFORMATION).show();
                   loadDataActors("");
                   clearDataInActor();
               } catch (Exception ex) {
                   AlertUtils.getAlert(ad.getMessAdd(), Alert.AlertType.WARNING).show();
               }   
           }
        });
    }
    public void buttonResetHandlerActor(){
        buttonClear.setOnAction((event) -> {
            clearDataInActor();
            
        });
    }
    
    
    //Khu vuc load data
    public void loadDataGender(String kw){
        List<FilmGenre> listGenre;
        listGenre = FilmGenreDAO.getGenreAllOrByName(kw);          
        listGenre.sort((o1, o2) -> o1.getfGenreName().compareTo(o2.getfGenreName()));
            
        this.listViewGender.setItems(FXCollections.observableList(listGenre));
        this.listViewGender.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//Ham lay nhieu gia tri trong ListView
            
    }
    public void loadDataActors(String kw){ 
        List<Actors> listActor;         
        listActor = ActorsDAO.getActorsAllOrByName(kw);
            
        listActor.sort((o1, o2) -> o1.getActorName().compareTo(o2.getActorName()));
                      
        this.listActors.setItems(FXCollections.observableList(listActor));
        this.listActors.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    
    public void loadDataOrSetDefault(){
        List<Integer> limitAgeList = List.of(13, 16, 18);
        this.limitAge.setItems(FXCollections.observableList(limitAgeList));
        
        this.txtStart.setEditable(false);
        this.txtStart.setValue(LocalDate.now());
        
        
        this.txtEnd.setEditable(false);
        this.txtEnd.setValue(LocalDate.now().plusDays(10));
        
        this.txtImage.setEditable(false);

        this.errorFilmName.setText("");
        this.errorDuration.setText("");
        this.errorEnd.setText("");
        this.errorStart.setText("");
        this.errorImage.setText("");
        this.errorFilmDes.setText("");
        
    }
    public void loadDataLabel(){
        this.txtName.textProperty().addListener((observable) -> {
            this.labelName.setText(this.txtName.getText());
            labelName.setWrapText(true);
        });
        this.limitAge.setOnAction((event) -> {
            this.labelAge.setText(this.limitAge.getValue().toString());
        });
        this.txtStart.setOnAction((event) -> {
            this.labelStartDate.setText(this.txtStart.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });
        this.txtEnd.setOnAction((event) -> {
            this.labelEndDate.setText(this.txtEnd.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });
        this.txtDuration.textProperty().addListener((observable) -> {
            this.labelDuration.setText(this.txtDuration.getText());
        });
        this.txtImage.textProperty().addListener((observable) -> {
            this.labelImageURL.setText(this.txtImage.getText());
        });
        
        this.listViewGender.setOnMouseClicked((event) -> {
            ObservableList<FilmGenre> selectedItems = listViewGender.getSelectionModel().getSelectedItems();
            
            setFilmGenre.addAll(selectedItems);
            String textGenre = "";
            for (FilmGenre selectedItem : setFilmGenre) {
                textGenre +=selectedItem.toString()+"|";
            }
            this.labelGender.setText(textGenre);
            labelGender.setWrapText(true);
        });
        this.listActors.setOnMouseClicked((event) -> {
            ObservableList<Actors> selectedItems = listActors.getSelectionModel().getSelectedItems();
            
            setActors.addAll(selectedItems);
            String textActors = "";
            for (Actors selectedItem : setActors) {
                textActors +=selectedItem.toString()+"|";
            }
            this.labelActor.setText(textActors);
            labelActor.setWrapText(true);
        });

    }
    public void loadNullLabel(){
        this.labelName.setText("");
        this.labelAge.setText("");
        this.labelDuration.setText("");
        this.labelGender.setText("");
        this.labelStartDate.setText("");
        this.labelEndDate.setText("");
        this.labelImageURL.setText("");
        this.labelActor.setText("");  
    }
    public void clearDataInActor(){
        txtActorsName.clear();
        errActorsName.setText("");
        txtBirthDay.getEditor().clear();
        errBirthDay.setText("");
        txtHomeTown.clear();
        errBirthDay.setText("");
    }
    
    //Khu vuc tao khung cho Trang(Bao gom ca nhung action cho khung duoc tao)
    public void createVBoxActors(){
        //Tao khung
        HBox hBoxName = new HBox();
        VBox vBoxName = new VBox();
        Label labelActorsName = new Label("Actor Name:");
        txtActorsName = new TextField();
        errActorsName = new Label("");
        errActorsName.setWrapText(true);
        vBoxName.getChildren().addAll(txtActorsName,errActorsName);
        hBoxName.getChildren().addAll(labelActorsName,vBoxName);
        
        HBox hBoxBDate = new HBox();
        VBox vBoxDate = new VBox();
        Label labelBDate = new Label("BOD:");
        txtBirthDay = new DatePicker();
        txtBirthDay.setEditable(false);
        errBirthDay = new Label("");
        errBirthDay.setWrapText(true);
        vBoxDate.getChildren().addAll(txtBirthDay,errBirthDay);
        hBoxBDate.getChildren().addAll(labelBDate,vBoxDate);
        
        HBox hBoxHomeTown = new HBox();
        VBox vBoxHomeTown = new VBox();
        Label labelHomeTown = new Label("Home Town:");
        txtHomeTown = new TextField();
        errHomeTown = new Label("");
        errHomeTown.setWrapText(true);
        vBoxHomeTown.getChildren().addAll(txtHomeTown,errHomeTown);
        hBoxHomeTown.getChildren().addAll(labelHomeTown,vBoxHomeTown);
            
        HBox buttonActors = new HBox();
        buttonSave = new Button("Save");
        buttonClear = new Button("Clear");
        buttonActors.getChildren().addAll(buttonSave,buttonClear);
        
        vBoxActors.getChildren().addAll(hBoxName,hBoxBDate,hBoxHomeTown,buttonActors);     
    }
    
    
    //Check Loi or check keyWord
    public Actors validateActors(){
        Actors actor= new Actors();
        txtActorsName.textProperty().addListener((observable) -> {
            boolean errorName = false;
        try {
            actor.setActorName(txtActorsName.getText()); 
            errorName=true;
        } catch (Exception ex) {
            errActorsName.setVisible(true);
            errActorsName.setText(ex.getMessage());
        }finally{
            if(errorName == true){
                errActorsName.setVisible(false);
            }
        }   
        });      
        txtBirthDay.setOnAction((event) -> {
            boolean errorBD = false;
            try {
                actor.setBirthDate(java.sql.Date.valueOf(txtBirthDay.getValue()));
                errorBD=true;
            } catch(Exception ex){
                errBirthDay.setVisible(true);
                errBirthDay.setText(ex.getMessage());            
            }finally{
                if(errorBD==true){
                    errBirthDay.setVisible(false);
                }
            }
        });
        txtHomeTown.textProperty().addListener((observable) -> {
            actor.setHomeTown(txtHomeTown.getText().trim());
        });
        
       return actor;
    }
   
    
    
     public void checkKeyword(){
        this.keywordGender.textProperty().addListener((o) -> {
            this.loadDataGender(this.keywordGender.getText());
        });
        this.keywordActor.textProperty().addListener((o) -> {
            this.loadDataActors(this.keywordActor.getText());
        });
    }
    
     public Film validateFilm(){
         Film film = new Film();
         film.setStartDate(java.sql.Date.valueOf(txtStart.getValue()));
         film.setEndDate(java.sql.Date.valueOf(txtEnd.getValue()));
        this.txtName.textProperty().addListener((observable) -> {
             boolean errorName = false;     
        try {
            film.setFilmName(txtName.getText().trim()); 
            errorName=true;
            String[] words = this.txtName.getText().split(" ");
            String result = "P";
            for (String word : words) {
                result+=word.charAt(0);
            }
            result+=(LocalDate.now().getMonth().toString()+LocalDate.now().getDayOfMonth()+LocalTime.now().getMinute());
            this.txtID.setText(result);
        } catch (Exception ex) {
            errorFilmName.setVisible(true);
            errorFilmName.setText(ex.getMessage());
        }finally{
            if(errorName == true){
                errorFilmName.setVisible(false);
            }
        }   
        });
        
        this.txtDescription.textProperty().addListener((observable) -> {
            film.setDescription(txtDescription.getText().trim());
        });
        
        this.limitAge.setOnAction((event) -> {
            film.setLimitAge(limitAge.getValue());
        });
        
        txtStart.setOnAction((event) -> {
            boolean errorBD = false;
            try {
                film.setStartDate(java.sql.Date.valueOf(txtStart.getValue()));
                errorBD=true;
            } catch(Error | NullPointerException ex){
                errorStart.setVisible(true);
                errorStart.setText(ex.getMessage());            
            }
            finally{
                if(errorBD==true){
                    errorStart.setVisible(false);
                }
            }
        });
        txtEnd.setOnAction((event) -> {
            boolean errorBD = false;
            try {
                film.setEndDate(java.sql.Date.valueOf(txtEnd.getValue()));
                errorBD=true;
            } catch(Error ex){
                errorEnd.setVisible(true);
                errorEnd.setText(ex.getMessage());            
            }catch(NullPointerException ex){
                errorEnd.setVisible(true);
                errorEnd.setText("Must be set startDate");           
            }
            finally{
                if(errorBD==true){
                    errorEnd.setVisible(false);
                }
            }
        });
        txtImage.textProperty().addListener((observable) -> {
            boolean errorName = false;
        try {
            film.setImageUrl(txtImage.getText()); 
            errorName=true;
        } catch (Exception ex) {
            errorImage.setVisible(true);
            errorImage.setText(ex.getMessage());
        }finally{
            if(errorName == true){
                errorImage.setVisible(false);
            }
        }   
        });   
        txtDuration.textProperty().addListener((observable) -> {
                boolean errorName = false;
            try {
                film.setDuration(Integer.parseInt(this.txtDuration.getText().trim()));
                if(Integer.parseInt(this.txtDuration.getText().trim())<85 ||Integer.parseInt(this.txtDuration.getText().trim())>210){
                    throw new Error("Duration must be 85<=duration<=210");
                }
                errorName = true;
            } catch (NumberFormatException e) {
                errorDuration.setVisible(true);
                errorDuration.setText("Duration must be Integer");
            }catch(Error ex){
                errorDuration.setVisible(true);
                errorDuration.setText(ex.getMessage());
            }finally{
                if(errorName == true){
                errorDuration.setVisible(false);
            }
            }
        });
        txtID.textProperty().addListener((observable) -> {
            film.setFilmID(this.txtID.getText());
        });
        return film;
        
    }
}
