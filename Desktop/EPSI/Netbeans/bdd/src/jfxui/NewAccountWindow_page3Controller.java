package jfxui;

import db.home.bank.Account;
import db.home.bank.AccountManager;
import db.home.bank.AccountType;
import db.home.bank.Address;
import db.home.bank.Agency;
import db.home.bank.Bank;
import db.home.bank.CountryCode;
import db.home.bank.Holder;
import db.home.bank.Postcode;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.TypedQuery;
import utils.AlertMessage;
import static utils.DateUtils.LocalDate2Date;
import utils.Valid;

/**
 * Class which allowed the user to create a new account, page 3 of the form, and
 * then writes all the informations in the database. This herits from
 * NewAccountWindowController
 *
 * @author Mary
 */
public class NewAccountWindow_page3Controller extends NewAccountWindowController {

    @FXML
    private TextField txtAccountManagerName;
    @FXML
    private TextField txtAccountManagerFirstName;
    @FXML
    private TextField txtAccountManagerPhone;
    @FXML
    private TextField txtAccountManagerEmail;
    @FXML
    private DatePicker txtAccountManagerAssignementDate;
    @FXML
    private Button btnPrevious;
    @FXML
    private Button btnCreate;

    @Override
    public void initialize(Mediator mediator) {
    }

