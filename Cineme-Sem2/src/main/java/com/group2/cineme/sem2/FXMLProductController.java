/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import DAO.ProductDAO;
import POJO.Product;
import Utils.SessionUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.internal.BinaryStreamImpl;

/**
 * FXML Controller class
 *
 * @author DONG
 */
public class FXMLProductController implements Initializable {
    
    @FXML
    Pagination pagination;
    @FXML
    Label totalLabel;
    @FXML
    AnchorPane anchorPane;
    ProductDAO proDAO = new ProductDAO();
    Product[] productArray = new Product[8];
    int i = 0;
    int total = 0;
    int quatity = 0;
    Map<Product, Integer> mapProducts = new HashMap<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapProducts.putAll(SessionUtil.getProductList());
        mapProducts.forEach((t, u) -> {
            total += u * t.getPrice();
            totalLabel.setText(SessionUtil.toMoney(total));
        });
        try {            
            proDAO.getAll("Product").forEach((t) -> {
                productArray[i++] = t;
            });
        } catch (Exception ex) {
            Logger.getLogger(FXMLProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        data();
    }
    
    @FXML
    private void setUpBtnConfirm() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Confirm now!!!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().getText().equalsIgnoreCase("OK")) {
            Iterator<Map.Entry<Product, Integer>> iterator = mapProducts.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Product, Integer> entry = iterator.next();
                if (entry.getValue() == 0) {
                    iterator.remove();
                }
            }
            SessionUtil.setProductList(mapProducts);
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
        } else {
            alert.close();
        }
        
    }
    
    public void data() {
        pagination.setPageCount(2);
        pagination.setCurrentPageIndex(0);
        // pagination.setMaxPageIndicatorCount(3);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            public Node call(Integer pageIndex) {
                if (pageIndex == 0) {
                    return page1(4, 8);
                } else {
                    return page1(0, 4);
                }
            }
        });
    }
    
    private GridPane page1(int startIndex, int endIndex) {
        GridPane grid = new GridPane();
        ColumnConstraints colContrain = new ColumnConstraints();
        colContrain.setHalignment(HPos.RIGHT);
        colContrain.setMinWidth(300);
        ColumnConstraints colContrain1 = new ColumnConstraints();
        colContrain1.setMinWidth(150);
        
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setMinHeight(50);
        
        grid.getRowConstraints().add(0, rowConstraints);

        //grid.setGridLinesVisible(true);
        // Tạo header
        Label l1 = new Label("Name");
        l1.setStyle("-fx-font-size: 22 ;");
        Label l2 = new Label("Quantity");
        l2.setStyle("-fx-font-size: 22 ;");
        Label l3 = new Label("Price/per");
        l3.setStyle("-fx-font-size: 22 ;");
        grid.add(l1, 1, 0);
        grid.add(l2, 2, 0);
        grid.add(l3, 3, 0);
        
        for (int j = startIndex; j < endIndex; j++) {
            Product item = productArray[j];
            //Product Image
            ImageView imageView = new ImageView();
            String view = item.getImgUrl();
            String projectPath = System.getProperty("user.dir");
            if(!projectPath.endsWith("Cineme-Sem2")){
                projectPath = new File(projectPath).getParentFile().toString();
            }
            File f = new File(projectPath+"/"+view);
            Image image = new Image(f.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(120);
            imageView.setFitHeight(120);
            grid.add(imageView, 0, j + 1);
            //Product Name
            grid.add(new Label(item.getProductName()), 1, j + 1);
            //Lấy lại dữ liệu sản phẩm đã orders khi muốn thay đổi ý định mua sắm
            quatity = 0;
            mapProducts.forEach((t, u) -> {
                if (t.getProductName().equalsIgnoreCase(item.getProductName())) {
                    quatity = u;                    
                }
            });
            Spinner<Integer> spinner = new Spinner<>(0, 100, quatity);
            spinner.setStyle("-fx-background: #FFFFE8; -fx-border-width:0px 0px 0px 0px;");
            spinner.setMaxWidth(60);
            grid.add(spinner, 2, j + 1);
            spinner.valueProperty().addListener((ov, oldValue, newValue) -> {
                total = total + (newValue - oldValue) * item.getPrice();
                totalLabel.setText(SessionUtil.toMoney(total));
                mapProducts.put(item, ov.getValue());
                SessionUtil.getProductList().forEach((t, u) -> {
                    System.out.println(t.getProductName() + ": " + u);
                });
            });
            //product price
            grid.add(new Label(SessionUtil.toMoney(item.getPrice()) + " VND"), 3, j + 1);
        }
        grid.getColumnConstraints().addAll(colContrain, colContrain1, colContrain1, colContrain1);
        return grid;
    }
    
}
