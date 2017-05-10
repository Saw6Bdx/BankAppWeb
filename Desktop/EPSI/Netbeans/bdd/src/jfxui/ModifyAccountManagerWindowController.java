/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import db.home.bank.AccountManager;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import utils.AlertMessage;
import static utils.DateUtils.LocalDate2Date;
import utils.Valid;

/**
 * Class which allowed the user to modify the information of one account manager. 
 * It selects in the database the account managers that the user has created.
 *
 * @author Mary
 */
public class ModifyAccountManagerWindowController extends ControllerBase {

    @FXML
    private TextField txtAccountManagerName;
    @FXML
    private TextField txtAccountManagerFirstName;
    @FXML
    private TextField txtAccountManagerPhone;
    @FXML
    private TextField txtAccountManagerEmail;
    @FXML
    private DatePicker txtAccountManagerAssignementDate;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnOK;
    @FXML
    private ChoiceBox<AccountManager> setAccountManagerName;

    int flagHolder;
    
    public void setFlagHolder(int flagHolder) {
        this.flagHolder = flagHolder;
    }
    
    public void initModifyAccountManagerWindow() {

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

        String accountManagerName = this.txtAccountManagerName.getText();
        String accountManagerFirstName = this.txtAccountManagerFirstName.getText();
        String accountManagerPhone = this.txtAccountManagerPhone.getText();
        String accountManagerEmail = this.txtAccountManagerEmail.getText();
        Date accountManagerAssignementDate = LocalDate2Date(this.txtAccountManagerAssignementDate.getValue());

        try {
            EntityManager em = getMediator().createEntityManager();

            em.getTransaction().begin();

            if (Valid.isValidOnlyLetters(accountManagerName)) {
                Query q = em.createQuery("UPDATE AccountManager t SET t.name=:pname WHERE t.id=:pid");
                q.setParameter("pname", accountManagerName);
                q.setParameter("pid", accountManager.getId());
                q.executeUpdate();
            }

            if (Valid.isValidOnlyLetters(accountManagerFirstName)) {
                Query q = em.createQuery("UPDATE AccountManager t SET t.firstName=:pfirstname WHERE t.id=:pid");
                q.setParameter("pfirstname", accountManagerFirstName);
                q.setParameter("pid", accountManager.getId());
                q.executeUpdate();
            }

            if (Valid.isValidDateNoFuture(accountManagerAssignementDate)) {
                Query q = em.createQuery("UPDATE AccountManager t SET t.assignementDate=:passignementDate WHERE t.id=:pid");
                q.setParameter("passignementDate", accountManagerAssignementDate);
                q.setParameter("pid", accountManager.getId());
                q.executeUpdate();
            }

            if (Valid.isValidPhoneNumber(accountManagerPhone)) {
                Query q = em.createQuery("UPDATE AccountManager t SET t.phone=:pphone WHERE t.id=:pid");
                q.setParameter("pphone", accountManagerPhone);
                q.setParameter("pid", accountManager.getId());
                q.executeUpdate();
            }

            if (Valid.isValidEmail(accountManagerEmail)) {
                Query q = em.createQuery("UPDATE AccountManager t SET t.email=:pemail WHERE t.id=:pid");
                q.setParameter("pemail", accountManagerEmail);
                q.setParameter("pid", accountManager.getId());
                q.executeUpdate();
            }

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
