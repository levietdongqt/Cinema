/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.RoomDAO;
import DAO.RoomSeatDetailDAO;
import DAO.SeatMapDAO;
import POJO.Room;
import POJO.RoomSeatDetail;
import POJO.RoomType;
import POJO.SeatMap;
import POJO.SeatType;
import Utils.AlertUtils;
import Utils.HibernateUtils;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import org.hibernate.Session;

public class FXMLSeatMapController implements Initializable {

    @FXML
    Label screenLabel;
    @FXML
    GridPane seatGrid;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Button btnNextStep;
    List<String> infoList = new ArrayList<>();
    String info = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setSeatGird();
        btnNextStep.setOnAction((t) -> {
            info ="Number of seats selected: "+ infoList.size() + "\n";
            Collections.sort(infoList);
            infoList.forEach(p -> {
                info = info.concat(p).concat("  ");
            });
            Alert alert = AlertUtils.getAlert(info, Alert.AlertType.INFORMATION);
            alert.show();
        });
    }

    public void setSeatGird() {

        screenLabel.setStyle("-fx-background-color: #99CCFF;");
        seatGrid.setPadding(new Insets(20));

        List<String> seatList = new ArrayList<>();
        Session ses = HibernateUtils.getFACTORY().openSession();
        try {
            ses.getTransaction().begin();
            Room room = ses.get(Room.class, "P01");
            room.getRoomType().getRoomSeatDetailList().forEach(p -> seatList.add(p.getSeatMap().getsMapID()));
            Collections.sort(seatList);
            ses.getTransaction().commit();
            ses.close();
        } catch (Exception ex) {
            Logger.getLogger(FXMLSeatMapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        char[] row = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N'};
        int rowIndex = 0;
        for (String item : seatList) {
            Button bt = new Button(item);
            bt.setPrefSize(50, 30);
            bt.setStyle("-fx-background-color: #9900cc; -fx-text-fill: white;");
            int columIndex = Integer.parseInt(item.substring(1)) - 1;
            if (item.charAt(0) != row[rowIndex]) {
                rowIndex++;
            }
            seatGrid.add(bt, columIndex, rowIndex);

            bt.setOnAction((t) -> {
                onActionButton(item, bt);

            });
        }
        int columnWidth = 55;
        int rowHight = 35;
        ColumnConstraints colConstraints = new ColumnConstraints(columnWidth);
        RowConstraints rowContraints = new RowConstraints(rowHight);
        int columNum = seatGrid.getColumnCount();
        int rowNum = seatGrid.getRowCount();
        for (int i = 0; i < columNum; i++) {
            seatGrid.getColumnConstraints().add(i, colConstraints);
        }
        for (int i = 0; i < rowNum; i++) {
            seatGrid.getRowConstraints().add(i, rowContraints);
        }

        // anchorPane.setMinWidth(seatGrid.getColumnCount()*columnWidth);
        anchorPane.setMinHeight(columNum * columnWidth);
    }

    public void onActionButton(String item, Button bt) {
        String color = bt.getStyle().toLowerCase();
        if (color.contains("#9900cc")) {    //Khi người dùng chọn ghế
            bt.setStyle("-fx-background-color: #ff00ff; -fx-text-fill: white;");
            infoList.add(item);

        } else {          ////Khi người dùng bỏ chọn ghế
            bt.setStyle("-fx-background-color: #9900cc; -fx-text-fill: white;");
            infoList.remove(item);
        }
    }

}
