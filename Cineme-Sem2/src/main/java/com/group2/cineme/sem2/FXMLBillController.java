package com.group2.cineme.sem2;

import Utils.SessionUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class FXMLBillController {

    @FXML
    private VBox vbox;

    public FXMLBillController(VBox vbox) {
        this.vbox = vbox;
    }

    public FXMLBillController() {
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        loadProduct();
    }

    public void loadProduct() {
        SessionUtil.getProductList().forEach((product, quantity) -> {
            // SessionUtil.getProductList() trả về 1 tập hợp Map
            // product  là Key và 1 đối tượng Product
            // quantity là Value và số lượng đặt mua
            AnchorPane pane = new AnchorPane();
            
            HBox hbox = new HBox();
            hbox.setSpacing(50);
            hbox.getChildren().add(new Label(product.getProductName()));
            hbox.getChildren().add(new Label("" + quantity));
            
            pane.getChildren().add(hbox);
        
            vbox.getChildren().add(pane);
        });
    }

}
