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
    List<RoomSeatDetail> infoList = new ArrayList<>();
    String info = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setSeatGird();
        btnNextStep.setOnAction((t) -> {
            info = "Number of seats selected: " + infoList.size() + "\n";
            Collections.sort(infoList, (o1, o2) -> o1.getSeatMap().getsMapID().compareTo(o2.getSeatMap().getsMapID()));
            infoList.forEach(p -> {
                String seatName = p.getSeatMap().getsMapID();
                info = info + seatName + "( " + p.getSeatType().getSeatPrice() + ")" + " ";
            });
            Alert alert = AlertUtils.getAlert(info, Alert.AlertType.INFORMATION);
            alert.show();
        });
    }

    public void setSeatGird() {
        screenLabel.setStyle("-fx-background-color: #99CCFF;");
        seatGrid.setPadding(new Insets(20));

        List<RoomSeatDetail> seatList = new ArrayList<>();
        Session ses = HibernateUtils.getFACTORY().openSession();
        try {
            ses.getTransaction().begin();
            Room room = ses.get(Room.class, "R01");
            room.getRoomType().getRoomSeatDetailList().forEach(p -> seatList.add(p));
            Collections.sort(seatList, (o1, o2) -> o1.getSeatMap().getsMapID().compareTo(o2.getSeatMap().getsMapID()));
            ses.getTransaction().commit();
            ses.close();

        } catch (Exception ex) {
            Logger.getLogger(FXMLSeatMapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        char[] row = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N'};
        int rowIndex = 0;
        for (RoomSeatDetail item : seatList) {
            String seatName = item.getSeatMap().getsMapID();
            String seatType = item.getSeatType().getTypeGroup();
            Button bt = new Button(seatName);
            bt.setPrefSize(60, 40);
            int columIndex = Integer.parseInt(seatName.substring(1)) - 1;
            if (seatName.charAt(0) != row[rowIndex]) {
                rowIndex++;
            }
            seatGrid.add(bt, columIndex, rowIndex);
            if (seatType.equalsIgnoreCase("Normal")) {
                bt.setStyle("-fx-background-color: #9900cc; -fx-text-fill: white;");
                bt.setOnAction((t) -> {
                    onActionButton(item,bt, "#9900cc","#00CC00");

                });
            }
            if (seatType.equalsIgnoreCase("VIP")) {
                bt.setStyle("-fx-background-color: #ff3333; -fx-text-fill: white;");
                bt.setOnAction((t) -> {
                    onActionButton(item,bt, "#ff3333","#00CC00");

                });
            }
        }
        int columnWidth = 65;
        int rowHight = 45;
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

    public void onActionButton(RoomSeatDetail item,Button bt, String currentColor,String futureColor) {
        String color = bt.getStyle().toLowerCase();
        if (color.contains(currentColor)) {    //Khi người dùng chọn ghế
            bt.setStyle("-fx-background-color:" + futureColor + "; -fx-text-fill: white;");
            infoList.add(item);
            return;

        }
        ////Khi người dùng bỏ chọn ghế
        bt.setStyle("-fx-background-color:" + currentColor + "; -fx-text-fill: white;");
        infoList.remove(item);

    }

}
