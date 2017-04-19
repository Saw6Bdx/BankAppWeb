
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
    @FXML
    private AnchorPane content;
    
    @Override
    public void initialize(Mediator mediator){
        /*try{
            //Le mettre dans 'content'
            //content.getChildren().setAll(loadFxml("TransactionsWindow.fxml"));
	}
	catch(IOException e){
            
	}*/
    }

    @FXML
    private void handleMenuFileChangeUser(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);
        ControllerBase controller = ControllerBase.loadFxml("AppWindow.fxml", mediator);
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
        
        Scene scene = new Scene(ControllerBase.loadFxml("NewUserWindow.fxml", mediator));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        //Hide current window, ne fonctionne pas, à voir plus tard
       // ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    private void handleMenuEditNewAccount(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);
        
        Scene scene = new Scene(ControllerBase.loadFxml("NewAccountWindow.fxml", mediator));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        //Hide current window, ne fonctionne pas, à voir plus tard
        ((Node)(event.getSource())).getScene().getWindow().hide();

        // New "account" button in the AppWindow
        // TO BE IMPLEMENTED ...
    }
    
    @FXML
    private void handleButtonAccountCurrent(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);
        
        //Scene scene = new Scene(ControllerBase.loadFxml("TransactionsWindow.fxml", mediator));
        content.getChildren().setAll(loadFxml("TransactionsWindow.fxml"));// appeler le TransactionsWindow du compte correspondant au bouton
        //Stage stage = new Stage();
        //stage.setScene(scene);
        //stage.show();
    }
    
    @FXML
    private void handleButtonAccountSaving(ActionEvent event) throws IOException {
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);
        
        Scene scene = new Scene(ControllerBase.loadFxml("TransactionsWindow.fxml", mediator));
        // appeler le TransactionsWindow du compte correspondant au bouton
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    private Mediator mediator = null;
    private EntityManagerFactory emf = null;
}