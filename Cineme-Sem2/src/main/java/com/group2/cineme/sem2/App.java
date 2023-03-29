package com.group2.cineme.sem2;

import Utils.HibernateUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.BorderPane;
import org.hibernate.Session;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Session ses = HibernateUtils.getFACTORY().openSession();
        ses.clear();
        ses.close();    
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
    static FXMLLoader setView(String fxml) throws IOException {
        FXMLLoader fxmlLoader1 = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLHome.fxml"));
        scene.setRoot(fxmlLoader.load());
        FXMLHomeController homeController = fxmlLoader.getController();
        homeController.setCenter(fxmlLoader1.load());
        return fxmlLoader1;

    }

    public static void main(String[] args) {
        launch();
    }

}
