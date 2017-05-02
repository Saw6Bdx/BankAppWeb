package jfxui;

import db.home.bank.Category;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import utils.AlertMessage;

/**
 *
 * @author Mary
 */
public class NewCategoryWindowController extends ControllerBase {

    @FXML
    private TextField txtCategory;
    @FXML
    private CheckBox checkValue;
    @FXML
    private ChoiceBox<Category> setCategoriesName;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnCancel;

    @Override
    public void initialize(Mediator mediator) {
        
    }

    @FXML
    private void handleBtnCreate(ActionEvent event) throws IOException, InterruptedException {

        String categoryName = this.txtCategory.getText();
        Category subCategoryName;

        try {
            // Loading categories field
            EntityManager em = getMediator().createEntityManager();
            TypedQuery<Category> qCategory = em.createNamedQuery("Category.findAll", Category.class);
            List<Category> categoryList = qCategory.getResultList();

            // Check if the category does not already exist
            boolean flagCategoryExist = false;
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryName.equals(categoryList.get(i).getLabel())) {
                    // If it exists, advise the user and close this window
                    flagCategoryExist = true;
                    AlertMessage.alertMessage("category", "Already exists in the database");
                }
            }
            
            // If the category does not exist, writing it into the database
            if (!flagCategoryExist) {
                Category categoryBdd = new Category(null, categoryName);
                // Check if the category will be a sub-category
                if (this.checkValue.isSelected()) {
                    subCategoryName = this.setCategoriesName.getValue();
                    categoryBdd.setIdLabel(subCategoryName);
                }
                em.getTransaction().begin();
                em.persist(categoryBdd);
                em.getTransaction().commit();
                em.close();
            }

            closeCurrentWindow(this.btnCreate);
            goToAnotherWindow("AppWindow.fxml"); // going to the application main page
            //WindowManagement.goToAnotherWindow(getClass().getResource("AppWindow.fxml"));

        } catch (PersistenceException e) {
            this.btnCreate.setDisable(true);
            AlertMessage.processPersistenceException(e);
        }

    }

    @FXML
    private void handleCheckBox(ActionEvent event) {

        try {
            // Loading categories field
            EntityManager em = getMediator().createEntityManager();
            TypedQuery<Category> qCategory = em.createNamedQuery("Category.findAll", Category.class);
            List<Category> categoryList = qCategory.getResultList();
            // Set categories name
            this.setCategoriesName.setItems(FXCollections.observableArrayList(categoryList));

        } catch (PersistenceException e) {
            //this.btnCreate.setDisable(true);
            AlertMessage.processPersistenceException(e);
        }

    }

    @FXML
    /**
     * When the user clicks on the Cancel button, the application goes back to
     * the Login Window and closes the New User Window.
     */
    private void handleBtnCancel(ActionEvent event) throws IOException {

        closeCurrentWindow(btnCancel);
        goToAnotherWindow("LoginWindow.fxml");

    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!! Ces fonctions pourraient être déplacés dans une classe du package utils 
    private void closeCurrentWindow(Button btn) {
        //Close current window
        Stage current = (Stage) btn.getScene().getWindow();
        current.close();
    }

    private void goToAnotherWindow(String WindowNameFXML) throws IOException {
        TitledPane loader = (TitledPane) FXMLLoader.load(getClass().getResource(WindowNameFXML));
        Scene scene = new Scene(loader);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}
