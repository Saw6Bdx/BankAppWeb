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
import javafx.scene.control.Button;
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
    
    @FXML private TextField txtTransactionsLabel;
    @FXML private TextField txtTransactionsAmount;
    @FXML private DatePicker dateCreated;
    @FXML private DatePicker dateEnd;
    @FXML private ChoiceBox<Category> choiceCategory;
    @FXML private ChoiceBox<Account> choiceAccount;
    @FXML private ChoiceBox<TransactionType>choiceTransactionsType;
    @FXML private Button btnApply;
    @FXML private Button btnCancel;
    
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
            TypedQuery<Account> qAccount = em.createNamedQuery("Account.findAll", Account.class);
            List<Account> accountList = qAccount.getResultList();
            
            for ( int i = 0 ; i < accountList.size() ; i++ ) {
                if ( str.equals(accountList.get(i).getNumber()) ) {
                    id = i;
                }
            }
            
            em.close();
        } catch (PersistenceException e) {
            
        }
        return id;
        /*switch (str) {
            case "1324350971":
                id = 1;
                break;
            case "1EGR693CZ20":
                id = 2;
                break;
        }
        return id;*/
    }
    
    public int idTransactionType(String str) {
        int id = 0;
        try {
            EntityManager em = getMediator().createEntityManager();
            TypedQuery<TransactionType> qTransactionType = em.createNamedQuery("TransactionType.findAll", TransactionType.class);
            List<TransactionType> transactionTypeList = qTransactionType.getResultList();
            
            for ( int i = 0 ; i < transactionTypeList.size() ; i++ ) {
                if ( str.equals(transactionTypeList.get(i).getType()) ) {
                    id = i;
                }
            }
            
            em.close();
        } catch (PersistenceException e) {
            
        }
        return id;
        /*switch (str) {
            case "Transfer":
                id = 1;
                break;
            case "Check":
                id = 2;
                break;
            case "Withdrawal":
                id = 3;
                break;
            case "CC payment":
                id = 4;
                break;
            case "Bank's order":
                id = 5;
                break;
        }
        return id;*/
    }
    
    
    public int idCategory(String str) {
        
        int id = 0;
        
        try {
            EntityManager em = getMediator().createEntityManager();
            TypedQuery<Category> qCategory = em.createNamedQuery("Category.findAll", Category.class);
            List<Category> categoryList = qCategory.getResultList();
            
            for ( int i = 0 ; i < categoryList.size() ; i++ ) {
                if ( str.equals(categoryList.get(i).getLabel()) ) {
                    id = i;
                }
            }
            
            em.close();
        } catch (PersistenceException e) {
            
        }
        return id;
/*        int id = 0;
        switch (str) {
            case "Transportation":
                id = 1;
                break;
            case "Everyday life":
                id = 2;
                break;
            case "Supermarket":
                id = 3;
                break;
            case "Withdrawal":
                id = 4;
                break;
            case "Dressing":
                id = 5;
                break;
            case "Health":
                id = 6;
                break;
            case "Accomodation":
                id = 7;
                break;
            case "Rent":
                id = 8;
                break;
            case "Electricity / water / gas":
                id = 9;
                break;
            case "Furniture":
                id = 10;
                break;
            case "Maintenance":
                id = 11;
                break;
            case "Hobbies":
                id = 12;
                break;
            case "Professional":
                id = 13;
                break;
            case "Saving":
                id = 14;
                break;
        }*/
        
    }
    
    
    @Override
    public void initialize(Mediator mediator) {
        try {
            EntityManager em = mediator.createEntityManager();
            List<Category> categories = em.createNamedQuery("Category.findAll", Category.class).getResultList();
            
            
            try{
                List<Account> accounts = em.createNamedQuery("Account.findAll", Account.class).getResultList();
                this.choiceAccount.setItems(FXCollections.observableList(accounts));
            }
            catch(PersistenceException e) {
                this.btnCancel.setDisable(true);
                AlertMessage.processPersistenceException(e);
            }
                try{
                List<TransactionType> accounts = em.createNamedQuery("TransactionType.findAll", TransactionType.class).getResultList();
                this.choiceTransactionsType.setItems(FXCollections.observableList(accounts));
                }
            catch(PersistenceException e) {
                this.btnCancel.setDisable(true);
                AlertMessage.processPersistenceException(e);
                }
            
            this.choiceCategory.setItems(FXCollections.observableList(categories));
            em.close();
	}
	catch(PersistenceException e) {
            this.btnCancel.setDisable(true);
            AlertMessage.processPersistenceException(e);
        }
    }
    
    @FXML
    private void handleBtnCancel(ActionEvent event) throws IOException {
        //Close current window
        Stage current = (Stage)btnCancel.getScene().getWindow();
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
        
        
        // Check the fields
        if(Valid.isValidOnlyLetters(transactionsLabel)){ // que des lettres pour le moment
            if(Valid.isValidDateNoFuture(transactionsCreationDate)){ // date de création antérieure à la date d'aujourd'hui
                if(Valid.isValidDouble(transactionsAmount)){ // double
                   
                        // Saving informations ... 
                        
                        // ... table TRANSACTIONS
                        Transactions TransactionsBdd = new Transactions();
                        TransactionsBdd.setDate(transactionsCreationDate);
                        TransactionsBdd.setAmount(Double.parseDouble(transactionsAmount));
                        TransactionsBdd.setLabel(transactionsLabel);
                        TransactionsBdd.setEndDate(transactionsEndDate);
                                                
                        // ... table TRANSACTIONTYPE
                        TransactionType transactionTypeBdd = new TransactionType(
                        idTransactionType(transactionsType.getType()) == 0 ? null : idTransactionType(transactionsType.getType())
                        );
                        
                        // ... table CATEGORY
                        Category CategoryBdd = new Category(
                        idCategory(transactionsCategory.getLabel()) == 0 ? null : idCategory(transactionsCategory.getLabel())
                        );
                        
                        // ... table ACCOUNT
                        Account AccountBdd = new Account(
                        idAccount(transactionsAccount.getNumber()) == 0 ? null : idAccount(transactionsAccount.getNumber())
                        );
                        
                        
                        TransactionsBdd.setIdTransactionType(transactionTypeBdd);
                        TransactionsBdd.setIdAccount(AccountBdd);
                        TransactionsBdd.setIdCategory(CategoryBdd);
                        
                        
                        EntityManager em = getMediator().createEntityManager();

                        em.getTransaction().begin();
                        em.persist(TransactionsBdd);
                        em.getTransaction().commit();

                        //Close current window
                        Stage current = (Stage)btnApply.getScene().getWindow();
                        current.close();
                   
                }
                else {
                    AlertMessage.alertMessage("transaction amount","Only numbers and point/coma allowed");
                }
            }
            else {
                AlertMessage.alertMessage("creation date","Cannot be in the future");
            }
        }
        else {
            AlertMessage.alertMessage("transaction label","Only letters allowed");
        }
    }
}