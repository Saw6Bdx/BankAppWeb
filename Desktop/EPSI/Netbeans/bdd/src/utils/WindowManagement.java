/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import static javafx.scene.input.DataFormat.URL;
import javafx.stage.Stage;

/**
 *
 * @author Mary
 */
public class WindowManagement {
    
    public static void closeCurrentWindow(Button btn) {
        //Close current window
        Stage current = (Stage) btn.getScene().getWindow();
        current.close();
    }

    /*public static void goToAnotherWindow(String WindowNameFXML) throws IOException {
        TitledPane loader = (TitledPane) FXMLLoader.load(getClass().getResource(WindowNameFXML));
        Scene scene = new Scene(loader);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    public static void goToAnotherWindow(URL WindowNameFXML) throws IOException {
        TitledPane loader = (TitledPane) FXMLLoader.load(WindowNameFXML);
        Scene scene = new Scene(loader);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }*/
    
}
