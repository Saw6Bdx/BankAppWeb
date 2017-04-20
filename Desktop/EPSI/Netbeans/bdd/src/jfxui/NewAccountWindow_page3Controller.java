package jfxui;

import db.home.bank.Account;
import db.home.bank.AccountManager;
import db.home.bank.AccountType;
import db.home.bank.Address;
import db.home.bank.Agency;
import db.home.bank.Bank;
import db.home.bank.CountryCode;
import db.home.bank.Postcode;
import java.io.IOException;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.AlertMessage;
import static utils.DateUtils.LocalDate2Date;
import utils.Valid;

/**
 *  
 * @author Mary
 */
public class NewAccountWindow_page3Controller extends NewAccountWindowController {//ControllerBase {
    
    @FXML private TextField txtAccountManagerName;
    @FXML private TextField txtAccountManagerFirstName;
    @FXML private TextField txtAccountManagerPhone;
    @FXML private TextField txtAccountManagerEmail;
    @FXML private DatePicker txtAccountManagerAssignementDate;
    @FXML private Button btnPrevious;
    @FXML private Button btnCreate;
   
    /*private Agency agency;
    private Bank bank;
    private Address address;
    private Postcode postCode;
    private Account account;
    private AccountType accountType;
    private CountryCode countryCode;

    public void setBank(Bank bank) {
        this.bank = bank;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public void setPostcode(Postcode postCode) {
        this.postCode = postCode;
    }
    
    public void setAccount(Account account) {
        this.account = account;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }
    
    public void setAgency(Agency agency) {
        this.agency = agency;
    }*/
    
    @Override
    public void initialize(Mediator mediator) {
    }
    
