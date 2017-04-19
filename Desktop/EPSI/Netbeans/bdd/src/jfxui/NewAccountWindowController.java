package jfxui;

import db.home.bank.AccountType;
import db.home.bank.CountryCode;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

/**
 *  
 * @author Mary
 */
public class NewAccountWindowController extends ControllerBase {
    
    @FXML private TextField txtAccountNumber;
    @FXML private DatePicker txtAccountCreationDate;
    @FXML private TextField txtAccountBalance;
    @FXML private TextField txtAccountOverdraft;
    @FXML private TextField txtAccountDescription;
    @FXML private TextField txtAccountInterestRate;
    @FXML private ChoiceBox<AccountType> txtAccountType;
    @FXML private ChoiceBox<CountryCode> txtAccountCountryCode;
    @FXML private Button btnCancel;
    @FXML private Button btnNext;
    
   
    @Override
    public void initialize(Mediator mediator) {
        
        try {
            EntityManager em = mediator.createEntityManager();
            List<AccountType> accountType = em.createNamedQuery("AccountType.findAll",AccountType.class).getResultList();

            try {
                List<CountryCode> countryCode = em.createNamedQuery("CountryCode.findAll",CountryCode.class).getResultList();
                this.txtAccountCountryCode.setItems(FXCollections.observableArrayList(countryCode));
            }
            catch(PersistenceException e) {
                this.btnNext.setDisable(true);
                this.processPersistenceException(e);
            }
                        
            this.txtAccountType.setItems(FXCollections.observableArrayList(accountType));
            em.close();
        }
	catch(PersistenceException e) {
            this.btnNext.setDisable(true);
            this.processPersistenceException(e);
	}
        
    }
    
    @FXML
    private void handleBtnNext(ActionEvent event) throws IOException {
    
        String accountNumber = txtAccountNumber.getText();
        String accountCreationDate = txtAccountCreationDate.getEditor().getText();
        String accountBalance = txtAccountBalance.getText();
        String accountOverdraft = txtAccountOverdraft.getText();
        String accountDescription = txtAccountDescription.getText();
        String accountInterestRate = txtAccountInterestRate.getText();
        AccountType accountType = txtAccountType.getValue();
        CountryCode accountCountryCode = txtAccountCountryCode.getValue();
        
        // Check the fields
        /*if ( Valid.isValidAccountNumber(accountNumber) ) { // que des chiffres
            if ( Valid.isValidAccountCreationDate(accountCreationDate) ) { // date antérieure à la date d'aujourd'hui
                if ( Valid.isValidAccountBalance(accountBalance) ) { // double
                    if ( Valid.isValidAccountOverdraft(accountOverdraft) ) { // double
                        if ( Valid.isValidAccountInterestRate(accountInterestRate) ) { // double + intervalle



                        }
                    }
                }
            }
        }*/
         account.setAccountNumber();
        // Going to the next "new account" window
        NewAccountWindow_page2Controller controller = (NewAccountWindow_page2Controller)ControllerBase.loadFxml(
                "NewAccountWindow_page2.fxml", 
                new Mediator(Persistence.createEntityManagerFactory("BankAppPU"))
        );
        controller.setAccount(account);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        //Close current window
        Stage current = (Stage)btnNext.getScene().getWindow();
        current.close();
       
    }
    
    
    private void alertMessage(String field, String message) {
        new Alert(
                Alert.AlertType.WARNING,
                String.format("Invalid %s format\n %s",field,message)
            ).showAndWait();
    }
    
   
    
    @FXML
    private void handleBtnCancel(ActionEvent event) throws IOException {

        //Close current window
        Stage current = (Stage)btnCancel.getScene().getWindow();
        current.close();
        
    }
    
    private void processPersistenceException(PersistenceException e) {
        new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
    }
    
}