/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import db.home.bank.AccountManager;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import utils.AlertMessage;

/**
 *
 * @author Mary
 */
public class DeleteAccountManagerWindowController extends ControllerBase {

    @FXML private ChoiceBox<AccountManager> setAccountManagerName;
    @FXML private Button btnCancel;
    @FXML private Button btnOK;
   
    @Override
    public void initialize(Mediator mediator) {
        
        try {
            EntityManager em = mediator.createEntityManager();
            List<AccountManager> accountManager = em.createNamedQuery("AccountManager.findAll",AccountManager.class).getResultList();
            
            System.out.println(accountManager);

            this.setAccountManagerName.setItems(FXCollections.observableArrayList(accountManager));
            em.close();
        }
	catch(PersistenceException e) {
            this.btnOK.setDisable(true);
            AlertMessage.processPersistenceException(e);
	}
        
    }
    
    @FXML
    private void handleBtnOK(ActionEvent event) throws IOException {

        AccountManager accountManager = setAccountManagerName.getValue();

        System.out.println(accountManager.getId());

        EntityManager em = getMediator().createEntityManager();

        AccountManager am = em.find(AccountManager.class, accountManager.getId());
        em.getTransaction().begin();
        em.remove(am);
        em.getTransaction().commit();

        //Close current window
        Stage current = (Stage) btnOK.getScene().getWindow();
        current.close();

    }
    
    @FXML
    private void handleBtnCancel(ActionEvent event) throws IOException {

        //Close current window
        Stage current = (Stage)btnCancel.getScene().getWindow();
        current.close();
        
    }
    
}
