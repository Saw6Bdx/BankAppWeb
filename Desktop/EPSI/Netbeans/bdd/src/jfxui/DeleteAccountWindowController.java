/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import db.home.bank.Account;
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
import javax.persistence.TypedQuery;
import utils.AlertMessage;

/**
 *
 * @author Mary
 */
public class DeleteAccountWindowController extends ControllerBase {

    @FXML private ChoiceBox<Account> setAccountName;
    @FXML private Button btnCancel;
    @FXML private Button btnOK;
    
    private int flagHolder;
   
    @Override
    public void initialize(Mediator mediator) {
        
    }
    
    public void setFlagHolder(int flagHolder) {
        this.flagHolder = flagHolder;
    }
    
    public void initDeleteAccountWindow() {
        
        try {
            EntityManager em = getMediator().createEntityManager();
            //List<Account> account = em.createNamedQuery("Account.findAll",Account.class).getResultList();
            TypedQuery<Account> qAccount = em.createQuery("SELECT a FROM Account a JOIN a.holderCollection h WHERE h.id=:pid", Account.class);
            qAccount.setParameter("pid",this.flagHolder);
            List<Account> account = qAccount.getResultList();
            
            this.setAccountName.setItems(FXCollections.observableArrayList(account));
            em.close();
        }
	catch(PersistenceException e) {
            this.btnOK.setDisable(true);
            AlertMessage.processPersistenceException(e);
	}
        
    }
    
    @FXML
    private void handleBtnOK(ActionEvent event) throws IOException {

        Account account = setAccountName.getValue();

        System.out.println(account.getId());

        EntityManager em = getMediator().createEntityManager();

        Account acc = em.find(Account.class, account.getId());
        em.getTransaction().begin();
        em.remove(acc);
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
