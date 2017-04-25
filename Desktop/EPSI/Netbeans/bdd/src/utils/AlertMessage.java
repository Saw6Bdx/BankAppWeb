/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.persistence.PersistenceException;

/**
 *
 * @author Mary
 */
public class AlertMessage {
    
    public static void alertMessage(String field, String message) {
        new Alert(
                Alert.AlertType.WARNING,
                String.format("Invalid %s format\n %s",field,message)
        ).showAndWait(); 
    }
    
    public static void processPersistenceException(PersistenceException e) {
        new Alert(Alert.AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
    }
    
}
