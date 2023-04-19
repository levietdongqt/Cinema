/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.BillDAO;
import DAO.FilmDAO;
import DAO.ScheduleDAO;
import DAO.TicketDAO;
import POJO.Bill;
import POJO.Film;
import POJO.ProductBill;
import POJO.RoomSeatDetail;
import POJO.Schedule;
import POJO.Ticket;
import Utils.AlertUtils;
import Utils.MyException;
import Utils.SessionUtil;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    @FXML
    private Label dateLabel;
    ScheduleDAO scheDAO = new ScheduleDAO();
    Schedule scheule;
    private char[] row = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K'};
    String seatNameList;
    int ticketTotal = 0;
    int foodTotal = 0;
    int i = 1;

    public FXMLTicketController() {
    }

    public FXMLTicketController(Schedule schedule) {
        this.scheule = schedule;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadDataFilm();
        setSeatGird();
    }

    @FXML
    private void setUpBtnBill() throws Exception {
        try {
            if (SessionUtil.getProductList().isEmpty() && SessionUtil.getTicketList().isEmpty()) {
                throw new MyException("Order something frist!");
            }
        } catch (MyException ex) {
            Alert alert = AlertUtils.getAlert(ex.getMessage(), Alert.AlertType.ERROR);
            alert.show();
            return;
        }
        saveToDB();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLBill.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> p) {
                return new FXMLBillController(scheule);
            }
        });
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.show();
        anchorPane.getParent().setDisable(true);
        stage.setOnHiding((t) -> {
            anchorPane.getParent().setDisable(false);
        });
    }

    private void saveToDB() throws Exception {
        FilmDAO fd = new FilmDAO();
        BillDAO billDAO = new BillDAO();
        TicketDAO ticDAO = new TicketDAO();
        Bill bill = billDAO.getById(7, Bill.class);
        List<ProductBill> proBillList = new ArrayList<>();
        Film film = scheule.getFilm();
        int currentView = film.getViewFilm();
        int selectView = currentView + SessionUtil.getTicketList().size();
        SessionUtil.getTicketList().forEach((t) -> {
            try {
                t.setBill(bill);
            } catch (Exception ex) {
                Logger.getLogger(FXMLTicketController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        SessionUtil.getProductList().forEach((key, vaule) -> {
            ProductBill proBill = new ProductBill();
            String proBillID = String.valueOf(LocalDate.now().getDayOfMonth())
                    + String.valueOf(LocalTime.now().getHour()) + String.valueOf(LocalTime.now().getMinute()) + i++;
            proBill.setBill(bill);
            proBill.setProBillID(proBillID);
            proBill.setProduct(key);
            proBill.setQuantity(vaule);
            proBillList.add(proBill);
        });
        ticDAO.addListTicketAndProduct(SessionUtil.getTicketList(), proBillList);
        film.setViewFilm(selectView);
        fd.update(film);

    }

//    public void getSchedule() {
//        try {
//            this.scheule = scheDAO.getById("SC202347173241", Schedule.class);
//        } catch (Exception ex) {
//            Logger.getLogger(FXMLTicketController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    private void loadDataFilm() {
        filmLabel.setText(scheule.getFilm().getFilmName());
        roomLabel.setText(scheule.getRoomTypeDetail().getRoom().getRoomName() + "  " + scheule.getNote());
        String duration = scheule.getStartTime().toLocalTime() + " - " + scheule.getEndTime().toLocalTime();
        String date = scheule.getStartTime().toLocalDate().getMonth() + "-" + scheule.getStartTime().toLocalDate().getDayOfMonth();
        dateLabel.setText(date);
        scheduleLabel.setText(duration);
    }

    private void getSelectedSeats() {
        seatNameList = "";
        ticketTotal = 0;
        SessionUtil.getTicketList().sort((o1, o2) -> {         
            int seatNum1 = Integer.parseInt(o1.getSeatMap().substring(1));
            int seatNum2 = Integer.parseInt(o2.getSeatMap().substring(1));
            int result = String.valueOf(o1.getSeatMap().charAt(0)).compareTo(String.valueOf(o2.getSeatMap().charAt(0)));
            if (result != 0) {
                return result;
            }
            return seatNum1 - seatNum2;
        });
        SessionUtil.getTicketList().forEach((t) -> {
            ticketTotal += t.getPrice();
            seatNameList += t.getSeatMap() + " ";
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
                foodTotal += product.getPrice() * quantity;
            });
            foodLabel.setText(String.valueOf(foodTotal) + " VND");
            totalLabel.setText(String.valueOf(ticketTotal + foodTotal) + " VND");
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
        seatList.sort((o1, o2) -> {
            int seatNum1 = o1.getSeatMap().getSeatNum();
            int seatNum2 = o2.getSeatMap().getSeatNum();
            int result = o1.getSeatMap().getSeatRow().compareTo(o2.getSeatMap().getSeatRow());
            if (result != 0) {
                return result;
            }
            return seatNum1 - seatNum2;
        });
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
            addTicketToSessionUtils(item);
            return;
        }
        ////Khi người dùng bỏ chọn ghế
        bt.setStyle("-fx-background-color:" + currentColor + "; -fx-text-fill: white;");
        removeTicketFromSessionUtils(item);

    }

    private void changeStatusCoupleSeat(RoomSeatDetail item, RoomSeatDetail lastItem, Button bt, String currentColor) {
        String color = bt.getStyle().toLowerCase();
        if (color.contains(currentColor)) {    //Khi người dùng chọn ghế
            bt.setStyle("-fx-background-color: #00CC00; -fx-text-fill: white;");
            addTicketToSessionUtils(item);
            addTicketToSessionUtils(lastItem);
            return;
        }
        ////Khi người dùng bỏ chọn ghế
        bt.setStyle("-fx-background-color:" + currentColor + "; -fx-text-fill: white;");
        removeTicketFromSessionUtils(item);
        removeTicketFromSessionUtils(lastItem);

    }

    private void addTicketToSessionUtils(RoomSeatDetail item) {
        Ticket ticket = new Ticket();
        ticket.setSchedule(scheule);
        ticket.setSeatMap(item.getSeatMap().getsMapID());
        ticket.setStatus(Boolean.TRUE);
        ticket.setPrice(item.getSeatType().getSeatPrice());
        SessionUtil.getTicketList().add(ticket);
    }

    private void removeTicketFromSessionUtils(RoomSeatDetail item) {
        Iterator<Ticket> listMirror = SessionUtil.getTicketList().iterator();
        while (listMirror.hasNext()) {
            Ticket ticket = listMirror.next();
            if (ticket.getSeatMap().equalsIgnoreCase(item.getSeatMap().getsMapID())) {
                listMirror.remove();
            }

        }
    }
}
