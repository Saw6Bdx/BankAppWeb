package jfxui;

import db.home.bank.Account;
import db.home.bank.AccountType;
import db.home.bank.Address;
import db.home.bank.Agency;
import db.home.bank.Bank;
import db.home.bank.CountryCode;
import db.home.bank.Postcode;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import utils.AlertMessage;
import utils.Valid;

/**
 *
 * @author Mary
 */
public class NewAccountWindow_page2Controller extends NewAccountWindowController {//ControllerBase {

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

    /*private Account account;
    private AccountType accountType;
    private CountryCode countryCode;

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }*/

    @Override
    public void initialize(Mediator mediator) {

        try {
            EntityManager em = mediator.createEntityManager();
            List<Bank> bankName = em.createNamedQuery("Bank.findAll", Bank.class).getResultList();
            this.txtBankName.setItems(FXCollections.observableArrayList(bankName));
            em.close();
        } catch (PersistenceException e) {
            this.btnNext.setDisable(true);
            AlertMessage.processPersistenceException(e);
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
        if (Valid.isValidOnlyLetters(agencyName)) { // que des lettres + hyphen + apostrophe
            if (Valid.isValidOnlyNumber(agencyCode)) { // que des chiffres 
                if (Valid.isValidOnlyNumber(bankCode)) { // que des chiffres 
                    if (Valid.isValidAddress(agencyAddressLine1)) {
                        if (Valid.isValidPostCode(agencyPostCode)) {
                            if (Valid.isValidOnlyLetters(agencyCity)) {

                                // Temporary back-up 
                                System.out.println(getAccountType().getType());
                                
                                // ... table AGENCY
                                Agency agencyObj = new Agency();
                                agencyObj.setAgencyCode(agencyCode);
                                agencyObj.setAgencyName(agencyName);
                                
                                // ... table BANK
                                Bank bankObj = new Bank();
                                bankObj.setBankCode(bankCode);
                                bankObj.setName(bankName.toString());
                                
                                // ... table ADDRESS
                                Address addressObj = new Address();
                                addressObj.setLine1(agencyAddressLine1);
                                if (!agencyAddressLine2.isEmpty()) {
                                    addressObj.setLine2(agencyAddressLine2);
                                }
                                
                                // ... table POSTCODE
                                Postcode postCodeObj = new Postcode();
                                postCodeObj.setCity(agencyCity);
                                postCodeObj.setPostcode(Integer.parseInt(agencyPostCode));
                                
                                // Going to the next "new account" window
                                /*NewAccountWindow_page3Controller controller = (NewAccountWindow_page3Controller) ControllerBase.loadFxml(
                                        "NewAccountWindow_page3.fxml",
                                        new Mediator(Persistence.createEntityManagerFactory("BankAppPU"))
                                );*/
                                NewAccountWindow_page3Controller controller = (NewAccountWindow_page3Controller)ControllerBase.loadFxml("NewAccountWindow_page3.fxml");
                                controller.setAddress(addressObj);
                                controller.setBank(bankObj);
                                controller.setPostcode(postCodeObj);
                                controller.setAgency(agencyObj);
                                /*controller.setAccount(this.account);
                                controller.setAccountType(this.accountType);
                                controller.setCountryCode(this.countryCode);*/
                                        
                                Scene scene = new Scene(controller.getParent());
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();

                                //Close current window
                                Stage current = (Stage) btnNext.getScene().getWindow();
                                current.close();

                            }
                            else {
                                AlertMessage.alertMessage("agency city", "Only letters, hyphen and apostroph allowed");
                            }
                        }
                        else {
                            AlertMessage.alertMessage("post code", "Only numbers allowed");
                        }
                    }
                    else {
                        AlertMessage.alertMessage("agency adress", "Cannot be empty");
                    }
                }
                else {
                    AlertMessage.alertMessage("bank code", "Only numbers");
                }
            }
            else {
                AlertMessage.alertMessage("agency code", "Only numbers");
            }
        }
        else {
            AlertMessage.alertMessage("agency name", "Only letters, hyphen and postroph allowed");
        }

    }

    
    @FXML
    private void handleBtnPrevious(ActionEvent event) throws IOException {

        // Going to the previous "new account" window
        TitledPane loader = (TitledPane) FXMLLoader.load(getClass().getResource("NewAccountWindow_page1.fxml"));
        Scene scene = new Scene(loader);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        //Close current window
        Stage current = (Stage) btnPrevious.getScene().getWindow();
        current.close();

    }

}
