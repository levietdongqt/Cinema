/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import com.jfoenix.controls.JFXHamburger;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

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
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File fileHome = new File("C:\\my-code\\My Code\\Cinema-Project\\Cinema\\Cineme-Sem2\\src\\main\\resources\\images\\home.png");
        Image iconHome = new Image(fileHome.toURI().toString());
        ImageView imageViewHome = new ImageView(iconHome);
        imageViewHome.setFitWidth(16);
        imageViewHome.setFitHeight(16);
        buttonHome.setGraphic(imageViewHome);
        
        

        
        Popup popup = new Popup();
        popup.getContent().add(vbox);     
        hamburger.setOnMouseClicked((event) -> {
            if(popup.isShowing()){
                popup.hide();
            }else{
                System.out.println(event.getScreenX());
                popup.show(hamburger,event.getSceneX()-500,event.getScreenY() );
            }
        });
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLNewFilm.fxml"));
        try {
            home.setCenter(fxmlLoader.load());
        } catch (IOException ex) {
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
