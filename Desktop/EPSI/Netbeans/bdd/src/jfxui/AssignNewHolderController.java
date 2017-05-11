/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import db.home.bank.Account;
import db.home.bank.Holder;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import utils.AlertMessage;
import static utils.Password.get_SHA_512_SecurePassword;

/**
 * FXML Controller class
 *
 * @author Charlotte
 */
public class AssignNewHolderController extends ControllerBase {

    @FXML private ChoiceBox<Account> choiceAccount;
    @FXML private TextField labelLogin, newHolderName, newHolderFirstname;
    @FXML private PasswordField labelPassword;
    @FXML private Button btnCancel, btnAssign;

    private int flagHolder, flagAccount;

    @Override
    public void initialize(Mediator mediator) {
        // empty initialization
    }

    public void setFlagHolder(int flagHolder) {
        this.flagHolder = flagHolder;
    }
    
    public void setFlagAccount(int flagAccount) {
        this.flagAccount = flagAccount;
    }

    public void initAssignNewHolder() {
        try {
            EntityManager em = getMediator().createEntityManager();
            // creation of the accountList of the current holder
            TypedQuery<Account> qAccount = em.createQuery("SELECT a FROM Account a JOIN a.holderCollection h WHERE h.id=:holder", Account.class);
            qAccount.setParameter("holder", this.flagHolder);
            List<Account> accountList = qAccount.getResultList();
            this.choiceAccount.setItems(FXCollections.observableArrayList(accountList));
            // selection of the current account if already open
            if(this.flagAccount != 0){
                TypedQuery<Account> qFlagAccount = em.createQuery("SELECT a FROM Account a WHERE a.id=:flagAccount", Account.class);
                qFlagAccount.setParameter("flagAccount", this.flagAccount);
                this.choiceAccount.setValue(qFlagAccount.getSingleResult());
            }
            // help "Select an account..."
            this.choiceAccount.setTooltip(new Tooltip("Select an account..."));
            em.close();
        } catch (PersistenceException e) {
            this.btnAssign.setDisable(true);
            AlertMessage.processPersistenceException(e);
        }
    }

    @FXML
    private void handleBtnCancel(ActionEvent event) throws IOException {
        //Close current window
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();
    }

    @FXML
    private void handleBtnAssign(ActionEvent event) throws IOException {
        EntityManager em = getMediator().createEntityManager();
        
        // Check Holder login and password
        TypedQuery<Holder> qHolder = em.createQuery("SELECT h FROM Holder h WHERE h.id=:holder", Holder.class);
        List<Holder> holderList = qHolder.setParameter("holder", this.flagHolder).getResultList();
        String myLogin = holderList.get(0).getLogin();
        String myPassword = holderList.get(0).getPassword();
        if(myLogin.equals(this.labelLogin.getText()) & myPassword.equals(get_SHA_512_SecurePassword(this.labelPassword.getText(),"1"))) {
            // Find the new holder in db
            TypedQuery<Holder> qNewHolder = em.createQuery("SELECT h FROM Holder h WHERE h.name=:newHolderName AND h.firstname=:newHolderFirstname", Holder.class);
            List<Holder> newHolderList = qNewHolder.setParameter("newHolderName", this.newHolderName.getText()).setParameter("newHolderFirstname", this.newHolderFirstname.getText()).getResultList();            
            // Check if the new holder already exists in db
            if(newHolderList.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "This holder doesn't exist. Please, correct the name and/or firstname or create a new holder.").showAndWait();
            }
            else {
                // Get the Account to assign the new holder
                Account account = this.choiceAccount.getValue();
                Holder newHolder = newHolderList.get(0);
                // ... table ASSIGN (in Holder and Account classes)
                //Collection<Holder> collHolder = new HashSet();
                Collection<Holder> collHolder = account.getHolderCollection();
                collHolder.add(newHolder);
                account.setHolderCollection(collHolder);

                //Collection<Account> collAccount = new HashSet();
                Collection<Account> collAccount = newHolder.getAccountCollection();
                collAccount.add(account);
                newHolder.setAccountCollection(collAccount);
                
                // ...to database
                em.getTransaction().begin();
                em.merge(account);
                em.merge(newHolder);
                em.getTransaction().commit();
                
                new Alert(Alert.AlertType.INFORMATION, String.format("The holder %s can now access to your account %s", newHolder, account)).showAndWait();
                Stage current = (Stage) btnAssign.getScene().getWindow();
                current.close();
            }
        }
        else {
            new Alert(Alert.AlertType.ERROR, "You login and/or password are invalid.").showAndWait();
        }
    }
}