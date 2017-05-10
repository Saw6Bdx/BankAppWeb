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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import utils.AlertMessage;
import utils.Valid;

/**
 * Class which allowed the user to create a new account, page 2 of the form.
 * This herits from NewAccountWindowController
 *
 * @author Mary
 */
public class NewAccountWindow_page2Controller extends NewAccountWindowController {

    @FXML
    private TextField txtAgencyName;
    @FXML
    private TextField txtAgencyCode;
    @FXML
    private ChoiceBox<Bank> txtBankName;
    @FXML
    private CheckBox checkValue;
    @FXML
    private TextField setBankName;
    @FXML
    private TextField txtBankCode;
    @FXML
    private TextField txtAgencyAddressLine1;
    @FXML
    private TextField txtAgencyAddressLine2;
    @FXML
    private TextField txtAgencyPostCode;
    @FXML
    private TextField txtAgencyCity;
    @FXML
    private Button btnPrevious;
    @FXML
    private Button btnNext;

    private boolean flagSetBankName;

    @Override
    public void initialize(Mediator mediator) {

        this.flagSetBankName = true;
        this.setBankName.setDisable(this.flagSetBankName);

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
    private void handleCheckBox(ActionEvent event) throws IOException {
        this.flagSetBankName = (this.flagSetBankName == true ? false : true);
        this.setBankName.setDisable(this.flagSetBankName);
    }

    @FXML
    private void handleBtnNext(ActionEvent event) throws IOException {

        String agencyName = this.txtAgencyName.getText();
        String agencyCode = this.txtAgencyCode.getText();
        String bankName_set = "";
        Bank bankName_choice = new Bank();
        if (this.checkValue.isSelected()) {
            bankName_set = this.setBankName.getText();
        } else {
            bankName_choice = this.txtBankName.getValue();
        }
        String bankCode = this.txtBankCode.getText();
        String agencyAddressLine1 = this.txtAgencyAddressLine1.getText();
        String agencyAddressLine2 = this.txtAgencyAddressLine2.getText();
        String agencyPostCode = this.txtAgencyPostCode.getText();
        String agencyCity = this.txtAgencyCity.getText();

        // Check the fields
        if (Valid.isValidOnlyLetters(agencyName)) { // only lettres + hyphen + apostrophe
            if (Valid.isValidOnlyNumber(agencyCode) && agencyCode.length() == 5) {
                if (Valid.isValidOnlyNumber(bankCode)) {
                    if (Valid.isValidAddress(agencyAddressLine1)) {
                        if (Valid.isValidPostCode(agencyPostCode)) {
                            if (Valid.isValidOnlyLetters(agencyCity)) {

                                // Temporary back-up 
                                // ... table AGENCY
                                Agency agencyObj = new Agency();
                                agencyObj.setAgencyCode(agencyCode);
                                agencyObj.setAgencyName(agencyName);

                                // ... table BANK
                                Bank bankObj = new Bank();
                                bankObj.setBankCode(bankCode);
                                if (this.checkValue.isSelected()) {
                                    bankObj.setName(bankName_set);
                                } else {
                                    bankObj.setName(bankName_choice.toString());
                                }

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
                                NewAccountWindow_page3Controller controller = (NewAccountWindow_page3Controller) ControllerBase.loadFxml(
                                        "NewAccountWindow_page3.fxml",
                                        getMediator()
                                );

                                // Transfer informations of Controller 1
                                AccountType accountTypeObj = new AccountType(null);
                                accountTypeObj.setType(getAccountType().getType());
                                controller.setAccountType(accountTypeObj);

                                Account accountObj = new Account(null, getAccount().getNumber(), getAccount().getCreationDate(),
                                        getAccount().getFirstBalance(), getAccount().getOverdraft());
                                if (getAccount().getInterestRate() != null) {
                                    accountObj.setInterestRate(getAccount().getInterestRate());
                                }
                                if (getAccount().getDescription() != null) {
                                    accountObj.setDescription(getAccount().getDescription());
                                }
                                accountObj.setIdAccountType(accountTypeObj);
                                controller.setAccount(accountObj);

                                controller.setCountryCode(new CountryCode(null, getCountryCode().getCode()));

                                // Saving informations of Controller 2
                                controller.setAddress(addressObj);
                                controller.setBank(bankObj);
                                controller.setPostcode(postCodeObj);
                                controller.setAgency(agencyObj);
                                controller.setFlagHolder(getFlagHolder());

                                Scene scene = new Scene(controller.getParent());
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();

                                //Close current window
                                Stage current = (Stage) this.btnNext.getScene().getWindow();
                                current.close();

                            } else {
                                AlertMessage.alertMessage("agency city", "Only letters, hyphen and apostroph allowed");
                            }
                        } else {
                            AlertMessage.alertMessage("post code", "Only numbers allowed");
                        }
                    } else {
                        AlertMessage.alertMessage("agency adress", "Cannot be empty");
                    }
                } else {
                    AlertMessage.alertMessage("bank code", "Only numbers");
                }
            } else {
                AlertMessage.alertMessage("agency code", "Only numbers");
            }
        } else {
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
        Stage current = (Stage) this.btnPrevious.getScene().getWindow();
        current.close();

    }

}
