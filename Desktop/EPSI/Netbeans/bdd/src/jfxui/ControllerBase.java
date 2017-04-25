/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author Guest
 */
public abstract class ControllerBase {
    
    private Mediator mediator = null;
    private Parent parent = null;
    
    public abstract void initialize(Mediator mediator);
    
    public void initMediator(Mediator mediator) {
        if (mediator == null) {
            throw new NullPointerException("mediator cannot be null");
        }
        if (this.mediator != null) {
            throw new UnsupportedOperationException("Cannot initMediator twice");
        }
        this.mediator = mediator;
        this.initialize(mediator);
    }
    
    public Mediator getMediator() {
        return this.mediator;
    }
    
    public Parent getParent() {
        return this.parent;
    } 
    
    public ControllerBase loadFxml(String fxml) throws IOException {
        return loadFxml(fxml, this.mediator);
    }
    
    public static ControllerBase loadFxml(String fxml, Mediator mediator) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
	Parent root = (Parent)loader.load();
	ControllerBase controller = loader.getController();
        
        controller.parent = root;
        controller.initMediator(mediator);
        return controller;
    }
    
}
