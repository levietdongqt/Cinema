/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.WorkSessionDAO;

import Utils.SessionUtil;
import com.jfoenix.controls.JFXHamburger;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    @FXML
    private Label timeLabel;
    @FXML
    ComboBox<String> report;
    @FXML
    private Button btnBooking;

    @FXML
    private Button btnEmp;

    @FXML
    private Button btnFilm;

    @FXML
    private Button btnSche;
    @FXML
    private Button btnBill;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        loadDataImageView();
        loadDataPopup();
        this.labelAdmin1.setText(SessionUtil.getEmployee().getEmpName());
        this.labelAdmin2.setText(SessionUtil.getEmployee().getEmpName());
        if (SessionUtil.getEmployee().getPosition().equalsIgnoreCase("manager")) {
            loadInHome("FXMLFilm");
        } else {
            loadInHome("FXMLShowSchedule");
            btnBooking.setDisable(true);
            btnFilm.setDisable(true);
            btnSche.setDisable(true);
        }
        loadTimeClock();
        setUpReport();
    }

    //Xu ly Button handler
    @FXML
    public void homeButtonHandler() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLHome.fxml"));
        App.scene.setRoot(fxmlLoader.load());

    }

    @FXML
    public void DashboardButtonHandler() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLHome.fxml"));
        App.scene.setRoot(fxmlLoader.load());
    }

    @FXML
    public void filmButtonHandler() throws IOException {
        App.setView("FXMLFilm");
    }
    @FXML
    public void billButtonHandler() throws IOException {
        App.setView("FXMLBillManager");
    }
    

    // xử lý logout và cập nhật endTime
    @FXML
    public void logOut() throws IOException, Exception {

        WorkSessionDAO worddao = new WorkSessionDAO();
        worddao.update();

        App.setRoot("FXMLLogin");
        Stage stage = (Stage) App.scene.getWindow();
        stage.setFullScreen(false);
        stage.setMaximized(false);

    }

    @FXML
    public void loadFXMLReport() throws IOException {
        String value = report.getValue();
        if(value != null){
        if (value.equalsIgnoreCase("Employee")) {
            //Load trang report Employee
        }
        if (value.equalsIgnoreCase("Film")) {
            newScene("FXMLFilmReport");
        }
        if (value.equalsIgnoreCase("Food")) {
            newScene("FXMLFoodReport");
        }                 
        }
    }

    private void newScene(String fileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fileName + ".fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
        home.setDisable(true);
        popup.hide();
        stage.setOnHiding((t) -> {
            home.setDisable(false);
            report.setValue(null);
        });
    }

    public void setUpReport() {
        ObservableList<String> list = report.getItems();
        list.add("Employee");
        list.add("Film");
        list.add("Food");
        report.setItems(list);
    }

    public void loadAdmin() throws IOException {
        App.setView("FXMLAdmin");
    }

    public void loadSigup() throws IOException {
        App.setView("FXMLSigup");
    }

    public void loadNewSchedule() throws IOException {
        App.setView("FXMLNewSchedule");
    }

    public void loadShowSchedule() throws IOException {
        App.setView("FXMLShowSchedule");
    }

    //Xu ly Button handler
    //Load Trang( de truyen vao App)
    public void setCenter(Parent fxml) throws IOException {
        this.home.setCenter(fxml);
    }

    //Su dung nay neu button nam tai trang home
    public void loadInHome(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        try {
            setCenter(fxmlLoader.load());
        } catch (IOException ex) {
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadDataPopup() {

        popup.getContent().add(vbox);
        this.hamburger.setOnMouseClicked((event) -> {
            if (popup.isShowing()) {
                popup.hide();
            } else {
                popup.show(hamburger, hamburger.getLayoutX() - 620, hamburger.getLayoutY() + 35);
            }
        });
    }

//    public void loadDataImageView(){
//        File fileHome = new File("src\\main\\resources\\images\\icon\\home.png");
//        Image iconHome = new Image(fileHome.toURI().toString());
//        ImageView imageViewHome = new ImageView(iconHome);
//        imageViewHome.setFitWidth(16);
//        imageViewHome.setFitHeight(16);
//        this.buttonHome.setGraphic(imageViewHome);
//    }
    public void loadTimeClock() {
        Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalTime localTime = LocalTime.now();
            this.timeLabel.setText(localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        })
        );
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();

    }
}
