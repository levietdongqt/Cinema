package com.group2.cineme.sem2;

import DAO.ProductDAO;
import POJO.Bill;
import POJO.Ticket;
import Utils.SessionUtil;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

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
    private VBox vbox;

    BigDecimal total = BigDecimal.ZERO;

    Bill b1 = new Bill();

    public FXMLBillController(VBox vbox) {
        this.vbox = vbox;
    }

    public FXMLBillController() {

    }

    public void testData() throws Exception {
        ProductDAO pdao = new ProductDAO();

        pdao.getAll("Product").forEach((t) -> {
            SessionUtil.getProductList().put(t, 3);
        });

        Ticket tk = new Ticket();
        Ticket tk2 = new Ticket();

        tk.setBill(b1);
        tk2.setBill(b1);

        tk.setSeatMap("A10");
        tk2.setSeatMap("B50");
        SessionUtil.getTicketList().add(tk);
        SessionUtil.getTicketList().add(tk2);

        SessionUtil.getTicketList().forEach((t) -> {

        });

        SessionUtil.getEmployee().setEmpName("mmb vcl");
        SessionUtil.getEmployee().setUserName("aduma mmb vcl");

        b1.setEmployee(SessionUtil.getEmployee());
        b1.setPrintDate(LocalDateTime.now());
        SessionUtil.getProductList().forEach((p, a) -> {
            System.out.println(p.getProductName());
            System.out.println(a);
        });
    }

    public void loadProduct() {

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
            price.setText(product.getPrice().toString());

            hbox.getChildren().add(name);
            hbox.getChildren().add(quant);
            hbox.getChildren().add(price);

            vbox.getChildren().add(hbox);

            BigDecimal productTotal = product.getPrice().multiply(new BigDecimal(quantity));
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

    public void otherData() {
        pdate.setText(b1.getPrintDate().toString());
        empid.setText(b1.getEmployee().getEmpName());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("\n VO \n");

        scrollPane.setMaxHeight(vbox.getPrefHeight());

        try {
            testData();
            otherData();
            loadProduct();
            exportpdf();
        } catch (Exception ex) {
            Logger.getLogger(FXMLBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}