    @FXML
    private void handleBtnCreate(ActionEvent event) throws IOException {
    
        String accountManagerName = txtAccountManagerName.getText();
        String accountManagerFirstName = txtAccountManagerFirstName.getText();
        String accountManagerPhone = txtAccountManagerPhone.getText();
        String accountManagerEmail = txtAccountManagerEmail.getText();
        Date accountManagerAssignementDate = LocalDate2Date(txtAccountManagerAssignementDate.getValue());
        
        // Check the fields
        if (Valid.isValidOnlyLetters(accountManagerName)) {
            if (Valid.isValidOnlyLetters(accountManagerFirstName)) {
                if (Valid.isValidDateNoFuture(accountManagerAssignementDate)) {

                    // Getting the back-up objects of the two previous controller
                    // ... table POSTCODE
                    Postcode postcodeBdd = new Postcode(null, getPostcode().getPostcode(), getPostcode().getCity());

                    // ... table ADDRESS
                    Address addressBdd = new Address(null, getAddress().getLine1());
                    if (getAddress().getLine2() != null) { // || !this.address.getLine2().isEmpty()) {
                        addressBdd.setLine2(getAddress().getLine2());
                    }
                    addressBdd.setIdPostcode(postcodeBdd);

                    // ... table BANK
                    Bank bankBdd = new Bank(null, getBank().getName(), getBank().getBankCode());

                    // ... table AGENCY
                    Agency agencyBdd = new Agency(null, getAgency().getAgencyName(), getAgency().getAgencyCode());
                    agencyBdd.setIdAddress(addressBdd);
                    agencyBdd.setIdBank(bankBdd);

                    // ... table COUNTRYCODE
                    CountryCode countryCodeBdd = new CountryCode(null, getCountryCode().getCode());
                    
                    // ... table ACCOUNTTYPE
                    AccountType accountTypeBdd = new AccountType(null);
                    System.out.println("Type de compte : "+getAccountType().getType());
                    accountTypeBdd.setType(getAccountType().getType());

                    

                    // ... table ACCOUNT
                    Account accountBdd = new Account(null, getAccount().getNumber(), getAccount().getCreationDate(),
                            getAccount().getFirstBalance(), getAccount().getOverdraft());
                    if (true) {//!this.account.getInterestRate()) {
                        accountBdd.setInterestRate(getAccount().getInterestRate());
                    }
                    if (!getAccount().getDescription().isEmpty()) {
                        accountBdd.setDescription(getAccount().getDescription());
                    }
                    accountBdd.setIdAccountType(accountTypeBdd);
                    accountBdd.setIdAgency(agencyBdd);
                    accountBdd.setIdCountryCode(countryCodeBdd);

                    // ... table ACCOUNTMANAGER
                    AccountManager accountManagerBdd = new AccountManager(null, accountManagerName, accountManagerFirstName, accountManagerAssignementDate);
                    if (Valid.isValidPhoneNumber(accountManagerPhone)) {
                        accountManagerBdd.setPhone(accountManagerPhone);
                    }
                    if (Valid.isValidPhoneNumber(accountManagerEmail)) {
                        accountManagerBdd.setEmail(accountManagerEmail);
                    }
                    accountManagerBdd.setIdAgency(agencyBdd);
                    
                    /*// ... table ADDRESS
                    Address addressBdd = new Address(null, this.address.getLine1());
                    if (this.address.getLine2() != null) { // || !this.address.getLine2().isEmpty()) {
                        addressBdd.setLine2(this.address.getLine2());
                    }
                    addressBdd.setIdPostcode(postcodeBdd);

                    // ... table BANK
                    Bank bankBdd = new Bank(null, this.bank.getName(), this.bank.getBankCode());

                    // ... table AGENCY
                    Agency agencyBdd = new Agency(null, this.agency.getAgencyName(), this.agency.getAgencyCode());
                    agencyBdd.setIdAddress(addressBdd);
                    agencyBdd.setIdBank(bankBdd);

                    // ... table ACCOUNTTYPE
                    AccountType accountTypeBdd = new AccountType(null);
                    accountTypeBdd.setType(this.accountType.getType());

                    // ... table COUNTRYCODE
                    CountryCode countryCodeBdd = new CountryCode(null, this.countryCode.getCode());

                    // ... table ACCOUNT
                    Account accountBdd = new Account(null, this.account.getNumber(), this.account.getCreationDate(),
                            this.account.getFirstBalance(), this.account.getOverdraft());
                    if (true) {//!this.account.getInterestRate()) {
                        accountBdd.setInterestRate(this.account.getInterestRate());
                    }
                    if (!this.account.getDescription().isEmpty()) {
                        accountBdd.setDescription(this.account.getDescription());
                    }
                    accountBdd.setIdAccountType(accountTypeBdd);
                    accountBdd.setIdAgency(agencyBdd);
                    accountBdd.setIdCountryCode(countryCodeBdd);

                    // ... table ACCOUNTMANAGER
                    AccountManager accountManagerBdd = new AccountManager(null, accountManagerName, accountManagerFirstName, accountManagerAssignementDate);
                    if (Valid.isValidPhoneNumber(accountManagerPhone)) {
                        accountManagerBdd.setPhone(accountManagerPhone);
                    }
                    if (Valid.isValidPhoneNumber(accountManagerEmail)) {
                        accountManagerBdd.setEmail(accountManagerEmail);
                    }
                    accountManagerBdd.setIdAgency(agencyBdd);*/

                    // Debug ...
                    /*System.out.println(String.format("Post code : %s", this.postCode.getPostcode()));
                    System.out.println(String.format("City : %s", this.postCode.getCity()));
                    System.out.println(String.format("Address line 1 : %s", this.address.getLine1()));
                    System.out.println(String.format("IdPostcode : %s", postcodeBdd.getId()));
                    System.out.println(String.format("Agency code : %s", this.agency.getAgencyCode()));
                    System.out.println(String.format("Agency name : %s", this.agency.getAgencyName()));
                    System.out.println(String.format("Id address : %s", agencyBdd.getIdAddress()));
                    System.out.println(String.format("Id bank : %s", agencyBdd.getIdBank()));*/

                    // Writing into the database
                    EntityManagerFactory emf = Persistence.createEntityManagerFactory("BankAppPU");
                    EntityManager em = emf.createEntityManager();

                    em.getTransaction().begin();
                    em.persist(postcodeBdd);
                    em.persist(addressBdd);
                    em.persist(bankBdd);
                    em.persist(agencyBdd);
                    em.persist(accountTypeBdd);
                    em.persist(countryCodeBdd);
                    em.persist(accountBdd);
                    em.persist(accountManagerBdd);
                    em.getTransaction().commit();

                    //Close current window
                    Stage current = (Stage) btnCreate.getScene().getWindow();
                    current.close();

                } else {
                    AlertMessage.alertMessage("account manager assignement date", "Cannot be in the future");
                }

            } else {
                AlertMessage.alertMessage("account manager first name", "Only letters, hyphen and apostrophe allowed");
            }
        } else {
            AlertMessage.alertMessage("account manager name", "Only letters, hyphen and apostrophe allowed");
        }

        // Une fois arrivée au bout des 3 controlleurs, 
        // création d'un nouveau bouton dans AppWindowController pour le compte en cours
      
    }
  
    
    @FXML
    private void handleBtnPrevious(ActionEvent event) throws IOException {

        // Going to the previous "new account" window
        TitledPane loader = (TitledPane)FXMLLoader.load(getClass().getResource("NewAccountWindow_page2.fxml"));
        Scene scene = new Scene(loader);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        //Close current window
        Stage current = (Stage)btnPrevious.getScene().getWindow();
        current.close();
        
    }
    
}