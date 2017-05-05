/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import db.home.bank.Category;
import db.home.bank.Transactions;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import utils.AlertMessage;

/**
 *
 * @author Mary
 */
public class BudgetCategoriesPieChartWindowController extends ControllerBase {

    @FXML
    private PieChart pieChart;
    
    private int flagIdAccount;

    @Override
    public void initialize(Mediator mediator) {
    }
    
    public void setFlagAccount(int flagIdAccount) {
        this.flagIdAccount = flagIdAccount;
    }
    
    public void initBudgetCategoriesWindowController(Mediator mediator) {
        
        EntityManager em = getMediator().createEntityManager();

        // Getting all the categories available
        TypedQuery<Category> qCategory = em.createQuery("SELECT a FROM Category a", Category.class);
        List<Category> categoryList = qCategory.getResultList();
        
        // Getting all the transactions, and its sum, according to one account
        TypedQuery<Transactions> qTransactions = em.createQuery("SELECT b FROM Transactions b WHERE b.idAccount.id=:pacc", Transactions.class);
        qTransactions.setParameter("pacc", this.flagIdAccount);
        List<Transactions> transactionsList = qTransactions.getResultList();

        // Variables setting
        int nbCategories = categoryList.size();
        int nbTransactions = transactionsList.size();
        double tabSumCategory[] = new double[nbCategories + 1]; // +1 because some transactions can have no categories
        for (int i = 0; i < nbCategories; i++) { // initialization table
            tabSumCategory[i] = 0.0;
        }

        // Getting the amount of transactions by categories
        Transactions transactions = new Transactions();
        Category categories = new Category();
        double sum = 0.0, sumCat = 0.0;
        for (int j = 0; j < nbTransactions; j++) {

            transactions = transactionsList.get(j);
            sum += transactions.getAmount();

            for (int i = 0; i < nbCategories; i++) {

                categories = categoryList.get(i);

                // Checking if the category is identical
                if (categories.equals(transactions.getIdCategory())) {
                    tabSumCategory[i] += transactions.getAmount();
                    sumCat += transactions.getAmount();
                }
            }
        }

        tabSumCategory[nbCategories] = sum - sumCat;

        // Percentages
        double percentage[] = new double[nbCategories + 1];
        for (int i = 0; i < nbCategories + 1; i++) {
            percentage[i] = round(tabSumCategory[i] / sum * 100, 2);
            tabSumCategory[i] = round(tabSumCategory[i], 2);
        }

        // Setting percentage into the window
        Collection<PieChart.Data> collPieChart = null;
        for (int i = 0; i < nbCategories; i++) {

            categories = categoryList.get(i);
            System.out.println(categories.getLabel());
            System.out.println(percentage[i]);
            //categories.getLabel(), 10));
            //new PieChart.Data(categories.getLabel(), tabSumCategory[i]));
            collPieChart.add(new PieChart.Data("coucou",10));

            }
        
        ObservableList<PieChart.Data> pieChartData;
        pieChartData = FXCollections.observableArrayList(collPieChart);
        this.pieChart.setData(pieChartData);
        
        /*this.labelTotal.setText(new Double(round(sum, 2)).toString() + " " + this.currency);
        this.labelAmount.setText(tabSumCategory[idCategory - 1] + " " + this.currency);
        this.labelPercentage.setText(percentage[idCategory - 1] + " %");*/
        
        em.close();
        
        /*ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Grapefruit", 13),
                new PieChart.Data("Oranges", 25),
                new PieChart.Data("Plums", 10),
                new PieChart.Data("Pears", 22),
                new PieChart.Data("Apples", 30)
                );
        
        this.pieChart.setTitle("Imported Fruits");
        this.pieChart.setData(pieChartData);*/

    }
    
    private double round(double A, int B) {
        return (double) ((int) (A * Math.pow(10, B) + .5)) / Math.pow(10, B);
    }
}
