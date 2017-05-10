/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Mary
 */
public class HelpContactWindowController extends ControllerBase {

    @FXML Button btnOK;
    
    @Override
    public void initialize(Mediator mediator) {
        
    }
    
    @FXML
    private void handleBtnOK(ActionEvent event) throws IOException {

        //Close current window
        Stage current = (Stage)this.btnOK.getScene().getWindow();
        current.close();
        
    }
    
}
