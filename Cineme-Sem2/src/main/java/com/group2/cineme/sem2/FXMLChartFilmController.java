/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import POJO.Film;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;

/**
 * FXML Controller class
 *
 * @author thuhuytran
 */
public class FXMLChartFilmController implements Initializable {
    
    @FXML
    private PieChart pieChart;

    private List<Film> films;
    private int year;
    private int month;
    @FXML
    private Button btnClose;
    double total;
   
    

    public FXMLChartFilmController(List<Film> film,int year,int month) {
        this.films = film;
        this.year=year;
        this.month=month;
    }

    public void initialize(URL url, ResourceBundle rb) {
        loadDataPieChart();
        loadViewPieChart();
    }

    public void setUpBtnColse() {
        Popup popup = (Popup) btnClose.getScene().getWindow();
        popup.hide();
    }

    public void loadDataPieChart() {
        List<PieChart.Data> lists = new ArrayList<>();
        int b = 50000;
        for (Film film : films) {
            lists.add(new PieChart.Data(film.getFilmName(), film.getSumPriceTicket() + b));
            
        }
        this.pieChart.setData(FXCollections.observableList(lists));

    }

    public void loadViewPieChart() {
        total = 0.0;
        for (PieChart.Data data : pieChart.getData()) {
            total += data.getPieValue();
        }
        String text = getTitlePie();
        pieChart.setTitle(text);
        pieChart.setLabelLineLength(10);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(90);
        pieChart.setClockwise(true);
        pieChart.setPrefSize(800, 600);
        pieChart.setLegendVisible(true);
        pieChart.getData().forEach(data -> {
            data.nameProperty().setValue(data.getName() + " " + String.format("%.2f%%", (data.getPieValue()*100)/total));
            
            data.getNode().setScaleX(0.8);
            data.getNode().setScaleY(0.8);
            
        });    
        
    }
    public String getTitlePie(){
        String text = "";
        if(month==0){
            text = "REPORT FILM "+String.format("%d",year);
        }else{
            text = "REPORT FILM "+String.format("%d",month)+"/"+String.format("%d",year);
        }
        return text;    
    }

}
