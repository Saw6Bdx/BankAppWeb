/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import db.home.bank.Account;
import db.home.bank.Transactions;
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
 * Class which allowed the user to delete one transaction. It selects in the
 * database the transactions that the user has created.
 *
 * @author Mary
 */
public class DeleteTransactionsWindowController extends ControllerBase {

    @FXML
    private ChoiceBox<Account> setAccountName;
    @FXML
    private ChoiceBox<Transactions> setTransactionsName;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnOK;

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
            TypedQuery<Account> qAccount = em.createQuery("SELECT a FROM Account a JOIN a.holderCollection h WHERE h.id=:pid", Account.class);
            qAccount.setParameter("pid", this.flagHolder);
            List<Account> account = qAccount.getResultList();

            this.setAccountName.setItems(FXCollections.observableArrayList(account));
            em.close();
        } catch (PersistenceException e) {
            this.btnOK.setDisable(true);
            AlertMessage.processPersistenceException(e);
        }

    }

    @FXML
    private void handleChoiceBox(ActionEvent event) throws IOException {

        int flagAccount = this.setAccountName.getValue().getId();

        try {
            EntityManager em = getMediator().createEntityManager();
            TypedQuery<Transactions> qTransactions = em.createQuery("SELECT a FROM Transactions a WHERE a.idAccount.id=:pacc", Transactions.class);
            qTransactions.setParameter("pacc", flagAccount);
            List<Transactions> transactions = qTransactions.getResultList();

            this.setTransactionsName.setItems(FXCollections.observableArrayList(transactions));
            em.close();
        } catch (PersistenceException e) {
            this.btnOK.setDisable(true);
            AlertMessage.processPersistenceException(e);
        }
    }

    @FXML
    private void handleBtnOK(ActionEvent event) throws IOException {

        Transactions transactions = this.setTransactionsName.getValue();

        try {
            EntityManager em = getMediator().createEntityManager();

            Transactions trans = em.find(Transactions.class, transactions.getId());
            em.getTransaction().begin();
            em.remove(trans);
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
