package com.group2.cineme.sem2;

import DAO.WorkSessionDAO;
import Utils.HibernateUtils;
import Utils.updateStatusScheduleForFuture;
import Utils.updateStatusScheduleForPass;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import org.hibernate.Session;

/**
 * JavaFX App
 */
public class App extends Application {

    static {
        Thread thread1 = new Thread(new updateStatusScheduleForFuture(0, 1));
        thread1.start();
        Thread thread2 = new Thread(new updateStatusScheduleForPass());
        thread2.start();

    }
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

    // Hàm này update endTime khi tắt ngang ứng dụng ( không bấm vào logout ) 
    @Override
    public void stop() throws Exception {
        super.stop();
        WorkSessionDAO workdao = new WorkSessionDAO();
        workdao.update();
    }
    


    public static void main(String[] args) {
        launch();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

}
