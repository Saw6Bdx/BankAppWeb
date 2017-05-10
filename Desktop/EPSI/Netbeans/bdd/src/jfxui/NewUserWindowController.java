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
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import utils.Valid;
import utils.AlertMessage;
import static utils.Password.get_SHA_512_SecurePassword;

/**
 * Class which create a new user account. It writes into the database the new
 * user created.
 *
 * @author Mary
 */
public class NewUserWindowController extends ControllerBase {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker txtBirthday;
    @FXML
    private TextField txtAddressLine1;
    @FXML
    private TextField txtAddressLine2;
    @FXML
    private TextField txtPostCode;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtPwd;
    @FXML
    private PasswordField txtConfirmPwd;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnCancel;

    @Override
    public void initialize(Mediator mediator) {

    }

    @FXML
    private void handleBtnCreate(ActionEvent event) throws IOException {

        String name = this.txtName.getText();
        String firstName = this.txtFirstName.getText();
        String phone = this.txtPhone.getText();
        String email = this.txtEmail.getText();
        String birthday = this.txtBirthday.getEditor().getText();

        String addLine1 = this.txtAddressLine1.getText();
        String addLine2 = this.txtAddressLine2.getText();
        String postCode = this.txtPostCode.getText();
        String city = this.txtCity.getText();

        String login = this.txtLogin.getText();
        String pwd = this.txtPwd.getText();
        String pwdConfirm = this.txtConfirmPwd.getText();

        boolean flagNewPostcode = false;

        // Convert birthday date from dd/MM/yyyy to yyyy-MM-dd
        Date d = new Date(0);
        if (Valid.isValidDate(birthday)) {
            birthday = convFormatDate(birthday);
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
            try {
                d = dateParser.parse(birthday);
            } catch (ParseException ex) {
            }
        } else {
            AlertMessage.alertMessage("date", "Date field cannot be empty");
        }

        // Check the fields
        if (Valid.isValidOnlyLetters(name)) {
            if (Valid.isValidOnlyLetters(firstName)) {
                if (Valid.isValidPhoneNumber(phone)) {
                    if (Valid.isValidEmail(email)) {

                        if (Valid.isValidAddress(addLine1)) {
                            if (Valid.isValidPostCode(postCode)) {
                                if (Valid.isValidOnlyLetters(city)) {

                                    if (Valid.isValidPwd(pwd, pwdConfirm)) {

                                        // All the fields are correct, then it is possible to create objects
                                        if (idPostcode(Integer.parseInt(postCode)) == 0) {
                                            flagNewPostcode = true;
                                        }
                                        Postcode postcode = new Postcode(
                                                idPostcode(Integer.parseInt(postCode)) == 0 ? null : idPostcode(Integer.parseInt(postCode)),
                                                Integer.parseInt(postCode),
                                                city
                                        );
                                        Address address = new Address(null, addLine1);
                                        Holder holder = new Holder(null, name, firstName, login, get_SHA_512_SecurePassword(pwd, "1"));
                                        
                                        // Writing info into the database
                                        try {
                                            EntityManager em = getMediator().createEntityManager();

                                            em.getTransaction().begin();
                                            if (flagNewPostcode) {
                                                em.persist(postcode);
                                            }
                                            address.setIdPostcode(postcode);
                                            if (!addLine2.isEmpty()) {
                                                address.setLine2(addLine2);
                                            }
                                            em.persist(address);
                                            holder.setPhone(phone);
                                            holder.setBirthday(d);
                                            holder.setIdAddress(address);
                                            em.persist(holder);
                                            em.getTransaction().commit();

                                            em.close();
                                        } catch (PersistenceException e) {
                                            AlertMessage.processPersistenceException(e);
                                        }

                                        // Going to the application main page
                                        TitledPane loader = (TitledPane) FXMLLoader.load(getClass().getResource("AppWindow.fxml"));
                                        Scene scene = new Scene(loader);
                                        Stage stage = new Stage();
                                        stage.setScene(scene);
                                        stage.show();

                                        //Close current window
                                        Stage current = (Stage) this.btnCreate.getScene().getWindow();
                                        current.close();

                                    } else {
                                        AlertMessage.alertMessage("password", "password and its confirmation do not match");
                                    }
                                } else {
                                    AlertMessage.alertMessage("city", "Only letters, apostrophe and hyphen allowed");
                                }
                            } else {
                                AlertMessage.alertMessage("postCode", "Cannot be empty");
                            }
                        } else {
                            AlertMessage.alertMessage("First line address", "Cannot be empty");
                        }
                    } else {
                        AlertMessage.alertMessage("e-mail", "Must contains an @");
                    }
                } else {
                    AlertMessage.alertMessage("phone", "Characters allowed : numbers and +");
                }
            } else {
                AlertMessage.alertMessage("first name", "Only letters, hyphen and apostrophe allowed");
            }
        } else {
            AlertMessage.alertMessage("name", "Only letters, hyphen and apostrophe allowed");
        }

    }

    public int idPostcode(int str) {
        int id = 0;
        try {
            EntityManager em = getMediator().createEntityManager();
            TypedQuery<Postcode> qBank = em.createNamedQuery("Postcode.findAll", Postcode.class);
            List<Postcode> postcodeList = qBank.getResultList();

            for (int i = 0; i < postcodeList.size(); i++) {
                if (str == postcodeList.get(i).getPostcode()) {
                    id = postcodeList.get(i).getId();
                }
            }

            em.close();
        } catch (PersistenceException e) {

        }
        return id;
    }

    /**
     * Method which changes the date format
     *
     * @param date in dd/MM/yyyy format
     * @return date in yyyy-mm-dd format
     */
    private String convFormatDate(String date) {
        String[] parts = date.split("/");
        String result = "";
        for (int i = parts.length - 1; i >= 0; i--) {
            result = result.concat(parts[i]);
            if (i != 0) {
                result = result.concat("-");
            }
        }
        return result;
    }

    /**
     * When the user clicks on the Cancel button, the application goes back to
     * the Login Window and closes the New User Window.
     */
    @FXML
    private void handleBtnCancel(ActionEvent event) throws IOException {

        //Close current window
        Stage current = (Stage) this.btnCancel.getScene().getWindow();
        current.close();

    }

}
