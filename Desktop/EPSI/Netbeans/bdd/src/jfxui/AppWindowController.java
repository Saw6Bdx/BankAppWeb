
package jfxui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * FXML Controller class
 *
 * @author Charlotte
 */

public class AppWindowController extends ControllerBase{
    @FXML private AnchorPane content;
    @FXML private AnchorPane contact;
    
    @Override
    public void initialize(Mediator mediator){
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
        //Hide current window, ne fonctionne pas, à voir plus tard
        ((Node)(event.getSource())).getScene().getWindow().hide();
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
        //Hide current window, ne fonctionne pas, à voir plus tard
        ((Node)(event.getSource())).getScene().getWindow().hide();
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
        //Hide current window, ne fonctionne pas, à voir plus tard
        //((Node)(event.getSource())).getScene().getWindow().hide();

        // New "account" button in the AppWindow
        // TO BE IMPLEMENTED ...
    }
    
    @FXML
    private void handleButtonAccountCurrent(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);
        
        TransactionsWindowController controller = (TransactionsWindowController)ControllerBase.loadFxml(
                "TransactionsWindow.fxml",
                this.mediator
        );
        controller.setFlagAccountType("Current");
        controller.initTransactionsWindowController(this.mediator);
        content.getChildren().setAll(controller.getParent());
        
        ContactWindowController controller2 = (ContactWindowController)ControllerBase.loadFxml(
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
        
        TransactionsWindowController controller = (TransactionsWindowController)ControllerBase.loadFxml(
                "TransactionsWindow.fxml",
                this.mediator
        );
        controller.setFlagAccountType("Savings");
        controller.initTransactionsWindowController(this.mediator);
        content.getChildren().setAll(controller.getParent());

        ContactWindowController controller2 = (ContactWindowController)ControllerBase.loadFxml(
                "ContactWindow.fxml",
                this.mediator
        );
        controller2.setFlagAccountType("Savings");
        controller2.initContactWindowController(this.mediator);
        contact.getChildren().setAll(controller2.getParent());

    }
    
   
    private Mediator mediator = null;
    private EntityManagerFactory emf = null;
}