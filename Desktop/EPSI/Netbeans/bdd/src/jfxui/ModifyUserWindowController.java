/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import db.home.bank.Account;
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
import javax.persistence.TypedQuery;
import utils.AlertMessage;
import static utils.DateUtils.LocalDate2Date;
import utils.Valid;

/**
 * Class which allowed the user to modify its profile. It writes in the database
 * the new information.
 *
 * @author Mary
 */
public class ModifyUserWindowController extends ControllerBase {

    @FXML
    private TextField txtHolderName;
    @FXML
    private TextField txtHolderFirstName;
    @FXML
    private TextField txtHolderPhone;
    @FXML
    private TextField txtHolderEmail;
    @FXML
    private DatePicker txtHolderBirthday;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnOK;
    @FXML
    private ChoiceBox<Holder> setHolderName;
    
    public int flagHolder;
    
    public void setFlagHolder(int flagHolder) {
        this.flagHolder = flagHolder;
    }

    @Override
    public void initialize(Mediator mediator) {

    }
    
    public void initModifyUserProfileWindow() {
        try {
            EntityManager em = getMediator().createEntityManager();
            
            TypedQuery<Holder> qHolder = em.createQuery("SELECT h FROM Holder h WHERE h.id=:pid", Holder.class);
            qHolder.setParameter("pid", this.flagHolder);
            List<Holder> holder = qHolder.getResultList();

            this.setHolderName.setItems(FXCollections.observableArrayList(holder));
            em.close();
        } catch (PersistenceException e) {
            this.btnOK.setDisable(true);
            AlertMessage.processPersistenceException(e);
        }
    }

    @FXML
    private void handleBtnOK(ActionEvent event) throws IOException {

        Holder holder = this.setHolderName.getValue();
        String holderName = this.txtHolderName.getText();
        String holderFirstName = this.txtHolderFirstName.getText();
        String holderPhone = this.txtHolderPhone.getText();
        String holderEmail = this.txtHolderEmail.getText();
        Date holderBirthday = LocalDate2Date(this.txtHolderBirthday.getValue());

        try {

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
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();

    }

}
