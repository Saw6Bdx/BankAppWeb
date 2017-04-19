package jfxui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

/**
 *  
 * @author Mary
 */
public class NewAccountWindow_page3Controller extends ControllerBase {
    
    @FXML private TextField txtAccountManagerName;
    @FXML private TextField txtAccountManagerFirstName;
    @FXML private TextField txtAccountManagerPhone;
    @FXML private TextField txtAccountManagerEmail;
    @FXML private DatePicker txtAccountManagerAssignementDate;
    @FXML private Button btnPrevious;
    @FXML private Button btnCreate;
   
    @Override
    public void initialize(Mediator mediator) {
    }
    
    @FXML
    private void handleBtnCreate(ActionEvent event) throws IOException {
    
        String accountManagerName = txtAccountManagerName.getText();
        String accountManagerFirstName = txtAccountManagerFirstName.getText();
        String accountManagerPhone = txtAccountManagerPhone.getText();
        String accountManagerEmail = txtAccountManagerEmail.getText();
        String accountManagerAssignementDate = txtAccountManagerAssignementDate.getEditor().getText();
        
        // Check the fields
        /*if ( Valid.isValidLetters(accountManagerName, "name") ) {
            if ( Valid.isValidLetters(accountManagerFirstName, "first name") ) {
                if ( Valid.isValidPhoneNumber(phone) ) {
                    if ( Valid.isValidEmail(email) ) {
                        
                        
                        
                    }
                }
            }
        }*/
        
        //Close current window
        Stage current = (Stage)btnCreate.getScene().getWindow();
        current.close();
      
    }
  
    private void alertMessage(String field, String message) {
        new Alert(
                Alert.AlertType.WARNING,
                String.format("Invalid %s format\n %s",field,message)
            ).showAndWait();
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