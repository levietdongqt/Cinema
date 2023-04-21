/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.group2.cineme.sem2;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 * @author DONG
 */
public class FXMLFoodChartController implements Initializable {
    @FXML
    private PieChart chart;
    List<Object[]> list = new ArrayList<>();
    int year;
    int month;
    Double total;
    /**
     * Initializes the controller class.
     */
    
    public FXMLFoodChartController(List<Object[]> list,int year, int month) {
       this.list = list;
       this.year = year;
       this.month = month;
    }    

    public FXMLFoodChartController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDataPieChart();
        loadViewPieChart();
    }    
     public void loadDataPieChart() {
            
        List<PieChart.Data> dataList = new ArrayList<>();
        for (Object[] objects : list) {
            dataList.add(new PieChart.Data(objects[0].toString(), Integer.parseInt(objects[4].toString())));
        }
        chart.setData(FXCollections.observableList(dataList)); //lay da ta cho PieChart

    }
     public void loadViewPieChart(){
        total = 0.0;
        for (PieChart.Data data : chart.getData()) {
            total += data.getPieValue();
        }
        String text = getTitlePie();
        chart.setTitle(text); // Ham de set tieu de
        chart.setLabelLineLength(10); //Ham de set do rong cua line giua cac mang
        chart.setLabelsVisible(true);
        chart.setStartAngle(90); // Doi truc cua bieu do
        chart.setPrefSize(800, 600); //set do lon cua bieu do
        chart.setLegendVisible(true); //Ham de hien cac chu thich
        chart.getData().forEach(data -> {
            data.nameProperty().setValue(data.getName() + " " + String.format("%.2f%%", (data.getPieValue()*100)/total)); // Hien thi du lieu cua PieChart
            
            data.getNode().setScaleX(0.8);
            data.getNode().setScaleY(0.8); // set do phong to cua bieu do
        });
                 
     }
     public String getTitlePie(){
        String text = "";
        if(month==0){
            text = "REVENUE'S CHART "+String.format("%d",year);
        }else{
            text = "REVENUE'S CHART "+String.format("%d",month)+"/"+String.format("%d",year);
        }
        return text;    
    }
}
