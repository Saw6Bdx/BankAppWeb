/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import db.home.bank.Category;
import db.home.bank.Transactions;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import utils.AlertMessage;

/**
 *
 * @author Mary
 */
public class BudgetCategoriesWindowController_v3 extends ControllerBase {

    @FXML
    private Label labelAmount, labelPercentage, labelTotal;
    @FXML
    private ChoiceBox<Category> setCategoryList;

    @FXML
    private Button btnOK;

    private String currency = "€"; // parameter to be set according to the currency

    @Override
    public void initialize(Mediator mediator) {

        try {
            EntityManager em = mediator.createEntityManager();
            // Getting all the categories available
            TypedQuery<Category> qCategory = em.createQuery("SELECT a FROM Category a", Category.class);
            List<Category> categoryList = qCategory.getResultList();

            this.setCategoryList.setItems(FXCollections.observableArrayList(categoryList));
            this.setCategoryList.getSelectionModel().selectFirst();
            em.close();
        } catch (PersistenceException e) {
            this.btnOK.setDisable(true);
            AlertMessage.processPersistenceException(e);
        }

    }

    /**
     * Function which calculate the round value of a double
     *
     * @param A, double to be rounded
     * @param B, precision (number after the coma)
     * @return rounded value
     */
    private double round(double A, int B) {
        return (double) ((int) (A * Math.pow(10, B) + .5)) / Math.pow(10, B);
    }

    @FXML
    private void handleChoiceBoxCategory(ActionEvent event) throws IOException {

        Category category = setCategoryList.getValue();
        int idCategory = category.getId();

        EntityManager em = getMediator().createEntityManager();

        // Getting all the categories available
        TypedQuery<Category> qCategory = em.createQuery("SELECT a FROM Category a", Category.class);
        List<Category> categoryList = qCategory.getResultList();
        // Getting all the transactions, and its sum
        TypedQuery<Transactions> qTransactions = em.createQuery("SELECT b FROM Transactions b", Transactions.class);
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
                    System.out.println(categories);
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
        this.labelTotal.setText(new Double(round(sum, 2)).toString() + " " + this.currency);
        this.labelAmount.setText(tabSumCategory[idCategory - 1] + " " + this.currency);
        this.labelPercentage.setText(percentage[idCategory - 1] + " %");
        
        em.close();
    }

    @FXML
    private void handleButtonOK(ActionEvent event) throws IOException {
        ControllerBase controller = (AppWindowController) ControllerBase.loadFxml("AppWindow.fxml", getMediator());
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        //Hide current window
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
