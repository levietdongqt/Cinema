/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.*;
import DAO.GenericDAO;
import POJO.Employee;
import POJO.Film;
import POJO.WorkSession;
import Utils.AlertUtils;
import Utils.HibernateUtils;
import Utils.SessionUtil;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;

/**
 * FXML Controller class
 *
 * @author BE BAU
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private Label cgv;

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField pass;

    @FXML
    private TextField user;

    @FXML
    private Label welcom;

    static {
        loadData();
    }

    public void close() {
        System.exit(0);
    }

    // login bằng phím enter 
    public void login(ActionEvent event) throws Exception {
        WorkSession work = new WorkSession();
        EmployeeDAO dao = new EmployeeDAO();
        pass.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    login(null);
                    boolean log = dao.checkaccount(user.getText().trim(), pass.getText().trim());
                    Employee employee = SessionUtil.getEmployee();
                    work.setEmployee(employee);
                    work.setStartTime(LocalDateTime.now());
//        work.setEndTime(LocalDateTime.of(2010, 10, 10, 0, 0, 0));
                    if (log) {
                        WorkSessionDAO workdao = new WorkSessionDAO();
                        workdao.add(work);
                        App.setRoot("FXMLHome");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    // login bằng button
    public void btnLogin(ActionEvent event) throws Exception {
        WorkSession work = new WorkSession();
        EmployeeDAO dao = new EmployeeDAO();
        try {
            login(null);
            boolean log = dao.checkaccount(user.getText().trim(), pass.getText().trim());
            Employee employee = SessionUtil.getEmployee();
            work.setEmployee(employee);
            work.setStartTime(LocalDateTime.now());
//        work.setEndTime(LocalDateTime.of(2010, 10, 10, 0, 0, 0));
            if (log) {
                WorkSessionDAO workdao = new WorkSessionDAO();
                workdao.add(work);
                App.setRoot("FXMLHome");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set vùng sáng mặc định cho 2 chữ khi vừa mới mở app lên 
        DropShadow original = new DropShadow(20, Color.valueOf("blue"));
        welcom.setEffect(original);
        cgv.setEffect(original);

        //set viền sáng vàng cho chữ cinemas
        welcom.setOnMouseEntered((MouseEvent event)
                -> {
            DropShadow shadow = new DropShadow(50, Color.valueOf("#F8FF1C"));

//            welcom.setStyle("-fx-text-fill:#ff2b00");
            welcom.setEffect(shadow);
            cgv.setEffect(shadow);

        });

        welcom.setOnMouseExited((MouseEvent event)
                -> {
            DropShadow shadow = new DropShadow(20, Color.valueOf("#F8FF1C"));
//            welcom.setStyle("-fx-text-fill:#ff2b00");
            welcom.setEffect(shadow);
            cgv.setEffect(shadow);

        });

        //set viền sáng vàng cho chữ cgv 
        cgv.setOnMouseEntered((MouseEvent event)
                -> {
            DropShadow shadow = new DropShadow(50, Color.valueOf("#F8FF1C"));

//            welcom.setStyle("-fx-text-fill:#ff2b00");
            welcom.setEffect(shadow);
            cgv.setEffect(shadow);

        });

        cgv.setOnMouseExited((MouseEvent event)
                -> {
            DropShadow shadow = new DropShadow(20, Color.valueOf("#F8FF1C"));
//            welcom.setStyle("-fx-text-fill:#ff2b00");
            welcom.setEffect(shadow);
            cgv.setEffect(shadow);

        });

        String jarFile;
        try {
            jarFile = FXMLLoginController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            String directoryPath = "C:\\saveImage";           
            File directory = new File(directoryPath);
            
            if (!directory.exists()) {
                directory.mkdir();
            }else{
                directory.delete();
                directory.mkdir();
            }
// Lưu hình ảnh vào thư mục mới tạo
            String dirToExtract = "images";
            AlertUtils.extract(jarFile, directoryPath, dirToExtract);
        } catch (URISyntaxException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void loadData() {
        FilmDAO f = new FilmDAO();
        f.updateByDate("endDate");
        List<Film> films;
        try {
            films = f.searchByDate("endDate");
            for (Film film : films) {
                film.setListGenre(new HashSet<>(f.getFilmGenreByID(film.getFilmID())));
                film.setListActors(new HashSet<>(f.getFilmActorsByID(film.getFilmID())));
            }
            SessionUtil.setMapFilm(films);
        } catch (Exception ex) {
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
