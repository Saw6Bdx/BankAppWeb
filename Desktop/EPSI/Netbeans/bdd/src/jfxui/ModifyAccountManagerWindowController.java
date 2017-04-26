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
import utils.AlertMessage;
import static utils.DateUtils.LocalDate2Date;
import utils.Valid;

/**
 *
 * @author Mary
 */
public class ModifyAccountManagerWindowController extends ControllerBase {
    
    @FXML private TextField txtAccountManagerName;
    @FXML private TextField txtAccountManagerFirstName;
    @FXML private TextField txtAccountManagerPhone;
    @FXML private TextField txtAccountManagerEmail;
    @FXML private DatePicker txtAccountManagerAssignementDate;
    @FXML private Button btnCancel;
    @FXML private Button btnOK;
    @FXML private ChoiceBox<AccountManager> setAccountManagerName;
   
    
    @Override
    public void initialize(Mediator mediator) {
        
        try {
            EntityManager em = mediator.createEntityManager();
            List<AccountManager> accountManager = em.createNamedQuery("AccountManager.findAll",AccountManager.class).getResultList();
            
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

        String accountManagerName = txtAccountManagerName.getText();
        String accountManagerFirstName = txtAccountManagerFirstName.getText();
        String accountManagerPhone = txtAccountManagerPhone.getText();
        String accountManagerEmail = txtAccountManagerEmail.getText();
        Date accountManagerAssignementDate = LocalDate2Date(txtAccountManagerAssignementDate.getValue());

        System.out.println(accountManager.getId());

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
