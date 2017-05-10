package jfxui;

import db.home.bank.Account;
import db.home.bank.Agency;
import db.home.bank.Bank;
import db.home.bank.CountryCode;
import db.home.bank.Holder;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import utils.AlertMessage;
import utils.RibIban;

/**
 * FXML Controller class
 *
 * @author Charlotte
 */
public class AppWindowController extends ControllerBase {

    @FXML
    private AnchorPane content;
    @FXML
    private AnchorPane contact;
    @FXML
    private TitledPane root;
    @FXML
    private ListView<Account> listAccount;
    
    public void setFlagHolder(int flagHolder) {
        this.flagHolder = flagHolder;
    }
    
    @Override
    public void initialize(Mediator mediator){
        // empty initialization
    }
    
    public void initAppWindowController(Mediator mediator) {
        this.mediator = mediator;

        try {
            EntityManager em = mediator.createEntityManager();
            TypedQuery<Holder> qHolder = em.createQuery("SELECT h FROM Holder h WHERE h.id =:holder", Holder.class);
            this.root.setText("BankApp : " + qHolder.setParameter("holder", this.flagHolder).getSingleResult());
            // Getting all the accounts available
            TypedQuery<Account> qAccount = em.createQuery("SELECT a FROM Account a JOIN a.holderCollection h WHERE h.id =:holder", Account.class);
            List<Account> accountList = qAccount.setParameter("holder", this.flagHolder).getResultList();

            this.listAccount.setItems(FXCollections.observableArrayList(accountList));
            this.emf =  Persistence.createEntityManagerFactory("BankAppPU");
            
            em.close();
        } catch (PersistenceException e) {
            AlertMessage.processPersistenceException(e);
        }
    }

    /**
     * Method which opens the transactions from the selected account in the list
     */
    @FXML
    private void handleOpenAccount(MouseEvent event) throws IOException {
        
        TransactionsWindowController controller = (TransactionsWindowController) ControllerBase.loadFxml(
                "TransactionsWindow.fxml",
                this.mediator
        );
        this.flagAccount = this.listAccount.getSelectionModel().getSelectedItem().getId();
        controller.setFlagAccount(this.flagAccount);
        controller.initTransactionsWindowController();//this.mediator);
        content.getChildren().setAll(controller.getParent());

        ContactWindowController controller2 = (ContactWindowController) ControllerBase.loadFxml(
                "ContactWindow.fxml",
                this.mediator
        );
        controller2.setFlagAccount(this.flagAccount);
        controller2.initContactWindowController(this.mediator);
        contact.getChildren().setAll(controller2.getParent());
    }
    
