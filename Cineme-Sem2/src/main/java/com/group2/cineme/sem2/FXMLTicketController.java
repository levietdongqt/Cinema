/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;


import DAO.BillDAO;
import DAO.ScheduleDAO;
import DAO.TicketDAO;
import POJO.Bill;
import POJO.ProductBill;
import POJO.RoomSeatDetail;
import POJO.Schedule;
import POJO.Ticket;
import Utils.SessionUtil;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXMLTicketController implements Initializable {

    @FXML
    Label screenLabel;
    @FXML
    GridPane seatGrid;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Button btnNextStep;
    @FXML
    private Label filmLabel;
    @FXML
    private Label roomLabel;
    @FXML
    private Label seatLabel;
    @FXML
    private VBox vbox;
    @FXML
    private Label scheduleLabel;
    @FXML
    private Label foodLabel;
    @FXML
    private Label ticketLabel;
    @FXML
    private Label totalLabel;
    ScheduleDAO scheDAO = new ScheduleDAO();
    Schedule scheule;
    private char[] row = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K'};
    List<RoomSeatDetail> selectedSeatList = new ArrayList<>();
    String seatNameList;
    int ticketTotal = 0;
    int foodTotal = 0;
    int i = 0;

    public FXMLTicketController() {
    }

    public FXMLTicketController(Schedule schedule) {
        this.scheule = schedule;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getSchedule();
        loadDataFilm();
        setSeatGird();
    }

    @FXML
    private void saveToDB() throws Exception {
        BillDAO billDAO = new BillDAO();
        TicketDAO ticDAO = new TicketDAO();
        Bill bill = billDAO.getById(3, Bill.class);
        List<ProductBill> proBillList = new ArrayList<>();
        selectedSeatList.forEach((t) -> {
            try {
                Ticket ticket = new Ticket();
                ticket.setBill(bill);
                ticket.setSchedule(scheule);
                ticket.setSeatMap(t.getSeatMap().getsMapID());
                ticket.setStatus(Boolean.TRUE);
                SessionUtil.getTicketList().add(ticket);
            } catch (Exception ex) {
                Logger.getLogger(FXMLTicketController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        SessionUtil.getProductList().forEach((key, vaule) -> {
            ProductBill proBill = new ProductBill();
            String proBillID = String.valueOf(LocalDate.now().getDayOfMonth()) + i++;
            proBill.setBill(bill);
            proBill.setProBillID(proBillID);
            proBill.setProduct(key);
            proBill.setQuantity(vaule);
            proBillList.add(proBill);
        });
        ticDAO.addListTicketAndProduct(SessionUtil.getTicketList(), proBillList);
    }

    public void getSchedule() {
        try {
            this.scheule = scheDAO.getById("SC202347173241", Schedule.class);
        } catch (Exception ex) {
            Logger.getLogger(FXMLTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDataFilm() {
        filmLabel.setText(scheule.getFilm().getFilmName());
        roomLabel.setText(scheule.getRoomTypeDetail().getRoom().getRoomName());
        String duration = scheule.getStartTime().toLocalTime() + " - " + scheule.getEndTime().toLocalTime();
        String date = scheule.getStartTime().toLocalDate().getMonth() + "-" + scheule.getStartTime().toLocalDate().getDayOfMonth();
        scheduleLabel.setText(duration + ". " + date + " . " + scheule.getNote());
    }

    private void getSelectedSeats() {
        seatNameList = "";
        ticketTotal = 0;
        sortRSDList(selectedSeatList);
        selectedSeatList.forEach((t) -> {
            ticketTotal += t.getSeatType().getSeatPrice();
            seatNameList += t.getSeatMap().getsMapID().toString() + " ";
        });
        seatLabel.setText(seatNameList);
        ticketLabel.setText(String.valueOf(ticketTotal) + " VND");
        totalLabel.setText(String.valueOf(ticketTotal + foodTotal) + " VND");
    }

    @FXML
    private void setUpBtnFood() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLProduct.fxml"));
//        AnchorPane popupContent = fxmlLoader.load();
//        // FXMLEditScheduleController editControl = fxmlLoader.getController();
//        popupFood.getContent().add(popupContent);
//        popupContent.setStyle("-fx-background-color:white");
//        popupFood.show(anchorPane.getScene().getWindow());
//        anchorPane.getParent().setDisable(true);
//       // gridPane.getParent().setDisable(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLProduct.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Food");
        stage.setScene(new Scene(root));
        stage.show();
        anchorPane.getParent().setDisable(true);
        stage.setOnHidden((t) -> {
            anchorPane.getParent().setDisable(false);
            foodTotal = 0;
            SessionUtil.getProductList().forEach((product, quantity) -> {
               // SessionUtil.getProductList() trả về 1 tập hợp Map
                // product  là Key và 1 đối tượng Product
                // quantity là Value và số lượng đặt mua
                System.out.println(product.getProductName() + ": " + quantity);
                foodTotal += product.getPrice().intValue() * quantity;
            });
            foodLabel.setText(String.valueOf(foodTotal) + " VND");
            totalLabel.setText(String.valueOf(ticketTotal + foodTotal) + " VND");
        });
    }


    @FXML
    private void btnNextStepHandler() {
        List<Ticket> ticketList = new ArrayList<>();

        selectedSeatList.forEach((t) -> {

            Ticket ticket = new Ticket();
            ticket.setSchedule(this.scheule);
            ticket.setStatus(Boolean.TRUE);
            ticket.setSeatMap(t.getSeatMap().getsMapID());

            ticketList.add(ticket);
        });
        SessionUtil.setTicketList(ticketList);
        SessionUtil.getTicketList().forEach((t) -> {
            System.out.println(t.getSeatMap());
        });

    }

    private void setSeatGird() {

        //Lấy list vé đã chọn
        List<String> ticketBlockedList = new ArrayList<>();
        TicketDAO ticketDAO = new TicketDAO();
        ticketDAO.getTicketBySchedule(scheule).forEach((t) -> {
            ticketBlockedList.add(t.getSeatMap());
        });
        //screenLabel.setStyle("-fx-background-color: #99CCFF;");
        seatGrid.setPadding(new Insets(20));

        List<RoomSeatDetail> seatList = new ArrayList<>();

        seatList = scheDAO.getRoomSeatDetails(scheule);

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
            //Xử lý riêng cho nút couple
            if (seatType.equalsIgnoreCase("Couple")) {

                coupleName = coupleName + seatName + "   ";    //Ghép tên ghế đôi
                countCouple++;                    //đếm thứ tự ghế trong trong 2 ghế
                if (countCouple == 2) {

                    RoomSeatDetail lastItem = seatList.get(i - 1);   //Lấy ghế đầu tiên trong 2 ghế

                    seatGrid.add(bt, columIndex - 1, rowIndex, 2, 1); 
                    bt.setText(coupleName);
                    bt.setPrefSize(125, 40);
                    countCouple = 0;
                    coupleName = "";
                    if (ticketBlockedList.contains(seatName)) {
                        //  bt.setDisable(true);
                        bt.setStyle("-fx-background-color: #555555; -fx-text-fill: white;");
                        continue;
                    } else {
                        bt.setStyle("-fx-background-color: #ff00ff; -fx-text-fill: white;");
                    }
                    bt.setOnAction((t) -> {
                        changeStatusCoupleSeat(item, lastItem, bt, "#ff00ff");
                        getSelectedSeats();
                    });
                    continue;
                }
                continue;

            }
            seatGrid.add(bt, columIndex, rowIndex);
            if (ticketBlockedList.contains(seatName)) {
                bt.setStyle("-fx-background-color: #555555; -fx-text-fill: white;");
                continue;
                // bt.setDisable(true);              
            }
            if (seatType.equalsIgnoreCase("Normal")) {
                bt.setStyle("-fx-background-color: #9900cc; -fx-text-fill: white;");
                bt.setOnAction((t) -> {
                    changeStatusSingelSeat(item, bt, "#9900cc");
                    getSelectedSeats();
                });
            }
            if (seatType.equalsIgnoreCase("VIP")) {
                bt.setStyle("-fx-background-color: #ff3333; -fx-text-fill: white;");
                bt.setOnAction((t) -> {
                    changeStatusSingelSeat(item, bt, "#ff3333");
                    getSelectedSeats();

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

        // anchorPane.setMinWidth(row*columnWidth);
        // anchorPane.setMinHeight(columNum * rowHight);
    }

    private void changeStatusSingelSeat(RoomSeatDetail item, Button bt, String currentColor) {
        String color = bt.getStyle().toLowerCase();
        if (color.contains(currentColor)) {    //Khi người dùng chọn ghế
            bt.setStyle("-fx-background-color: #00CC00; -fx-text-fill: white;");
            selectedSeatList.add(item);
            return;
        }
        ////Khi người dùng bỏ chọn ghế
        bt.setStyle("-fx-background-color:" + currentColor + "; -fx-text-fill: white;");
        selectedSeatList.remove(item);

    }

    private void changeStatusCoupleSeat(RoomSeatDetail item, RoomSeatDetail lastItem, Button bt, String currentColor) {
        String color = bt.getStyle().toLowerCase();
        if (color.contains(currentColor)) {    //Khi người dùng chọn ghế
            bt.setStyle("-fx-background-color: #00CC00; -fx-text-fill: white;");
            selectedSeatList.add(item);
            selectedSeatList.add(lastItem);
            return;
        }
        ////Khi người dùng bỏ chọn ghế
        bt.setStyle("-fx-background-color:" + currentColor + "; -fx-text-fill: white;");
        selectedSeatList.remove(item);
        selectedSeatList.remove(lastItem);

    }

    private List<RoomSeatDetail> sortRSDList(List<RoomSeatDetail> list) {
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
