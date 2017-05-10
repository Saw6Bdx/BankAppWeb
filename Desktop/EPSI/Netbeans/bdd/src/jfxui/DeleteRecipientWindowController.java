/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import db.home.bank.Recipient;
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
 * 
 * @author Nicolas
 */
public class DeleteRecipientWindowController extends ControllerBase {

    @FXML private ChoiceBox<Recipient> setRecipientsName;
    @FXML private Label setMsgOptional;
    @FXML private Button btnCancel;
    @FXML private Button btnOK;

    @Override
    public void initialize(Mediator mediator) {

        try {
            EntityManager em = getMediator().createEntityManager();
            List<Recipient> recipientList = em.createQuery("SELECT r FROM Recipient r").getResultList();
            
            this.setRecipientsName.setItems(FXCollections.observableArrayList(recipientList));
            em.close();

            if (recipientList.isEmpty()) {
                this.setMsgOptional.setText("No recipient available to be deleted");
                this.btnOK.setDisable(true);
            } 

        } catch (PersistenceException e) {
            this.btnOK.setDisable(true);
            AlertMessage.processPersistenceException(e);
        }

    }

    @FXML
    private void handleBtnOK(ActionEvent event) throws IOException {

        Recipient recipient = setRecipientsName.getValue();

        EntityManager em = getMediator().createEntityManager();

        Recipient rec = em.find(Recipient.class, recipient.getId());
        em.getTransaction().begin();
        em.remove(rec);
        em.getTransaction().commit();

        //Close current window
        Stage current = (Stage) btnOK.getScene().getWindow();
        current.close();

    }

    @FXML
    private void handleBtnCancel(ActionEvent event) throws IOException {

        //Close current window
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();

    }

}