/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import db.home.bank.Holder;
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
public class ModifyUserWindowController extends ControllerBase {
    
    @FXML private TextField txtHolderName;
    @FXML private TextField txtHolderFirstName;
    @FXML private TextField txtHolderPhone;
    @FXML private TextField txtHolderEmail;
    @FXML private DatePicker txtHolderBirthday;
    @FXML private Button btnCancel;
    @FXML private Button btnOK;
    @FXML private ChoiceBox<Holder> setHolderName;
   
    
    @Override
    public void initialize(Mediator mediator) {
        
        try {
            EntityManager em = mediator.createEntityManager();
            List<Holder> holder = em.createNamedQuery("Holder.findAll",Holder.class).getResultList();
            
            this.setHolderName.setItems(FXCollections.observableArrayList(holder));
            em.close();
        }
	catch(PersistenceException e) {
            this.btnOK.setDisable(true);
            AlertMessage.processPersistenceException(e);
	}
        
    }
    
    @FXML
    private void handleBtnOK(ActionEvent event) throws IOException {

        Holder holder = setHolderName.getValue();
        System.out.println(txtHolderName.getText());
        String holderName = txtHolderName.getText();
        String holderFirstName = txtHolderFirstName.getText();
        String holderPhone = txtHolderPhone.getText();
        String holderEmail = txtHolderEmail.getText();
        Date holderBirthday = LocalDate2Date(txtHolderBirthday.getValue());

        System.out.println(holder.getId());

        EntityManager em = getMediator().createEntityManager();

        em.getTransaction().begin();

        if (Valid.isValidOnlyLetters(holderName)) {
            Query q = em.createQuery("UPDATE Holder t SET t.name=:pname WHERE t.id=:pid");
            q.setParameter("pname", holderName);
            q.setParameter("pid", holder.getId());
            q.executeUpdate();
        }

        if (Valid.isValidOnlyLetters(holderFirstName)) {
            Query q = em.createQuery("UPDATE Holder t SET t.firstname=:pfirstname WHERE t.id=:pid");
            q.setParameter("pfirstname", holderFirstName);
            q.setParameter("pid", holder.getId());
            q.executeUpdate();
        }

        if (Valid.isValidPhoneNumber(holderPhone)) {
            Query q = em.createQuery("UPDATE Holder t SET t.phone=:pphone WHERE t.id=:pid");
            q.setParameter("pphone", holderPhone);
            q.setParameter("pid", holder.getId());
            q.executeUpdate();
        }

        /*if (Valid.isValidEmail(holderEmail)) {
            Query q = em.createQuery("UPDATE Holder t SET t.=:pemail WHERE t.id=:pid");
            q.setParameter("pemail", holderEmail);
            q.setParameter("pid", holder.getId());
            q.executeUpdate();
        }*/
        
        /*if (Valid.isValidDateNoFuture(holderBirthday)) {
            Query q = em.createQuery("UPDATE Holder t SET t.birthday=:pbirthday WHERE t.id=:pid");
            q.setParameter("pbirthday", holderBirthday);
            q.setParameter("pid", holder.getId());
            q.executeUpdate();
        }*/

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
