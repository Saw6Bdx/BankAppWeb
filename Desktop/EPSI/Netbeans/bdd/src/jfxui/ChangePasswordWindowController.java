/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import db.home.bank.Holder;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import utils.AlertMessage;
import static utils.Password.get_SHA_512_SecurePassword;
import utils.Valid;

/**
 * Class which allow the current user to change its password.
 *
 * @author Mary
 */
public class ChangePasswordWindowController extends ControllerBase {

    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtOldPwd;
    @FXML
    private PasswordField txtNewPwd;
    @FXML
    private PasswordField txtConfirmPwd;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnOK;

    @Override
    public void initialize(Mediator mediator) {
    }

    /**
     * Method binded to the action on the OK button. It gets the values informed
     * in the text field (login and password), and check if they are correct.
     * There is a limited numbers of attempt.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleBtnOK(ActionEvent event) throws IOException {

        String login = this.txtLogin.getText();
        String oldPwd = this.txtOldPwd.getText();
        String newPwd = this.txtNewPwd.getText();
        String confirmPwd = this.txtConfirmPwd.getText();

        try {
            EntityManager em = getMediator().createEntityManager();

            em.getTransaction().begin();
            TypedQuery<Holder> qHolder = em.createQuery("SELECT a FROM Holder a", Holder.class);
            List<Holder> holderList = qHolder.getResultList();

            // Checking if the login exists in the db
            Holder holder = new Holder();
            int holderId = 0, holderIndex = 0, nbTry = 0, nbTryMax = 3;
            while (holderId == 0 && nbTry < nbTryMax) {
                for (int i = 0; i < holderList.size(); i++) {
                    holder = holderList.get(i);
                    if (login.equals(holder.getLogin())) {
                        holderId = holder.getId();
                        holderIndex = i;
                    }
                }
                if (holderId == 0 && nbTry < nbTryMax) {
                    AlertMessage.alertMessage("Login", "Does not exist");
                }
            }
            if (nbTry == nbTryMax) {
                AlertMessage.alertMessage("Login", "No more attempts");
                //Close current window
                Stage current = (Stage) this.btnOK.getScene().getWindow();
                current.close();
            }

            // Check if old pwd is identical as the one stored in the database
            if (get_SHA_512_SecurePassword(oldPwd, "1").equals(holderList.get(holderIndex).getPassword())) {

                // Check if the new pwd and its confirmation is identical
                if (Valid.isValidPwd(newPwd, confirmPwd)) {

                    // Set the pwd in the db
                    Query q = em.createQuery("UPDATE Holder t SET t.password=:ppwd WHERE t.id=:pid");
                    q.setParameter("ppwd", get_SHA_512_SecurePassword(newPwd, "1"));
                    q.setParameter("pid", holderId);
                    q.executeUpdate();

                } else {
                    AlertMessage.alertMessage("password", "password and its confirmation do not match");
                }
            } else {
                AlertMessage.alertMessage("Old password", "No identical with the one stored");
            }

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
