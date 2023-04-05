/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.WorkSessionDAO;

import DAO.FilmDAO;
import POJO.Film;
import Utils.SessionUtil;
import com.jfoenix.controls.JFXHamburger;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author BE BAU
 */
public class FXMLHomeController implements Initializable {

    @FXML
    private JFXHamburger hamburger;
    @FXML
    private VBox vbox;
    @FXML
    private Button buttonHome;

    @FXML
    private BorderPane home;

    @FXML
    AnchorPane anchorPane;
    
    
    Popup popup = new Popup();
    
    @FXML
    private Label labelAdmin1;
    
    @FXML
    private Label labelAdmin2;
   
    
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        loadDataImageView();
        loadDataPopup();
        this.labelAdmin1.setText(SessionUtil.getEmployee().getEmpName());
         this.labelAdmin2.setText(SessionUtil.getEmployee().getEmpName());
        loadInHome("FXMLFilm");
    }

    //Xu ly Button handler
    public void homeButtonHandler() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLHome.fxml"));
        App.scene.setRoot(fxmlLoader.load());

    }

    public void filmButtonHandler() throws IOException {
        App.setView("FXMLFilm");
    }

    // xử lý logout và cập nhật endTime
    public void logOut() throws IOException, Exception {
        WorkSessionDAO worddao = new WorkSessionDAO();
        worddao.update();
        App.setRoot("FXMLLogin");
        Stage stage = (Stage) App.scene.getWindow();
        stage.setFullScreen(false);
    }

    public void loadAdmin() throws IOException {
        App.setView("FXMLAdmin");
    }

    public void loadSigup() throws IOException {
        App.setView("FXMLSigup");
    }

   
  
    
    //Xu ly Button handler
  
   
   
    
    
    
    
    
    //Load Trang( de truyen vao App)
    public void setCenter(Parent fxml) throws IOException{
        this.home.setCenter(fxml);
    }
    
    //Su dung nay neu button nam tai trang home
    public void loadInHome(String fxml){
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml+".fxml"));
        try {
            setCenter(fxmlLoader.load());
        } catch (IOException ex) {
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void loadDataPopup(){
        
        popup.getContent().add(vbox);     
        this.hamburger.setOnMouseClicked((event) -> {
            if(popup.isShowing()){
                popup.hide();              
            }else{
                popup.show(hamburger,event.getScreenX()-720,event.getScreenY());
            }
        });

        loadInHome("FXMLNewSchedule");

    }

    public void loadDataImageView(){
        File fileHome = new File("src\\main\\resources\\images\\icon\\home.png");
        Image iconHome = new Image(fileHome.toURI().toString());
        ImageView imageViewHome = new ImageView(iconHome);
        imageViewHome.setFitWidth(16);
        imageViewHome.setFitHeight(16);
        this.buttonHome.setGraphic(imageViewHome);
    }


}
