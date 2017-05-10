package jfxui;

import db.home.bank.Recipient;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import utils.Valid;
import utils.AlertMessage;

/**
 *
 * @author Nicolas
 */
public class NewRecipientWindowController extends ControllerBase {

    @FXML private TextField txtName;
    @FXML private TextField txtIban;
    @FXML private Button btnCreate;
    @FXML private Button btnCancel;

    @Override
    public void initialize(Mediator mediator) {

    }

    @FXML
    private void handleBtnCreate(ActionEvent event) throws IOException {

        String name = txtName.getText();
        String iban = txtIban.getText();

        // Check the fields
        if (Valid.isValidOnlyLetters(name)) {
            if (Valid.isValidLettersNumbers(iban)) {

                // All the fields are correct, then it is possible to create objects
                Recipient recipient = new Recipient(null, name, iban);
                
                // Writing info into the database
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("BankAppPU");
                EntityManager em = emf.createEntityManager();

                em.getTransaction().begin();
                recipient.setName(name);
                recipient.setIban(iban);
                em.persist(recipient);
                em.getTransaction().commit();

                //Close current window
                Stage current = (Stage) btnCreate.getScene().getWindow();
                current.close();

            } else {
                AlertMessage.alertMessage("iban", "Only letters and numbers allowed");
            }
        } else {
            AlertMessage.alertMessage("name", "Only letters, hyphen and apostrophe allowed");
        }

    }

    @FXML
    /**
     * When the user clicks on the Cancel button, the application goes back to
     * the App Window and closes the New Recipient Window.
     */
    private void handleBtnCancel(ActionEvent event) throws IOException {

        //Close current window
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();
    }

}