    @FXML
    private void handleMenuFileChangeUser(ActionEvent event) throws IOException {

        ControllerBase controller = ControllerBase.loadFxml("LoginWindow.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        this.root.getScene().getWindow().hide();

    }

    @FXML
    private void handleMenuFileNewUser(ActionEvent event) throws IOException {
        
        ControllerBase controller = ControllerBase.loadFxml("NewUserWindow.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void handleMenuModifyUserProfile(ActionEvent event) throws IOException {
        
        ModifyUserWindowController controller = (ModifyUserWindowController) ControllerBase.loadFxml(
                "ModifyUserWindow.fxml", 
                this.mediator
        );
        controller.setFlagHolder(this.flagHolder);
        controller.initModifyUserProfileWindow();
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void handleMenuChangePassword(ActionEvent event) throws IOException {
        
        ControllerBase controller = ControllerBase.loadFxml("ChangePasswordWindow.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleMenuFileClose(ActionEvent event) throws IOException {
        Platform.exit();
    }

    @FXML
    private void handleMenuEditNewAccount(ActionEvent event) throws IOException {
        
        NewAccountWindowController controller = (NewAccountWindowController) ControllerBase.loadFxml("NewAccountWindow_page1.fxml", this.mediator);
        controller.setFlagHolder(this.flagHolder);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        this.root.getScene().getWindow().hide();
        
        // New "account" button in the AppWindow
        // TO BE IMPLEMENTED ...
    }
    
    
    @FXML
    private void handleMenuDeleteAccount(ActionEvent event) throws IOException {
        
        DeleteAccountWindowController controller = (DeleteAccountWindowController) ControllerBase.loadFxml("DeleteAccountWindow.fxml", this.mediator);
        controller.setFlagHolder(this.flagHolder);
        controller.initDeleteAccountWindow();
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
    }

    
    @FXML
    private void handleMenuModifyAccountManager(ActionEvent event) throws IOException {
        
        ControllerBase controller = ControllerBase.loadFxml("ModifyAccountManagerWindow.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleMenuDeleteAccountManager(ActionEvent event) throws IOException {
        
        //ControllerBase controller = ControllerBase.loadFxml("DeleteAccountManagerWindow.fxml", this.mediator);
        DeleteAccountManagerWindowController controller = (DeleteAccountManagerWindowController) ControllerBase.loadFxml(
                "DeleteAccountManagerWindow.fxml", 
                this.mediator
        );
        controller.setFlagHolder(this.flagHolder);
        controller.initDeleteAccountManagerWindow();
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleMenuAssignNewHolder(ActionEvent event) throws IOException{
        ControllerBase controller = ControllerBase.loadFxml("AssignNewHolder.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleMenuEditRibIban(ActionEvent event) throws IOException {
        EntityManager em = getMediator().createEntityManager();
        TypedQuery<Bank> qBank = em.createQuery("SELECT b FROM Bank b JOIN b.agencyCollection ag JOIN ag.accountCollection a WHERE a.id =:acc", Bank.class);
        TypedQuery<Agency> qAgency = em.createQuery("SELECT ag FROM Agency ag JOIN ag.accountCollection a WHERE a.id =:acc", Agency.class);
        TypedQuery<CountryCode> qCountryCode = em.createQuery("SELECT cc FROM CountryCode cc JOIN cc.accountCollection a WHERE a.id =:acc", CountryCode.class);
        TypedQuery<Account> qAccount = em.createQuery("SELECT a FROM Account a WHERE a.id =:acc", Account.class);

        this.flagAccount = this.listAccount.getSelectionModel().getSelectedItem().getId();
        
        List<Bank> bankList = qBank.setParameter("acc", this.flagAccount).getResultList();
        Bank bank = bankList.get(0);

        List<Agency> agencyList = qAgency.setParameter("acc", this.flagAccount).getResultList();
        Agency agency = agencyList.get(0);

        List<Account> accountList = qAccount.setParameter("acc", this.flagAccount).getResultList();
        Account account = accountList.get(0);

        List<CountryCode> countryCodeList = qCountryCode.setParameter("acc", this.flagAccount).getResultList();
        CountryCode countryCode = countryCodeList.get(0);

        em.close();

        try {
            FileWriter fw = new FileWriter("Rib.txt");
            String rib = RibIban.generateRib(agency, account, bank);
            String iban = RibIban.generateIban(agency, account, bank, countryCode);
            String str = "--------------------------RIB-------------------------\r\n";
            str += "Bank code  " + "  Agency code  " + "  Account number  " + "  Rib key\r\n";
            str += "  " + rib.substring(0, 5) + "         " + rib.substring(5, 10) + "        " + rib.substring(10, rib.length() - 2) + "        " + rib.substring(rib.length() - 2, rib.length()) + "\r\n";
            str += "\r\n---------------IBAN--------------\r\n";
            str += iban.substring(0, 4) + " " + iban.substring(4, 8) + " " + iban.substring(8, 12) + " " + iban.substring(12, 16) + " " + iban.substring(16, 20) + " " + iban.substring(20, 24) + " " + iban.substring(24, iban.length());
            fw.write(str);
            fw.close();
            
            ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "Rib.txt");
            pb.start();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMenuEditNewTransaction(ActionEvent event) throws IOException {

        NewTransactionsWindowController controller = (NewTransactionsWindowController) ControllerBase.loadFxml(
                "NewTransactionsWindow.fxml", 
                this.mediator
        );
        controller.setFlagHolder(flagHolder);
        controller.initNewTransactionsWindow();
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleMenuDeleteTransaction(ActionEvent event) throws IOException {
        
        DeleteTransactionsWindowController controller = (DeleteTransactionsWindowController) ControllerBase.loadFxml("DeleteTransactionsWindow.fxml", this.mediator);
        controller.setFlagHolder(this.flagHolder);
        controller.initDeleteAccountWindow();
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
     private void handleMenuEditNewRecipient(ActionEvent event) throws IOException {
 
         ControllerBase controller = ControllerBase.loadFxml(
                 "NewRecipientWindow.fxml", 
                 this.mediator
         );
         Scene scene = new Scene(controller.getParent());
         Stage stage = new Stage();
         stage.setScene(scene);
         stage.show();
      }
 
     @FXML
     private void handleMenuEditDeleteRecipient(ActionEvent event) throws IOException {
         
         ControllerBase controller = ControllerBase.loadFxml(
                 "DeleteRecipientWindow.fxml", 
                 this.mediator
         );
         Scene scene = new Scene(controller.getParent());
         Stage stage = new Stage();
         stage.setScene(scene);
         stage.show();
      }

    @FXML
    private void handleMenuBudgetCategories(ActionEvent event) throws IOException {
        
        BudgetCategoriesWindowController_v3 controller = (BudgetCategoriesWindowController_v3) ControllerBase.loadFxml(
                "BudgetCategoriesWindow_v3.fxml", 
                this.mediator
        );
        controller.setFlagAccount(this.listAccount.getSelectionModel().getSelectedItem().getId());
        controller.initBudgetCategoriesWindowController(this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
    }

    @FXML
    private void handleMenuBudgetNewCategory(ActionEvent event) throws IOException {
        
        ControllerBase controller = ControllerBase.loadFxml("NewCategoryWindow.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleMenuBudgetDeleteCategory(ActionEvent event) throws IOException {
        
        ControllerBase controller = ControllerBase.loadFxml("DeleteCategoryWindow.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleContact(ActionEvent event) throws IOException {
        
        ControllerBase controller = ControllerBase.loadFxml("HelpContactWindow.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleAbout(ActionEvent event) throws IOException {
        
        ControllerBase controller = ControllerBase.loadFxml("AboutWindow.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private Mediator mediator = null;
    private EntityManagerFactory emf = null;
    private int flagHolder, flagAccount;
}
   