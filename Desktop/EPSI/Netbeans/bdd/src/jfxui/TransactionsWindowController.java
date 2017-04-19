package jfxui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

/**
 *
 * @author Nicolas
 */
public class TransactionsWindowController extends ControllerBase{
    
    @Override
    public void initialize(Mediator mediator){
    }
	
    /*@FXML
    private void handleLoginWindowLogin(ActionEvent event) throws IOException{
        if(labelLogin.getText().equals("Login") && labelPassword.getText().equals("26929999")){
            TitledPane loader = (TitledPane)FXMLLoader.load(getClass().getResource("FXML.fxml"));
            Scene scene = new Scene(loader);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            //Hide current window
            ((Node)(event.getSource())).getScene().getWindow().hide();
            //System.out.print("Pwd OK");
            //System.out.print("Login OK");
        }
        else{
            new Alert(Alert.AlertType.ERROR, "Login or pwd are invalid").showAndWait();
        }
    } */      

}