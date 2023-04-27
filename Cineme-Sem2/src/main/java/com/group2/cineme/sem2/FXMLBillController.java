package com.group2.cineme.sem2;

import DAO.BillDAO;
import DAO.CustomerDAO;
import DAO.FilmDAO;
import DAO.PromotionDAO;
import DAO.TicketDAO;
import POJO.Bill;
import POJO.Customer;
import POJO.Schedule;
import POJO.Film;
import POJO.ProductBill;
import POJO.Promotion;
import POJO.Ticket;
import Utils.AlertUtils;
import Utils.SessionUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FXMLBillController implements Initializable {

    private DecimalFormat formatter = new DecimalFormat("#,##0");

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
    private HBox hbox;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtVoucher;
    @FXML
    private Button buttonUseVoucher;

    @FXML
    private Button buttonUsePoint;

    private LocalDateTime printDate;
    BigDecimal total = BigDecimal.ZERO;

    Bill b1 = new Bill();
    private Schedule schedule;
    private BigDecimal sumTicket = BigDecimal.ZERO;
    private Customer cus;
    private long cusMoney;
    private int sale;

    BillDAO billDAO = new BillDAO();
    List<Promotion> promotions = new ArrayList<>();

    public FXMLBillController(VBox vbox) {
        this.vbox = vbox;
    }

    public FXMLBillController(Schedule schedule) {
        this.schedule = schedule;
    }

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

    //Ham de su dung diem thuong 10 diem <=> 1000 
    @FXML
    public void usePointButtonHandler() {
        if (cus == null) {
            AlertUtils.getAlert("Please enter Customer Phone!!!", Alert.AlertType.ERROR).show();
        } else {
            CustomerDAO cd = new CustomerDAO();
            int cusPoint = cus.getTotalPoints();
            cusMoney = cusPoint * 100;
            String info = "You have " + String.format("%d * 100 = %d", cusPoint, cusMoney) + "\n" + "Are you sure use all the Point";
            Alert alert = AlertUtils.getAlert(info, Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> results = alert.showAndWait();
            if (results.get().getText().equalsIgnoreCase("OK")) {
                BigDecimal cusP = BigDecimal.valueOf(cusMoney);
                total = total.subtract(cusP);
                //Kiem tra neu Tong - diem thuong < 0 thi xet total = 0, vi total khong the hien <0
                if (total.compareTo(BigDecimal.ZERO) < 0) {
                    total = BigDecimal.ZERO;
                }
                this.btotal.setText(formatter.format(total) + " VND");
                try {
                    cus.setTotalPoints(0);
                    cd.update(cus);
                } catch (Exception ex) {
                    Logger.getLogger(FXMLBillController.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.accPoint.setText(String.format("%d", cus.getTotalPoints()));
                this.buttonUsePoint.setDisable(true);
            }
        }

    }

    //Nut cho KH dang ki
    @FXML
    public void registerButtonHandler() throws IOException {
        if (this.txtPhone.getText().trim().isEmpty() || this.error.isVisible() == true) {
            AlertUtils.getAlert("Check phone again please!!!", Alert.AlertType.ERROR).show();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCustomer.fxml"));
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> p) {
                    return new FXMLCustomerController(txtPhone.getText());
                }
            });
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
            hbox.setDisable(true);
            stage.setOnHiding((t) -> {
                hbox.setDisable(false);
            });
        }

    }

    //Logic Promotion so num * 2000
    public int promoLogic(String promo) {
        int num = Integer.parseInt(promo.replaceAll("\\D+", "").trim());
        return num * 1000;
    }

    //Nut su dung Voucher
    @FXML
    public Promotion seachVoucherButtonHandler() {
        Promotion promo = null;
        for (Promotion promotion : promotions) {
            if (promotion.getPromoName().toLowerCase().equals(this.txtVoucher.getText().toLowerCase().trim())) {
                promo = promotion;
            }
        }
        return promo;
    }

    public void useVoucherButtonHandler() {
        if (seachVoucherButtonHandler()==null) {
            AlertUtils.getAlert("Please type Voucher Text again", Alert.AlertType.WARNING).show();
        } else {
            sale = promoLogic(seachVoucherButtonHandler().getPromoName());
            String info = "Are you sure use " + seachVoucherButtonHandler() + "?";
            Alert alert = AlertUtils.getAlert(info, Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> results = alert.showAndWait();
            if (results.get().getText().equalsIgnoreCase("OK")) {
                BigDecimal sales = BigDecimal.valueOf(sale);
                total = total.subtract(sales);
                //Kiem tra neu Tong - diem thuong < 0 thi xet total = 0, vi total khong the hien <0
                if (total.compareTo(BigDecimal.ZERO) < 0) {
                    total = BigDecimal.ZERO;
                }
                this.btotal.setText(formatter.format(total) + " VND");
                this.buttonUseVoucher.setDisable(true);
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
        printDate = LocalDateTime.now();
        this.pdate.setText(printDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
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
        if (schedule.getStartTime().toLocalDate().equals(LocalDate.now())) {
            this.time.setText(schedule.getStartTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        } else {
            this.time.setText(schedule.getStartTime().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));
        }
        this.ftotal.setText(formatter.format(sumTicket).replace(",", ".") + " VND");
        total = total.add(sumTicket);
        this.btotal.setText(formatter.format(total).replace(",", ".") + " VND");
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

        ptotal.setText(formatter.format(total).replace(",", ".") + " VND");
    }

    //Ham de load data cho comboBox voicher
    public void loadDataComboBox() {
        PromotionDAO pd = new PromotionDAO();
        promotions = pd.getPromoByDateTime("endTime");
    }

    @FXML
    private void btnBackHanlder() {
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnAcceptHanlder() throws Exception {
        if (seachVoucherButtonHandler()==null) {
            b1.setPromotion(null);
        } else {
            b1.setPromotion(seachVoucherButtonHandler());
        }
        b1.setPrintDate(printDate);
        b1.setCustomer(cus);
        b1.setEmployee(SessionUtil.getEmployee());
        b1.setExchangePoints((total.floatValue() / 1000));
        billDAO.add(b1);
        saveToDB();
        if (cus != null) {
            CustomerDAO cd = new CustomerDAO();
            cus.setTotalPoints(cus.getTotalPoints() + (total.intValue() / 1000));
            cd.update(cus);
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLPrintBill.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> p) {
                return new FXMLPrintBillController(cus, schedule, printDate, total, cusMoney + sale);
            }
        });
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
            hbox.setDisable(true);
            stage.setOnHiding((t) -> {
                hbox.setDisable(false);
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Stage stage = (Stage) hbox.getScene().getWindow();
        stage.close();
    }

    private void saveToDB() throws Exception {
        FilmDAO fd = new FilmDAO();
        TicketDAO ticDAO = new TicketDAO();
        List<ProductBill> proBillList = new ArrayList<>();
        Film film = schedule.getFilm();
        int currentView = film.getViewFilm();
        int selectView = currentView + SessionUtil.getTicketList().size();
        List<Film> a = SessionUtil.getMapFilm();
        for (Film film1 : a) {
            if (film1.getFilmName().equals(film.getFilmName())) {
                film1.setViewFilm(selectView);
            }
        }
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
        film.setViewFilm(selectView);
        fd.update(film);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scrollPane.setMaxHeight(vbox.getPrefHeight());
        try {
            loadDataComboBox();
            checkValidatePhone();
            loadProduct();
            loadToView();
        } catch (Exception ex) {
            Logger.getLogger(FXMLBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
