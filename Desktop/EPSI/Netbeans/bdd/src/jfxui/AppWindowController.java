package jfxui;

import db.home.bank.Account;
import db.home.bank.Agency;
import db.home.bank.Bank;
import db.home.bank.CountryCode;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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
    private Parent root;

    @Override
    public void initialize(Mediator mediator) {
    }

    @FXML
    private void handleMenuFileChangeUser(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);

        ControllerBase controller = ControllerBase.loadFxml("LoginWindow.fxml", mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        this.root.getScene().getWindow().hide();

    }

    @FXML
    private void handleMenuFileNewUser(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);

        ControllerBase controller = ControllerBase.loadFxml("NewUserWindow.fxml", mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        this.root.getScene().getWindow().hide();
    }

    @FXML
    private void handleMenuModifyUserProfile(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);

        ControllerBase controller = ControllerBase.loadFxml("ModifyUserWindow.fxml", mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        this.root.getScene().getWindow().hide();
    }

    @FXML
    private void handleMenuChangePassword(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);

        ControllerBase controller = ControllerBase.loadFxml("ChangePasswordWindow.fxml", mediator);
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
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);
        ControllerBase controller = ControllerBase.loadFxml("NewAccountWindow_page1.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        // New "account" button in the AppWindow
        // TO BE IMPLEMENTED ...
    }

    @FXML
    private void handleMenuModifyAccountManager(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);

        ControllerBase controller = ControllerBase.loadFxml("ModifyAccountManagerWindow.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleMenuDeleteAccountManager(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);

        ControllerBase controller = ControllerBase.loadFxml("DeleteAccountManagerWindow.fxml", this.mediator);
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

        List<Bank> bankList = qBank.setParameter("acc", 1).getResultList();
        Bank bank = bankList.get(0);

        List<Agency> agencyList = qAgency.setParameter("acc", 1).getResultList();
        Agency agency = agencyList.get(0);

        List<Account> accountList = qAccount.setParameter("acc", 1).getResultList();
        Account account = accountList.get(0);

        List<CountryCode> countryCodeList = qCountryCode.setParameter("acc", 1).getResultList();
        CountryCode countryCode = countryCodeList.get(0);

        em.close();

        try {
            FileWriter fw = new FileWriter("Rib.txt");
            String rib = RibIban.generateRib(agency, account, bank);
            String iban = RibIban.generateIban(agency, account, bank, countryCode);
            String str = "--------------------------RIB-------------------------\n";
            str += "Bank code  " + "  Agency code  " + "  Account number  " + "  Rib key\n";
            str += "  " + rib.substring(0, 5) + "         " + rib.substring(5, 10) + "        " + rib.substring(10, rib.length() - 2) + "        " + rib.substring(rib.length() - 2, rib.length()) + "\n";
            str += "\n---------------IBAN--------------\n";
            str += iban.substring(0, 4) + " " + iban.substring(4, 8) + " " + iban.substring(8, 12) + " " + iban.substring(12, 16) + " " + iban.substring(16, 20) + " " + iban.substring(20, 24) + " " + iban.substring(24, iban.length());
            fw.write(str);
            fw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMenuEditNewTransaction(ActionEvent event) throws IOException {

        ControllerBase controller = ControllerBase.loadFxml("NewTransactionsWindow.fxml", getMediator());
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleMenuBudgetCategories(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);

        ControllerBase controller = ControllerBase.loadFxml("BudgetCategoriesWindow_v3.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleMenuBudgetNewCategory(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);

        ControllerBase controller = ControllerBase.loadFxml("NewCategoryWindow.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
	
	@FXML
    private void handleMenuBudgetDeleteCategory(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);

        ControllerBase controller = ControllerBase.loadFxml("DeleteCategoryWindow.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleButtonAccountCurrent(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);

        TransactionsWindowController controller = (TransactionsWindowController) ControllerBase.loadFxml(
                "TransactionsWindow.fxml",
                this.mediator
        );
        controller.setFlagAccountType("Current");
        controller.initTransactionsWindowController(this.mediator);
        content.getChildren().setAll(controller.getParent());

        ContactWindowController controller2 = (ContactWindowController) ControllerBase.loadFxml(
                "ContactWindow.fxml",
                this.mediator
        );
        controller2.setFlagAccountType("Current");
        controller2.initContactWindowController(this.mediator);
        contact.getChildren().setAll(controller2.getParent());
    }

    @FXML
    private void handleButtonAccountSaving(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);

        TransactionsWindowController controller = (TransactionsWindowController) ControllerBase.loadFxml(
                "TransactionsWindow.fxml",
                this.mediator
        );
        controller.setFlagAccountType("Savings");
        controller.initTransactionsWindowController(this.mediator);
        content.getChildren().setAll(controller.getParent());

        ContactWindowController controller2 = (ContactWindowController) ControllerBase.loadFxml(
                "ContactWindow.fxml",
                this.mediator
        );
        controller2.setFlagAccountType("Savings");
        controller2.initContactWindowController(this.mediator);
        contact.getChildren().setAll(controller2.getParent());

    }

    @FXML
    private void handleContact(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);

        ControllerBase controller = ControllerBase.loadFxml("HelpContactWindow.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleAbout(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);

        ControllerBase controller = ControllerBase.loadFxml("AboutWindow.fxml", this.mediator);
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private Mediator mediator = null;
    private EntityManagerFactory emf = null;
}
