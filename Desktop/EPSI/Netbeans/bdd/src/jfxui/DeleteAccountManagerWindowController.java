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
import javax.persistence.TypedQuery;
import utils.AlertMessage;

/**
 * Class which allowed the user to delete one account manager. It selects in the
 * database the account managers.
 *
 * @author Mary
 */
public class DeleteAccountManagerWindowController extends ControllerBase {

    @FXML
    private ChoiceBox<AccountManager> setAccountManagerName;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnOK;

    private int flagHolder;
    
    public void setFlagHolder(int flagHolder) {
        this.flagHolder = flagHolder;
    }
    
    public void initDeleteAccountManagerWindow() {

        try {
            EntityManager em = getMediator().createEntityManager();
            TypedQuery<AccountManager> qAccountManager = em.createQuery("SELECT am FROM AccountManager am JOIN am.idAgency a JOIN a.accountCollection acc JOIN acc.holderCollection h WHERE h.id=:pid", AccountManager.class);
            qAccountManager.setParameter("pid", this.flagHolder);
            List<AccountManager> accountManager = qAccountManager.getResultList();
            
            this.setAccountManagerName.setItems(FXCollections.observableArrayList(accountManager));
            em.close();
        } catch (PersistenceException e) {
            this.btnOK.setDisable(true);
            AlertMessage.processPersistenceException(e);
        }

    }
    
    @Override
    public void initialize(Mediator mediator) {

    }
    

    @FXML
    private void handleBtnOK(ActionEvent event) throws IOException {

        AccountManager accountManager = this.setAccountManagerName.getValue();

        try {
            EntityManager em = getMediator().createEntityManager();

            AccountManager am = em.find(AccountManager.class, accountManager.getId());
            em.getTransaction().begin();
            em.remove(am);
            em.getTransaction().commit();
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
