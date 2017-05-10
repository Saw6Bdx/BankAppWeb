/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import db.home.bank.Category;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;

/**
 * Class to see the percentages allocated to each category on a pie chart.
 * @author Mary
 * 
 */
public class BudgetCategoriesPieChartWindowController extends ControllerBase {

    @FXML
    private PieChart pieChart;
    @FXML
    private Button btnOK;
    
    private double[] percentage;
    private List<Category> categoryList;

    @Override
    public void initialize(Mediator mediator) {
    }
    
    public void setPercentage(double[] percentage) {
        this.percentage = percentage;
    }
    
    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
    
    /**
     * Method which use the informations of a previous treatment. 
     * The informations required are the categoryList (where you can access to 
     * its number of elements, its label ...) and the percentage table.
     * @param mediator 
     */
    public void initBudgetCategoriesWindowController(Mediator mediator) {
        
        Category categories;
        int nbCategories = this.categoryList.size();
        int i = 0;
        
        while (percentage[i] == 0.0) {
            i++;
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data(categoryList.get(i).getLabel(), percentage[i]));
        
        for (int j = i+1; j < nbCategories; j++) {
            categories = categoryList.get(j);
            if (percentage[j] != 0) {
                PieChart.Data pcd = new PieChart.Data(categories.getLabel(), percentage[j]);
                pieChartData.add(pcd);
            }
        }
        
        this.pieChart.setData(pieChartData);
        this.pieChart.setTitle("Categories");
        
    }
    
    @FXML
    private void handleButtonOK(ActionEvent event) throws IOException {
        //Hide current window
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
}