    @FXML
    private void handleBtnCreate(ActionEvent event) throws IOException {

        String accountManagerName = this.txtAccountManagerName.getText();
        String accountManagerFirstName = this.txtAccountManagerFirstName.getText();
        String accountManagerPhone = this.txtAccountManagerPhone.getText();
        String accountManagerEmail = this.txtAccountManagerEmail.getText();
        Date accountManagerAssignementDate = LocalDate2Date(this.txtAccountManagerAssignementDate.getValue());

        boolean flagNewBank = false;

        // Check the fields
        if (Valid.isValidOnlyLetters(accountManagerName)) {
            if (Valid.isValidOnlyLetters(accountManagerFirstName)) {
                if (Valid.isValidDateNoFuture(accountManagerAssignementDate)) {

                    // Getting the back-up objects of the two previous controller
                    // ... table POSTCODE
                    Postcode postcodeBdd = new Postcode(
                            null,
                            getPostcode().getPostcode(),
                            getPostcode().getCity()
                    );
                    if (alreadyExistsPostcode(getPostcode().getPostcode())) {
                        EntityManager em = getMediator().createEntityManager();
                        TypedQuery<Postcode> qPostcode = em.createQuery("SELECT a FROM Postcode a WHERE a.postcode=:param", Postcode.class);
                        qPostcode.setParameter("param", getPostcode().getPostcode());
                        postcodeBdd = qPostcode.getResultList().get(0);
                    }

                    // ... table ADDRESS
                    Address addressBdd = new Address(
                            null,
                            getAddress().getLine1()
                    );
                    if (getAddress().getLine2() != null) {
                        addressBdd.setLine2(getAddress().getLine2());
                    }
                    addressBdd.setIdPostcode(postcodeBdd);

                    // ... table BANK
                    /* On peut supprimer le choix du code banque qui est forcèment 
                    lié au nom de la banque */
 /*Bank bankBdd = new Bank(
                            null, 
                            getBank().getName(), 
                            getBank().getBankCode()
                    );*/
                    if (idBank(getBank().getName()) == 0) {
                        flagNewBank = true;
                    }
                    Bank bankBdd = new Bank(
                            idBank(getBank().getName()) == 0 ? null : idBank(getBank().getName()),
                            getBank().getName(),
                            getBank().getBankCode()
                    );

                    // ... table AGENCY
                    Agency agencyBdd = new Agency(
                            idBank(getAgency().getAgencyName()) == 0 ? null : idBank(getAgency().getAgencyName()),
                            getAgency().getAgencyName(),
                            getAgency().getAgencyCode()
                    );
                    agencyBdd.setIdAddress(addressBdd);
                    agencyBdd.setIdBank(bankBdd);

                    // ... table COUNTRYCODE
                    CountryCode countryCodeBdd = new CountryCode(
                            idCountryCode(getCountryCode().getCode()) == 0 ? null : idCountryCode(getCountryCode().getCode())
                    );

                    // ... table ACCOUNTTYPE
                    AccountType accountTypeBdd = new AccountType(
                            idAccountType(getAccountType().getType()) == 0 ? null : idAccountType(getAccountType().getType())
                    );
                    accountTypeBdd.setType(getAccountType().getType());

                    // ... table ACCOUNT
                    Account accountBdd = new Account(
                            null,
                            getAccount().getNumber(),
                            getAccount().getCreationDate(),
                            getAccount().getFirstBalance(),
                            getAccount().getOverdraft()
                    );
                    if (getAccount().getInterestRate() != null) {
                        accountBdd.setInterestRate(getAccount().getInterestRate());
                    }
                    if (getAccount().getDescription() != null) {
                        accountBdd.setDescription(getAccount().getDescription());
                    }
                    accountBdd.setIdAccountType(accountTypeBdd);
                    accountBdd.setIdAgency(agencyBdd);
                    accountBdd.setIdCountryCode(countryCodeBdd);

                    // ... table ACCOUNTMANAGER
                    AccountManager accountManagerBdd = new AccountManager(
                            null,
                            accountManagerName,
                            accountManagerFirstName,
                            accountManagerAssignementDate
                    );
                    if (Valid.isValidPhoneNumber(accountManagerPhone)) {
                        accountManagerBdd.setPhone(accountManagerPhone);
                    }
                    if (Valid.isValidEmail(accountManagerEmail)) {
                        accountManagerBdd.setEmail(accountManagerEmail);
                    }
                    accountManagerBdd.setIdAgency(agencyBdd);

                    // ... table ASSIGN (in Holder and Account classes)
                    EntityManager em = getMediator().createEntityManager();
                    TypedQuery<Holder> qHolder = em.createQuery("SELECT a FROM Holder a WHERE a.id=:pid", Holder.class);
                    qHolder.setParameter("pid", getFlagHolder());
                    Holder holderBdd = qHolder.getSingleResult();

                    Collection<Holder> collHolder = new HashSet();
                    collHolder.add(holderBdd);
                    accountBdd.setHolderCollection(collHolder);

                    Collection<Account> collAccount = new HashSet();
                    collAccount.add(accountBdd);
                    holderBdd.setAccountCollection(collAccount);

                    /* Writing into the database the information where the user 
                    have written something new. No more adding datas into:
                    em.persist(accountTypeBdd); // --> pas écrire en bdd
                    em.persist(countryCodeBdd); // --> pas écrire en bdd
                     */
                    //EntityManager em = getMediator().createEntityManager();
                    em.getTransaction().begin();
                    em.persist(postcodeBdd);
                    em.persist(addressBdd);
                    if (flagNewBank) {
                        em.persist(bankBdd);
                    }
                    em.persist(agencyBdd);
                    em.persist(accountBdd);
                    em.persist(accountManagerBdd);
                    em.getTransaction().commit();

                    //Close current window
                    Stage current = (Stage) this.btnCreate.getScene().getWindow();
                    current.close();
                    
                    //Refresh the app
                    AppWindowController controller = (AppWindowController)ControllerBase.loadFxml(
                            "AppWindow.fxml",
                            getMediator()
                    );
                    controller.setFlagHolder(getFlagHolder());
                    controller.initAppWindowController(getMediator());
                    Scene scene = new Scene(controller.getParent());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();

                } else {
                    AlertMessage.alertMessage("account manager assignement date", "Cannot be in the future");
                }

            } else {
                AlertMessage.alertMessage("account manager first name", "Only letters, hyphen and apostrophe allowed");
            }
        } else {
            AlertMessage.alertMessage("account manager name", "Only letters, hyphen and apostrophe allowed");
        }

    }

    /**
     * Method which checks if the post code already exist in the database
     *
     * @param str
     * @return
     */
    private boolean alreadyExistsPostcode(int str) {
        EntityManager em = getMediator().createEntityManager();
        TypedQuery<Postcode> qPostcode = em.createQuery("SELECT a.id FROM Postcode a WHERE a.postcode=:param", Postcode.class);
        qPostcode.setParameter("param", str);
        return !qPostcode.getResultList().isEmpty();
    }

    @FXML
    private void handleBtnPrevious(ActionEvent event) throws IOException {

        // Going to the previous "new account" window
        TitledPane loader = (TitledPane) FXMLLoader.load(getClass().getResource("NewAccountWindow_page2.fxml"));
        Scene scene = new Scene(loader);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        //Close current window
        Stage current = (Stage) this.btnPrevious.getScene().getWindow();
        current.close();

    }

}
