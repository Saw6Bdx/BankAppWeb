package jfxui;

import db.home.bank.Address;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import db.home.bank.Holder;
import db.home.bank.Postcode;
import utils.Valid;
import utils.AlertMessage;
import static utils.Password.get_SHA_512_SecurePassword;

/**
 *  
 * @author Mary
 */
public class NewUserWindowController extends ControllerBase {
    
    @FXML private TextField txtName;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtPhone;
    @FXML private TextField txtEmail;
    @FXML private DatePicker txtBirthday;
    @FXML private TextField txtAddressLine1;
    @FXML private TextField txtAddressLine2;
    @FXML private TextField txtPostCode;
    @FXML private TextField txtCity;
    @FXML private TextField txtLogin;
    @FXML private PasswordField txtPwd;
    @FXML private PasswordField txtConfirmPwd;
    @FXML private Button btnCreate;
    @FXML private Button btnCancel;
    
    @Override
    public void initialize(Mediator mediator) {
        
    }
    
    @FXML
    private void handleBtnCreate(ActionEvent event) throws IOException {
    
        String name = txtName.getText();
        String firstName = txtFirstName.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String birthday = txtBirthday.getEditor().getText();
        
        String addLine1 = txtAddressLine1.getText();
        String addLine2 = txtAddressLine2.getText();
        String postCode = txtPostCode.getText();
        String city = txtCity.getText();
        
        String login = txtLogin.getText();
        String pwd = txtPwd.getText();
        String pwdConfirm = txtConfirmPwd.getText();
        
        // Convert birthday date from dd/MM/yyyy to yyyy-MM-dd
        if ( Valid.isValidDate(birthday) ) {
            birthday = convFormatDate(birthday);
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = dateParser.parse(birthday);
            } catch (ParseException ex) {
            }
        }
        else {
            AlertMessage.alertMessage("date","Date field cannot be empty");
        }
        
        // Check the fields
        if ( Valid.isValidOnlyLetters(name) ) {
            if ( Valid.isValidOnlyLetters(firstName) ) {
                if ( Valid.isValidPhoneNumber(phone) ) {
                    if ( Valid.isValidEmail(email) ) {
                        
                        if ( Valid.isValidAddress(addLine1) ) {
                            if ( Valid.isValidPostCode(postCode) ) {
                                if ( Valid.isValidOnlyLetters(city) ) {
                                    
                                    if (Valid.isValidPwd(pwd, pwdConfirm)) {
                            
                                        // All the fields are correct, then it is possible to create objects
                                        Postcode postcode = new Postcode(null,Integer.parseInt(postCode),city);
                                        Address address = new Address(null,addLine1);
                                        Holder holder = new Holder(null,name,firstName,login,get_SHA_512_SecurePassword(pwd,"1"));
    
                                        // Writing info into the database
                                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BankAppPU");
                                        EntityManager em = emf.createEntityManager();

                                        em.getTransaction().begin(); 
                                        em.persist(postcode);
                                        address.setIdPostcode(postcode);
                                        if (!addLine2.isEmpty()) {
                                            address.setLine2(addLine2);
                                        }    
                                        em.persist(address);
                                        holder.setPhone(phone);
                                        holder.setIdAddress(address);
                                        em.persist(holder);
                                        em.getTransaction().commit();

                                        // Going to the application main page
                                        TitledPane loader = (TitledPane)FXMLLoader.load(getClass().getResource("AppWindow.fxml"));
                                        Scene scene = new Scene(loader);
                                        Stage stage = new Stage();
                                        stage.setScene(scene);
                                        stage.show();

                                        //Close current window
                                        Stage current = (Stage)btnCreate.getScene().getWindow();
                                        current.close();
                        
                                    }
                                    else {
                                        AlertMessage.alertMessage("password","password and its confirmation do not match");
                                    }
                                }
                                else {
                                    AlertMessage.alertMessage("city","Only letters, apostrophe and hyphen allowed");
                                }
                            }
                            else {
                                AlertMessage.alertMessage("postCode","Cannot be empty");
                            }
                        }
                        else {
                            AlertMessage.alertMessage("First line address","Cannot be empty");
                        }
                    }
                    else {
                        AlertMessage.alertMessage("e-mail","Must contains an @");
                    }
                }
                else {
                    AlertMessage.alertMessage("phone","Characters allowed : numbers and +");
                }
            }
            else {
                AlertMessage.alertMessage("first name","Only letters, hyphen and apostrophe allowed");
            }
        } 
        else {
            AlertMessage.alertMessage("name","Only letters, hyphen and apostrophe allowed");
        }
        
    }
    
    
    /**
     * Method which changes the date format
     * @param date in dd/MM/yyyy format
     * @return date in yyyy-mm-dd format
     */
    private String convFormatDate(String date) {
        String[] parts = date.split("/");
        String result = "";
        for (int i = parts.length - 1 ; i >= 0 ; i-- ) {
            result = result.concat(parts[i]);
            if (i != 0 ) {
                result = result.concat("-");
            }
        }
        return result;
    }
    
    @FXML
    /**
     * When the user clicks on the Cancel button, the application goes back to the Login Window 
     * and closes the New User Window.
     */
    private void handleBtnCancel(ActionEvent event) throws IOException {

        //Close current window
        Stage current = (Stage)btnCancel.getScene().getWindow();
        current.close();
        
        // Going back to the previous stage        
        /*ControllerBase controller = ControllerBase.loadFxml("LoginWindow.fxml", getMediator());
        Scene scene = new Scene(controller.getParent());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();*/
        
    }
        
}