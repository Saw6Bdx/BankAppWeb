package jfxui;

import db.home.bank.Bank;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import projetJava.Account;

/**
 *  
 * @author Mary
 */
public class NewAccountWindow_page2Controller extends ControllerBase {
    
    @FXML private TextField txtAgencyName;
    @FXML private TextField txtAgencyCode;
    @FXML private ChoiceBox<Bank> txtBankName;
    @FXML private TextField txtBankCode;
    @FXML private TextField txtAgencyAddressLine1;
    @FXML private TextField txtAgencyAddressLine2;
    @FXML private TextField txtAgencyPostCode;
    @FXML private TextField txtAgencyCity;
    @FXML private Button btnPrevious;
    @FXML private Button btnNext;
  
    private Account account;
    
    public void setAccount(Account account) {
        this.account = account;
    }
            
    
    @Override
    public void initialize(Mediator mediator) {
        
        try {
            EntityManager em = mediator.createEntityManager();
            List<Bank> bankName = em.createNamedQuery("Bank.findAll",Bank.class).getResultList();
            this.txtBankName.setItems(FXCollections.observableArrayList(bankName));
            em.close();
        }
	catch(PersistenceException e) {
            this.btnNext.setDisable(true);
            this.processPersistenceException(e);
	}
        
    }
    
    @FXML
    private void handleBtnNext(ActionEvent event) throws IOException {
    
        String agencyName = txtAgencyName.getText();
        String agencyCode = txtAgencyCode.getText();
        Bank bankName = txtBankName.getValue();
        String bankCode = txtBankCode.getText();
        String agencyAddressLine1 = txtAgencyAddressLine1.getText();
        String agencyAddressLine2 = txtAgencyAddressLine2.getText();
        String agencyPostCode = txtAgencyPostCode.getText();
        String agencyCity = txtAgencyCity.getText();
        
        // Check the fields
        /*if ( Valid.isValidAgencyName(agencyName) ) { // que des lettres
            if ( Valid.isValidAgencyCode(agencyCode) ) { // que des chiffres ?
                if ( Valid.isValidBankCode(bankCode) ) { // que des chiffres ?
                    if ( Valid.isValidAddress(addLine1) ) {
                        if ( Valid.isValidPostCode(postCode) ) {
                            if ( Valid.isValidCity(city) ) {
                            }
                        }
                    }
                }
            }
        }*/
        
        // Going to the next "new account" window
        Scene scene = new Scene(ControllerBase.loadFxml("NewAccountWindow_page3.fxml", new Mediator(Persistence.createEntityManagerFactory("BankAppPU"))));
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
    private void handleBtnPrevious(ActionEvent event) throws IOException {

        // Going to the previous "new account" window
        TitledPane loader = (TitledPane)FXMLLoader.load(getClass().getResource("NewAccountWindow.fxml"));
        Scene scene = new Scene(loader);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        //Close current window
        Stage current = (Stage)btnPrevious.getScene().getWindow();
        current.close();
        
    }
    
    private void processPersistenceException(PersistenceException e) {
        new Alert(Alert.AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
    }
    
}