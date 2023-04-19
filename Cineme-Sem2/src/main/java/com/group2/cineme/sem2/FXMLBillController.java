package com.group2.cineme.sem2;

import DAO.BillDAO;
import DAO.CustomerDAO;
import DAO.FilmDAO;
import DAO.ProductDAO;
import DAO.TicketDAO;
import POJO.Bill;
import POJO.Customer;
import POJO.Schedule;
import POJO.Film;
import POJO.ProductBill;
import POJO.Ticket;
import Utils.AlertUtils;
import Utils.SessionUtil;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class FXMLBillController implements Initializable {

    @FXML
    private Label btotal;

    @FXML
    private AnchorPane billarea;

    @FXML
    private Button exportbut;

    @FXML
    private Label empid;

    @FXML
    private Label filmname;

    @FXML
    private Label ftotal;

    @FXML
    private Label pdate;

    @FXML
    private Label ptotal;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label seats;

    @FXML
    private Label ticket;

    @FXML
    private Label time;

    @FXML
    private Label accPoint;

    @FXML
    private Label error;
    @FXML
    private VBox vbox;
    @FXML
    private TextField txtPhone;
    @FXML
    private ComboBox cBVoicher;

    BigDecimal total = BigDecimal.ZERO;

    Bill b1 = new Bill();
    private Schedule schedule;
    private BigDecimal sumTicket = BigDecimal.ZERO;
    private Customer cus;

    BillDAO billDAO = new BillDAO();

    public FXMLBillController(VBox vbox) {
        this.vbox = vbox;
    }

    public FXMLBillController(Schedule schedule) {
        this.schedule = schedule;
    }

//    public void testData() throws Exception {
//        ProductDAO pdao = new ProductDAO();
//
//        pdao.getAll("Product").forEach((t) -> {
//            SessionUtil.getProductList().put(t, 3);
//        });
//
//        Ticket tk = new Ticket();
//        Ticket tk2 = new Ticket();
//
//        tk.setBill(b1);
//        tk2.setBill(b1);
//
//        tk.setSeatMap("A10");
//        tk2.setSeatMap("B50");
//        SessionUtil.getTicketList().add(tk);
//        SessionUtil.getTicketList().add(tk2);
//
//        SessionUtil.getTicketList().forEach((t) -> {
//
//        });
//
//        SessionUtil.getEmployee().setEmpName("mmb vcl");
//        SessionUtil.getEmployee().setUserName("aduma mmb vcl");
//
//        b1.setEmployee(SessionUtil.getEmployee());
//        b1.setPrintDate(LocalDateTime.now());
//        SessionUtil.getProductList().forEach((p, a) -> {
//            System.out.println(p.getProductName());
//            System.out.println(a);
//        });
//    }
    //Ham de search xem khach co ton tai trong db khong? => neu co: accPoint se tu dong cap nhat so diem cua khach
    @FXML
    public void searchPhoneButtonHandler() {
        if (this.error.isVisible() == true || this.txtPhone.getText().trim().isEmpty()) {
            AlertUtils.getAlert("Check phone again please!!!", Alert.AlertType.ERROR).show();
        } else {
            CustomerDAO cd = new CustomerDAO();
            try {
                cus = cd.getById(this.txtPhone.getText(), Customer.class);
                if (cus != null) {
                    AlertUtils.getAlert("Accumulated Point's" + cus.getCustomerName() + " is updated", Alert.AlertType.CONFIRMATION).show();
                    this.accPoint.setText(String.format("%d", cus.getTotalPoints()));
                } else {
                    AlertUtils.getAlert("Customer dont' existed", Alert.AlertType.CONFIRMATION).show();
                    this.accPoint.setText("unknow");
                }
            } catch (Exception ex) {
                Logger.getLogger(FXMLBillController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    //Ham de check loi luc nhap so dien thoai
    public void checkValidatePhone() {
        this.txtPhone.textProperty().addListener((o) -> {
            if (this.txtPhone.getText().trim().isEmpty()) {
                this.error.setText("Please enter phone number");
                this.error.setVisible(true);
            } else if (!Pattern.matches("({0,1}[0-9]{3}){0,1}[-.\\s]{0,1}[0-9]{3}[-.\\s]{0,1}[0-9]{4}", this.txtPhone.getText().trim())) {
                this.error.setText("Please enter phone [1234567890],[123 456 7890],");
                this.error.setVisible(true);
            } else {
                this.error.setVisible(false);
            }
        });
    }

    //Ham de load tat ca cac noi dung len Bill
    public void loadToView() {
        this.filmname.setText(schedule.getFilm().getFilmName());
        this.empid.setText(SessionUtil.getEmployee().getEmpName());
        String text = "";
        List<Ticket> tickets = SessionUtil.getTicketList();
        int sum = 0;
        for (Ticket ticket1 : tickets) {
            text += ticket1.getSeatMap() + " ";
            sum += ticket1.getPrice();
        }
        sumTicket = BigDecimal.valueOf(sum);
        this.seats.setText(text);
        this.ticket.setText(String.format("%d", tickets.size()));
        if(schedule.getStartTime().toLocalDate().equals(LocalDate.now())){
            this.time.setText(schedule.getStartTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        }else{
            this.time.setText(schedule.getStartTime().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));
        }
        this.ftotal.setText(sumTicket.toString());
        this.pdate.setText(LocalDateTime.now().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.btotal.setText(total.add(sumTicket).toString());
    }

    public void loadProduct() {
        System.out.println(SessionUtil.getProductList().toString());
        SessionUtil.getProductList().forEach((product, quantity) -> {

            HBox hbox = new HBox();
            hbox.setPrefWidth(vbox.getPrefWidth());
            hbox.styleProperty().setValue("-fx-border-width: 0 0 2 0; -fx-border-color: #ffffff;");

            Label name = new Label();
            name.setPrefWidth(hbox.getPrefWidth() / 3);
            name.setAlignment(Pos.TOP_CENTER);
            name.setText(product.getProductName());

            Label quant = new Label();
            quant.setPrefWidth(hbox.getPrefWidth() / 3);
            quant.setAlignment(Pos.TOP_CENTER);
            quant.setText(quantity.toString());

            Label price = new Label();
            price.setPrefWidth(hbox.getPrefWidth() / 3);
            price.setAlignment(Pos.TOP_CENTER);
            price.setText(String.valueOf(product.getPrice()));

            hbox.getChildren().add(name);
            hbox.getChildren().add(quant);
            hbox.getChildren().add(price);

            vbox.getChildren().add(hbox);

            BigDecimal productTotal = BigDecimal.valueOf((product.getPrice() * quantity));
            total = total.add(productTotal);

        });

        ptotal.setText(total.toString());
    }

    public void exportpdf() {
        exportbut.setOnAction(event -> {
            PrinterJob job = PrinterJob.createPrinterJob();

            if (job != null) {
                boolean success = job.printPage(billarea);
                if (success) {
                    job.endJob();
                }
            }
        });
    }

//    public void otherData() {
//        pdate.setText(b1.getPrintDate().toString());
//        empid.setText(b1.getEmployee().getEmpName());
//    }
    @FXML
    private void btnBackHanlder() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnAcceptHanlder() throws Exception {
        b1.setEmployee(SessionUtil.getEmployee());
        billDAO.add(b1);
        saveToDB();
        Alert alert = AlertUtils.getAlert("Buy Ticket successful!!", Alert.AlertType.INFORMATION);
        SessionUtil.getProductList().clear();
        SessionUtil.getTicketList().clear();
        alert.showAndWait();
        App.setView("FXMLShowSchedule");
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();

    }

    private void saveToDB() throws Exception {
        FilmDAO fd = new FilmDAO();
        TicketDAO ticDAO = new TicketDAO();
        List<ProductBill> proBillList = new ArrayList<>();
//        Film film = scheule.getFilm();
//        int currentView = film.getViewFilm();
//        int selectView = currentView + SessionUtil.getTicketList().size();
        SessionUtil.getTicketList().forEach((t) -> {
            try {
                t.setBill(b1);
            } catch (Exception ex) {
                Logger.getLogger(FXMLTicketController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        SessionUtil.getProductList().forEach((key, vaule) -> {
            ProductBill proBill = new ProductBill();
            String proBillID = String.valueOf(b1.getBillID()) + key.productId.substring(1);
            proBill.setBill(b1);
            proBill.setProBillID(proBillID);
            proBill.setProduct(key);
            proBill.setQuantity(vaule);
            proBillList.add(proBill);
        });
        ticDAO.addListTicketAndProduct(SessionUtil.getTicketList(), proBillList);
//        film.setViewFilm(selectView);
//        fd.update(film);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scrollPane.setMaxHeight(vbox.getPrefHeight());

        try {
            checkValidatePhone();
//            testData();
//            otherData();
            loadProduct();
            loadToView();
//            testData();
//            otherData();

            exportpdf();
        } catch (Exception ex) {
            Logger.getLogger(FXMLBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
