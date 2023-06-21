package application;
	
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	 @Override
	    public void start(Stage primaryStage) {
	        String path = "src/application/users.txt";
	        Login login = new Login(path);
	        login.loginPage(primaryStage);
	    }
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
