package jfxui;

import db.home.bank.Account;
import db.home.bank.AccountType;
import db.home.bank.CountryCode;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import utils.AlertMessage;
import static utils.DateUtils.LocalDate2Date;
import utils.Valid;

/**
 * Class which allowed the user to create a new account, page 1 of the form.
 * This herits from NewAccountWindowController
 *
 * @author Mary
 */
public class NewAccountWindow_page1Controller extends NewAccountWindowController {

    @FXML
    private TextField txtAccountNumber;
    @FXML
    private DatePicker txtAccountCreationDate;
    @FXML
    private TextField txtAccountBalance;
    @FXML
    private TextField txtAccountOverdraft;
    @FXML
    private TextField txtAccountDescription;
    @FXML
    private TextField txtAccountInterestRate;
    @FXML
    private ChoiceBox<AccountType> txtAccountType;
    @FXML
    private ChoiceBox<CountryCode> txtAccountCountryCode;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnNext;

    @Override
    public void initialize(Mediator mediator) {

        try {
            EntityManager em = mediator.createEntityManager();
            List<AccountType> accountType = em.createNamedQuery("AccountType.findAll", AccountType.class).getResultList();

            try {
                List<CountryCode> countryCode = em.createNamedQuery("CountryCode.findAll", CountryCode.class).getResultList();
                this.txtAccountCountryCode.setItems(FXCollections.observableArrayList(countryCode));
            } catch (PersistenceException e) {
                this.btnNext.setDisable(true);
                AlertMessage.processPersistenceException(e);
            }

            this.txtAccountType.setItems(FXCollections.observableArrayList(accountType));
            em.close();
        } catch (PersistenceException e) {
            this.btnNext.setDisable(true);
            AlertMessage.processPersistenceException(e);
        }

    }

    @FXML
    private void handleBtnNext(ActionEvent event) throws IOException {

        String accountNumber = this.txtAccountNumber.getText();
        Date accountCreationDate = LocalDate2Date(this.txtAccountCreationDate.getValue());
        String accountBalance = this.txtAccountBalance.getText();
        String accountOverdraft = this.txtAccountOverdraft.getText();
        String accountDescription = this.txtAccountDescription.getText();
        String accountInterestRate = this.txtAccountInterestRate.getText();
        AccountType accountType = this.txtAccountType.getValue();
        CountryCode accountCountryCode = this.txtAccountCountryCode.getValue();

        // Check the fields
        if (Valid.isValidOnlyNumber(accountNumber)) {
            if (Valid.isValidDateNoFuture(accountCreationDate)) {
                if (Valid.isValidDouble(accountBalance)) {
                    if (Valid.isValidDouble(accountOverdraft)) {

                        // Saving informations ... 
                        // ... table ACCOUNT
                        Account accountObj = new Account();
                        accountObj.setNumber(accountNumber);
                        accountObj.setCreationDate(accountCreationDate);
                        accountObj.setFirstBalance(Double.parseDouble(accountBalance));
                        accountObj.setOverdraft(Double.parseDouble(accountOverdraft));
                        if (!accountInterestRate.isEmpty() && Valid.isValidDouble(accountInterestRate)) {
                            accountObj.setInterestRate(Double.parseDouble(accountInterestRate));
                        }
                        if (!accountDescription.isEmpty()) {
                            accountObj.setDescription(accountDescription);
                        }

                        // ... table ACCOUNTTYPE
                        AccountType accountTypeObj = new AccountType();
                        accountTypeObj.setType(accountType.toString());

                        // ... table COUNTRYCODE
                        CountryCode countryCodeObj = new CountryCode();
                        countryCodeObj.setCode(accountCountryCode.toString());

                        // Going to the next "new account" window
                        NewAccountWindow_page2Controller controller = (NewAccountWindow_page2Controller) ControllerBase.loadFxml(
                                "NewAccountWindow_page2.fxml",
                                getMediator()
                        );
                        controller.setAccount(accountObj);
                        controller.setAccountType(accountTypeObj);
                        controller.setCountryCode(countryCodeObj);
                        controller.setFlagHolder(getFlagHolder());
                        Scene scene = new Scene(controller.getParent());
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();

                        //Close current window
                        Stage current = (Stage) btnNext.getScene().getWindow();
                        current.close();

                    } else {
                        AlertMessage.alertMessage("overdraft", "Only numbers and point/coma allowed");
                    }
                } else {
                    AlertMessage.alertMessage("account balance", "Only numbers and point/coma allowed");
                }
            } else {
                AlertMessage.alertMessage("creation date", "Cannot be in the future");
            }
        } else {
            AlertMessage.alertMessage("account number", "Only numbers allowed");
        }

    }

    @FXML
    private void handleBtnCancel(ActionEvent event) throws IOException {

        //Close current window
        Stage current = (Stage) btnCancel.getScene().getWindow();
        current.close();

        AppWindowController controller = (AppWindowController) ControllerBase.loadFxml(
                "AppWindow.fxml",
                getMediator()
        );
        controller.setFlagHolder(getFlagHolder());
        controller.initAppWindowController(getMediator());
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

}
