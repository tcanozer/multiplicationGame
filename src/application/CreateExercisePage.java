package application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CreateExercisePage {
	
	private List<Exercise> exercises;
	private User currentUser;

	
	public CreateExercisePage(User currentUser, List<Exercise> exercises) {
		this.currentUser = currentUser;
		this.exercises = exercises;
	}

	/*
	 * The page for getting input for generating random exercises. Only parents can
	 * access this page.
	 */


	public void showInputForm(Stage primaryStage) {
		primaryStage.setTitle("Add New Exercise");

		Label minValueLabel = new Label("Minimum Value:");
		TextField minValueField = new TextField();

		Label maxValueLabel = new Label("Maximum Value:");
		TextField maxValueField = new TextField();

		Label numOfQuestionsLabel = new Label("Number of Questions:");
		TextField numOfQuestionsField = new TextField();

		Button backToExercisesButton = new Button("Back ");
		backToExercisesButton.setOnAction(event -> backToExercisesPage(primaryStage));

		Label messageLabel = new Label();

		Button generateButton = new Button("Generate Exercise");
		generateButton.setOnAction(event -> {

			int minValue = Integer.parseInt(minValueField.getText());
			int maxValue = Integer.parseInt(maxValueField.getText());
			int numOfQuestions = Integer.parseInt(numOfQuestionsField.getText());

			Boolean bool = generateExercise(minValue, maxValue, numOfQuestions);
			if (bool) {
				messageLabel.setText("Exercise Created");
				backToExercisesPage(primaryStage);
			} else {
				messageLabel.setText("Max Value cannot be less than Min Value");
			}
		});

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10));
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.add(backToExercisesButton, 0, 0);
		gridPane.add(minValueLabel, 0, 1);
		gridPane.add(minValueField, 1, 1);
		gridPane.add(maxValueLabel, 0, 2);
		gridPane.add(maxValueField, 1, 2);
		gridPane.add(numOfQuestionsLabel, 0, 3);
		gridPane.add(numOfQuestionsField, 1, 3);
		gridPane.add(generateButton, 1, 4);
		gridPane.add(messageLabel, 0, 5);
		GridPane.setColumnSpan(messageLabel, 2);

		Scene scene = new Scene(gridPane, 400, 300);
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	// Method for saving exercises list
	private void saveExercisesToFile() {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("exercises.dat"))) {
			outputStream.writeObject(exercises);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Gets a:min value, b:max value and number of question parameters 1-Create an
	 * random exercise ex 2-Add this exercise to exercise list 3-Save this exercise
	 * list to a file via serialization
	 */
	public boolean generateExercise(int minValue, int maxValue, int numOfQuestions) {
		if (minValue > maxValue) {
			return false;
		}
		int id = exercises.size() +1;
		Exercise ex = new Exercise(id,minValue, maxValue, numOfQuestions);
		exercises.add(ex);
		saveExercisesToFile();
		return true;
	}
	
	private void backToExercisesPage(Stage primaryStage) {
		ExercisesPage exercises = new ExercisesPage(this.currentUser);
		exercises.showExerciseList(primaryStage);
	}



}
