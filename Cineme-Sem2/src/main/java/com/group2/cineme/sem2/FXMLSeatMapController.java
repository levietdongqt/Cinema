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
import POJO.Ticket;
import Utils.AlertUtils;
import Utils.HibernateUtils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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
   private char  []  row = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N'};
    List<RoomSeatDetail> infoList = new ArrayList<>();
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setSeatGird();
        btnNextStepHandler();
    }
    private  void btnNextStepHandler(){
         btnNextStep.setOnAction((t) -> {
             StringBuilder info = new StringBuilder();
            info.append("Number of seats selected: ").append(infoList.size()).append("\n");
            sortRSDList(infoList);

            infoList.forEach(p -> {
                String seatName = p.getSeatMap().getsMapID();
                info.append(seatName).append("( ").append(p.getSeatType().getSeatPrice()).append(")\n");
            });
            Alert alert = AlertUtils.getAlert(info.toString(), Alert.AlertType.INFORMATION);
            alert.show();
        });
    }
    private void setSeatGird() {

        //Giả lập vé đã chọn
        List<String> ticketList = new ArrayList<>();
        ticketList.add("G5");
        ticketList.add("G7");
        ticketList.add("H3");
        ticketList.add("E7");
        ticketList.add("K2");
        screenLabel.setStyle("-fx-background-color: #99CCFF;");
        seatGrid.setPadding(new Insets(20));

        List<RoomSeatDetail> seatList = new ArrayList<>();
        
        try(Session ses = HibernateUtils.getFACTORY().openSession()) {
            ses.getTransaction().begin();
            Room room = ses.get(Room.class, "R02");
            room.getRoomType().getRoomSeatDetailList().forEach(p -> seatList.add(p));
            ses.getTransaction().commit();
            ses.close();

        } catch (Exception ex) {
            Logger.getLogger(FXMLSeatMapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int rowIndex = 0;
        int countCouple = 0;
        String coupleName = "";
        sortRSDList(seatList);
        for (int i = 0; i < seatList.size(); i++) {
            RoomSeatDetail item = seatList.get(i);
            String seatName = item.getSeatMap().getsMapID();
            Button bt = new Button(seatName);
            String seatType = item.getSeatType().getTypeGroup();

            bt.setPrefSize(60, 40);
            int columIndex = Integer.parseInt(seatName.substring(1)) - 1;
            if (seatName.charAt(0) != row[rowIndex]) {
                rowIndex++;
            }
            if (seatType.equalsIgnoreCase("Couple")) {

                coupleName = coupleName + seatName + "   ";
                countCouple++;
                if (countCouple == 2) {

                    RoomSeatDetail lastItem = seatList.get(i - 1);

                    seatGrid.add(bt, columIndex - 1, rowIndex, 2, 1);
                    bt.setText(coupleName);
                    bt.setPrefSize(125, 40);
                    countCouple = 0;
                    coupleName = "";
                    if (ticketList.contains(seatName)) {
                        //  bt.setDisable(true);
                        bt.setStyle("-fx-background-color: #555555; -fx-text-fill: white;");
                        continue;
                    } else {
                        bt.setStyle("-fx-background-color: #ff00ff; -fx-text-fill: white;");
                    }
                    bt.setOnAction((t) -> {
                        doubleButtonHandler(item, lastItem, bt, "#ff00ff");
                    });
                    continue;
                }
                continue;

            }
            seatGrid.add(bt, columIndex, rowIndex);
            if (ticketList.contains(seatName)) { 
                // bt.setDisable(true);
                bt.setStyle("-fx-background-color: #555555; -fx-text-fill: white;");
                continue;
            }
            if (seatType.equalsIgnoreCase("Normal")) {
                bt.setStyle("-fx-background-color: #9900cc; -fx-text-fill: white;");
                bt.setOnAction((t) -> {
                    singleButtonHandler(item, bt, "#9900cc");

                });
            }
            if (seatType.equalsIgnoreCase("VIP")) {
                bt.setStyle("-fx-background-color: #ff3333; -fx-text-fill: white;");
                bt.setOnAction((t) -> {
                    singleButtonHandler(item, bt, "#ff3333");

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

    private void singleButtonHandler(RoomSeatDetail item, Button bt, String currentColor) {
        String color = bt.getStyle().toLowerCase();
        if (color.contains(currentColor)) {    //Khi người dùng chọn ghế
            bt.setStyle("-fx-background-color: #00CC00; -fx-text-fill: white;");
            infoList.add(item);
            return;
        }
        ////Khi người dùng bỏ chọn ghế
        bt.setStyle("-fx-background-color:" + currentColor + "; -fx-text-fill: white;");
        infoList.remove(item);

    }

    private void doubleButtonHandler(RoomSeatDetail item, RoomSeatDetail lastItem, Button bt, String currentColor) {
        String color = bt.getStyle().toLowerCase();
        if (color.contains(currentColor)) {    //Khi người dùng chọn ghế
            bt.setStyle("-fx-background-color: #00CC00; -fx-text-fill: white;");
            infoList.add(item);
            infoList.add(lastItem);
            return;
        }
        ////Khi người dùng bỏ chọn ghế
        bt.setStyle("-fx-background-color:" + currentColor + "; -fx-text-fill: white;");
        infoList.remove(item);
        infoList.remove(lastItem);

    }
    private List<RoomSeatDetail> sortRSDList(List<RoomSeatDetail> list){
        list.sort((o1, o2) -> {
            int seatNum1 = o1.getSeatMap().getSeatNum();
            int seatNum2 = o2.getSeatMap().getSeatNum();
            int result = o1.getSeatMap().getSeatRow().compareTo(o2.getSeatMap().getSeatRow());
            if (result != 0) {
                return result;
            }
            return seatNum1 - seatNum2;
        });
        return list;
    }

}
