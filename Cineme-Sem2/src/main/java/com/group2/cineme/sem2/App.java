package com.group2.cineme.sem2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.BorderPane;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("FXMLHome"));
        stage.setScene(scene);
        stage.show();
    }
  

    static void setFull(String fxml) throws IOException {
        Stage stage = (Stage) scene.getWindow(); // Lấy đối tượng Stage hiện tại
        Scene newScene = new Scene(loadFXML(fxml));
        stage.setScene(newScene); // Cài đặt Scene mới vào Stage hiện tại
        stage.setFullScreen(true);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    //Ham nay la ham de setView vao Home
    static void setView(String fxml) throws IOException{
        System.out.println("hello");
        FXMLLoader fxmlLoader1 = new FXMLLoader(App.class.getResource(fxml+".fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLHome.fxml"));
        scene.setRoot(fxmlLoader.load());
        FXMLHomeController home11 = fxmlLoader.getController();
         home11.hienThi(fxmlLoader1.load());
        
    }
   
    public static void main(String[] args) {
        launch();
    }
    
}
