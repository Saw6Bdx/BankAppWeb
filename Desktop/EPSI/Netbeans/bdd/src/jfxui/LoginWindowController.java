package jfxui;

import db.home.bank.Holder;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class LoginWindowController extends ControllerBase{   
    @FXML private TextField labelLogin;
    @FXML private PasswordField labelPassword;
    
    @Override
    public void initialize(Mediator mediator){
    }
    
    @FXML
    private void handleLoginWindowLogin(ActionEvent event) throws IOException{
        this.emf = Persistence.createEntityManagerFactory("BankAppPU");
        this.mediator = new Mediator(this.emf);
        EntityManager em = this.mediator.createEntityManager();
        TypedQuery<String> qLogin = em.createQuery("SELECT h.login FROM Holder h", String.class);
        List<String> loginList = qLogin.getResultList();
        //holderList.get(i).getLogin();
        
        this.login = labelLogin.getText();
        this.password = labelPassword.getText();
        // Vérifier si login existe dans la base
        if(loginList.contains(this.login)) {
            TypedQuery<Holder> qHolder = em.createQuery("SELECT h FROM Holder h WHERE h.login =:login", Holder.class);
            List<Holder> holderList = qHolder.setParameter("login", this.login).getResultList();
            String passwordHolder = holderList.get(0).getPassword();
            // Login existe, vérifier si le mot de passe correspond au même holder dans la base
            if(this.password.equals(passwordHolder)) {
                AppWindowController controller = (AppWindowController)ControllerBase.loadFxml("AppWindow.fxml", this.mediator);
                controller.setFlagHolder(holderList.get(0).getId());
                controller.initAppWindowController(mediator);
                Scene scene = new Scene(controller.getParent());
                //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                //Hide current window
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            else{
                new Alert(AlertType.ERROR, "Password is invalid.").showAndWait();
            }    
        }
        else{
            new Alert(AlertType.ERROR, "Login doesn't exist.").showAndWait();
        }
    }
 
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
    private String login, password;
}