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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import utils.AlertMessage;

/**
 * Class which allowed the user to delete one account. It selects in the
 * database the accounts of the current user.
 *
 * @author Mary
 */
public class DeleteAccountWindowController extends ControllerBase {

    @FXML
    private ChoiceBox<Account> setAccountName;
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
    private void handleBtnOK(ActionEvent event) throws IOException {

        Account account = this.setAccountName.getValue();

        try {
            EntityManager em = getMediator().createEntityManager();

            Account acc = em.find(Account.class, account.getId());
            em.getTransaction().begin();
            em.remove(acc);
            em.getTransaction().commit();

            em.close();
        } catch (PersistenceException e) {
            AlertMessage.processPersistenceException(e);
        }

        //Close current window
        Stage current = (Stage) this.btnOK.getScene().getWindow();
        current.close();

        refreshApp();

    }

    @FXML
    private void handleBtnCancel(ActionEvent event) throws IOException {

        //Close current window
        Stage current = (Stage) this.btnCancel.getScene().getWindow();
        current.close();
        
        refreshApp();

    }
    
    private void refreshApp() throws IOException {
        //Refresh the app
        AppWindowController controller = (AppWindowController) ControllerBase.loadFxml(
                "AppWindow.fxml",
                getMediator()
        );
        controller.setFlagHolder(this.flagHolder);
        controller.initAppWindowController(getMediator());
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}
