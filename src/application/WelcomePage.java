package application;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.HPos;
import javafx.geometry.VPos;


public class WelcomePage {

	private User currentUser;

	public WelcomePage(User currentUser) {
		this.currentUser = currentUser;
	}
	
	public void mainPage(Stage primaryStage) {
		primaryStage.setTitle("Main Page");


		Button exercisesButton = new Button("Exercises");
		exercisesButton.setOnAction(event -> openExercisesPage(primaryStage,this.currentUser));
		
		Button diariesButton = new Button("User Diaries");
		diariesButton.setOnAction(event -> openDiariesPage(primaryStage,this.currentUser));

		Button logoutButton = new Button("Logout ");
		logoutButton.setOnAction(event -> logout(primaryStage));
	

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10));
		gridPane.setVgap(10);
		gridPane.setHgap(10);

		gridPane.add(logoutButton, 2, 0);
		gridPane.add(exercisesButton, 1, 1);
		gridPane.add(diariesButton, 1, 2);



		ColumnConstraints col1 = new ColumnConstraints();
		ColumnConstraints col2 = new ColumnConstraints();
		ColumnConstraints col3 = new ColumnConstraints();
		col1.setPercentWidth(30);
		col2.setPercentWidth(50);
		col3.setPercentWidth(30);
		gridPane.getColumnConstraints().addAll(col1, col2, col3);
		
		GridPane.setHalignment(exercisesButton, HPos.CENTER);
		GridPane.setValignment(exercisesButton, VPos.CENTER);
		GridPane.setHalignment(diariesButton, HPos.CENTER);
		GridPane.setValignment(diariesButton, VPos.CENTER);

		// Create scene and set it on the stage
		Scene scene = new Scene(gridPane, 400, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	private void openExercisesPage(Stage primaryStage,User user) {
		ExercisesPage exercises = new ExercisesPage(user);
		exercises.showExerciseList(primaryStage);
	}
	
	private void openDiariesPage(Stage primaryStage,User user) {
		DiariesPage diaries = new DiariesPage(user);
		if(user.isAdmin()) {
			diaries.selectUserPage(primaryStage);
		}else {
			diaries.selectDiaryPage(primaryStage, user);
		}
	}

	private static void logout(Stage primaryStage) {
		// Navigate to the login page
		Login loginPage = new Login("src/application/users.txt");
		loginPage.loginPage(primaryStage);
	}

}
