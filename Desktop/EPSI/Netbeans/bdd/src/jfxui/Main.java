package jfxui;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TitledPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
                    TitledPane root = (TitledPane)FXMLLoader.load(getClass().getResource("LoginWindow.fxml"));
                    Scene scene = new Scene(root);
                    primaryStage.setScene(scene);
                    primaryStage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}