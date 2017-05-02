package jfxui;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
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
    
    /*public static void main(String[] args) throws UnsupportedEncodingException {
        String pwd = get_SHA_512_SecurePassword("12345123456789","1");
        String pwd2 = get_SHA_512_SecurePassword("12345123456789","1");
        System.out.println(pwd);
        System.out.println(pwd.length());
        System.out.println(pwd.equals(pwd2));
    }*/
}