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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import utils.AlertMessage;

/**
 * Class which allowed the user to delete one category. It selects in the
 * categories that the user has created.
 *
 * @author Mary
 */
public class DeleteCategoryWindowController extends ControllerBase {

    @FXML
    private ChoiceBox<Category> setCategoriesName;
    @FXML
    private Label setMsgOptional;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnOK;

    @Override
    public void initialize(Mediator mediator) {

        try {
            // Loading categories field (id > 14 because with the app there are 14 categories available that cannot be deleted)
            EntityManager em = getMediator().createEntityManager();
            List<Category> categoryList = em.createQuery("SELECT c FROM Category c WHERE c.id > 14").getResultList();

            this.setCategoriesName.setItems(FXCollections.observableArrayList(categoryList));
            em.close();

            if (categoryList.size() == 0) {
                this.setMsgOptional.setText("No category available to be deleted");
                this.btnOK.setDisable(true);
            }

        } catch (PersistenceException e) {
            this.btnOK.setDisable(true);
            AlertMessage.processPersistenceException(e);
        }

    }

    @FXML
    private void handleBtnOK(ActionEvent event) throws IOException {

        Category category = this.setCategoriesName.getValue();

        try {
            EntityManager em = getMediator().createEntityManager();

            Category cat = em.find(Category.class, category.getId());
            em.getTransaction().begin();
            em.remove(cat);
            em.getTransaction().commit();

            em.close();
        } catch (PersistenceException e) {
            AlertMessage.processPersistenceException(e);
        }

        //Close current window
        Stage current = (Stage) this.btnOK.getScene().getWindow();
        current.close();

    }

    @FXML
    private void handleBtnCancel(ActionEvent event) throws IOException {

        //Close current window
        Stage current = (Stage) this.btnCancel.getScene().getWindow();
        current.close();

    }

}
