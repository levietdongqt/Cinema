/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import static com.group2.cineme.sem2.App.scene;
import com.jfoenix.controls.JFXHamburger;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Button btnEM;

    @FXML
    private JFXHamburger hamburger;
    @FXML
    private VBox vbox;
    @FXML
    private Button buttonHome;

    @FXML
    private BorderPane home;

    @FXML
    private Button btnNewFilm;
//    private String FXMLLogin;
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDataImageView();
//        loadDataPopup();
    }  
    
    
    
   public void homeButtonHandler() throws IOException{
       FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLHome.fxml"));
       App.scene.setRoot(fxmlLoader.load());
   }
       
 
   public void filmButtonHandler() throws IOException{
       App.setView("FXMLAdmin");
   }
   public void logOut(ActionEvent event) throws IOException{
       App.setRoot("FXMLLogin");
   }
   
   
   
   
   
       
//    public void FilmManager(ActionEvent event) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLLogin.fxml"));
//        try {
//            home.setCenter(fxmlLoader.load());
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void EmployeeManager(ActionEvent event) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLAdmin.fxml"));
//        try {
//            home.setCenter(fxmlLoader.load());
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }


    
//    public void Logout(ActionEvent event) throws IOException {
//        System.out.println("123");
//       App.setRoot("FXMLLogin");
//         System.out.println("456");
//
//    }

    
    
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
        Popup popup = new Popup();
        popup.getContent().add(vbox);     
        this.hamburger.setOnMouseClicked((event) -> {
            if(popup.isShowing()){
                popup.hide();
            }else{
                popup.show(hamburger,event.getScreenX()-720,event.getScreenY());            
            }
        });
        loadInHome("Admin");
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
