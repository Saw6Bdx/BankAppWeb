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
        this.pieChart.setTitle("Imported Fruits");
        
        em.close();
        
    }
    
    private double round(double A, int B) {
        return (double) ((int) (A * Math.pow(10, B) + .5)) / Math.pow(10, B);
    }
}
