package jfxui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class LoginWindowController extends ControllerBase{   
    @FXML private TextField labelLogin;
    @FXML private PasswordField labelPassword;
    
    @Override
    public void initialize(Mediator mediator){
    }
    
    @FXML
    private void handleLoginWindowLogin(ActionEvent event) throws IOException{
        if(labelLogin.getText().equals("Login") && labelPassword.getText().equals("0000")){
            this.emf = Persistence.createEntityManagerFactory("BankAppPU");
            this.mediator = new Mediator(this.emf);
            
            ControllerBase controller = (AppWindowController)ControllerBase.loadFxml("AppWindow.fxml", mediator);
            Scene scene = new Scene(controller.getParent());
            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            //Hide current window
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        else{
            new Alert(AlertType.ERROR, "Login or pwd are invalid").showAndWait();
        }
    }
/*@FXML
private void handleMenuFileQuit(ActionEvent event){
    Alert alert = new Alert(AlertType.CONFIRMATION, "Vous êtes sûr de vouloir quitter ?", ButtonType.OK, ButtonType.CANCEL);
    Optional<ButtonType> result = alert.showAndWait();
    if(result.isPresent() && result.get() == ButtonType.OK){
    Platform.exit();
    }
}*/
        
    @FXML
    private void handleLoginWindowCreate(ActionEvent event) throws IOException{
            this.emf = Persistence.createEntityManagerFactory("BankAppPU");
            this.mediator = new Mediator(this.emf);
            
            ControllerBase controller = (NewUserWindowController)ControllerBase.loadFxml("NewUserWindow.fxml", mediator);
            Scene scene = new Scene(controller.getParent());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            //Hide current window
            ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    private Mediator mediator = null;
    private EntityManagerFactory emf = null;
}