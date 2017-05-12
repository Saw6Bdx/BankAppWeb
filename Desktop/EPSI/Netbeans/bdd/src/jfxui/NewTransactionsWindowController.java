package jfxui;

import db.home.bank.Account;
import db.home.bank.Category;
import db.home.bank.Transactions;
import db.home.bank.TransactionType;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import utils.AlertMessage;
import static utils.DateUtils.LocalDate2Date;
import utils.Valid;

/**
 *
 * @author Nicolas
 */
public class NewTransactionsWindowController extends ControllerBase {

    private Account account;
    private Category category;
    private TransactionType transactionType;

    @FXML
    private TextField txtTransactionsLabel;
    @FXML
    private TextField txtTransactionsAmount;
    @FXML
    private DatePicker dateCreated;
    @FXML
    private DatePicker dateEnd;
    @FXML
    private ChoiceBox<Category> choiceCategory;
    @FXML
    private ChoiceBox<Account> choiceAccount;
    @FXML
    private ChoiceBox<TransactionType> choiceTransactionsType;
    @FXML
    private Button btnApply;
    @FXML
    private Button btnCancel;
    
    private int flagHolder;
    
    public void setFlagHolder(int flagHolder) {
        this.flagHolder = flagHolder;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public int idAccount(String str) {
        int id = 0;
        try {
            EntityManager em = getMediator().createEntityManager();
            TypedQuery<Account> qAccount = em.createQuery("SELECT a FROM Account a JOIN a.holderCollection h WHERE h.id=:pid", Account.class);
            qAccount.setParameter("pid", this.flagHolder);
            List<Account> accountList = qAccount.getResultList();

            for (int i = 0; i < accountList.size(); i++) {
                if (str.equals(accountList.get(i).getNumber())) {
                    id = accountList.get(i).getId();
                }
            }

            em.close();
        } catch (PersistenceException e) {

        }
        return id;
    }

    public int idTransactionType(String str) {
        int id = 0;
        try {
            EntityManager em = getMediator().createEntityManager();
            TypedQuery<TransactionType> qTransactionType = em.createNamedQuery("TransactionType.findAll", TransactionType.class);
            List<TransactionType> transactionTypeList = qTransactionType.getResultList();

            for (int i = 0; i < transactionTypeList.size(); i++) {
                if (str.equals(transactionTypeList.get(i).getType())) {
                    id = transactionTypeList.get(i).getId();;
                }
            }

            em.close();
        } catch (PersistenceException e) {

        }
        return id;
    }

    public int idCategory(String str) {

        int id = 0;

        try {
            EntityManager em = getMediator().createEntityManager();
            TypedQuery<Category> qCategory = em.createNamedQuery("Category.findAll", Category.class);
            List<Category> categoryList = qCategory.getResultList();

            for (int i = 0; i < categoryList.size(); i++) {
                if (str.equals(categoryList.get(i).getLabel())) {
                    id = categoryList.get(i).getId();
                }
            }

            em.close();
        } catch (PersistenceException e) {

        }
        return id;
    }

    @Override
    public void initialize(Mediator mediator) {
    }
    
    public void initNewTransactionsWindow() {
        try {
            EntityManager em = getMediator().createEntityManager();
            List<Category> categories = em.createNamedQuery("Category.findAll", Category.class).getResultList();

            try {
                TypedQuery<Account> qAccount = em.createQuery("SELECT a FROM Account a JOIN a.holderCollection h WHERE h.id=:pid", Account.class);
                qAccount.setParameter("pid", this.flagHolder);
                List<Account> accounts = qAccount.getResultList();
                
                this.choiceAccount.setItems(FXCollections.observableList(accounts));
            } catch (PersistenceException e) {
                this.btnCancel.setDisable(true);
                AlertMessage.processPersistenceException(e);
            }
            try {
                List<TransactionType> accounts = em.createNamedQuery("TransactionType.findAll", TransactionType.class).getResultList();
                this.choiceTransactionsType.setItems(FXCollections.observableList(accounts));
            } catch (PersistenceException e) {
                this.btnCancel.setDisable(true);
                AlertMessage.processPersistenceException(e);
            }

            this.choiceCategory.setItems(FXCollections.observableList(categories));
            em.close();
        } catch (PersistenceException e) {
            this.btnCancel.setDisable(true);
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
    private void handleBtnApply(ActionEvent event) throws IOException {
        String transactionsLabel = txtTransactionsLabel.getText();
        String transactionsAmount = txtTransactionsAmount.getText();
        Date transactionsCreationDate = LocalDate2Date(dateCreated.getValue());
        Date transactionsEndDate = LocalDate2Date(dateEnd.getValue());
        Category transactionsCategory = choiceCategory.getValue();
        Account transactionsAccount = choiceAccount.getValue();
        TransactionType transactionsType = choiceTransactionsType.getValue();

        Alert alert = new Alert(AlertType.CONFIRMATION, "Do you confirm this new transaction ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        ButtonType result = alert.getResult();
        if (result == ButtonType.NO) {
            alert.close();
        } else {
            // Check the fields
            if (Valid.isValidOnlyLetters(transactionsLabel)) { // que des lettres pour le moment
                if (Valid.isValidDateNoFuture(transactionsCreationDate)) { // date de création antérieure à la date d'aujourd'hui
                    if (Valid.isValidDouble(transactionsAmount)) { // double

                        // Saving informations ... 
                        // ... table TRANSACTIONS
                        Transactions TransactionsBdd = new Transactions();
                        
                        TransactionsBdd.setDate(transactionsCreationDate);
                        TransactionsBdd.setAmount(Double.parseDouble(transactionsAmount));
                        TransactionsBdd.setLabel(transactionsLabel);
                        TransactionsBdd.setEndDate(transactionsEndDate);
                        TransactionsBdd.setIdTransactionType(transactionsType);
                        TransactionsBdd.setIdAccount(transactionsAccount);
                        TransactionsBdd.setIdCategory(transactionsCategory);
                      
                        EntityManager em = getMediator().createEntityManager();

                        em.getTransaction().begin();
                        em.persist(TransactionsBdd);
                        em.getTransaction().commit();

                        //Close current window
                        Stage current = (Stage) btnApply.getScene().getWindow();
                        current.close();

                    } else {
                        AlertMessage.alertMessage("transaction amount", "Only numbers and point/coma allowed");
                    }
                } else {
                    AlertMessage.alertMessage("creation date", "Cannot be in the future");
                }
            } else {
                AlertMessage.alertMessage("transaction label", "Only letters allowed");
            }
        }
    }
}
