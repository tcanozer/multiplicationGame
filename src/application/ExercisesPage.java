package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.EOFException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class ExercisesPage {

	private List<Exercise> exercises;
	private User currentUser;

	public ExercisesPage(User currentUser) {
		this.currentUser = currentUser;
		this.exercises = new ArrayList<Exercise>();
	}

	// Method for loading saved exercises file
	private List<Exercise> loadExercisesFromFile() {
		List<Exercise> exercises = new ArrayList<>();
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("exercises.dat"))) {
			exercises = (List<Exercise>) inputStream.readObject();
		} catch (EOFException e) {
			System.out.println("The exercises file is empty or corrupted.");
		} catch (FileNotFoundException e) {
			System.out.println("Exercises archive file not found.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return exercises;
	}

	public List<Exercise> getExercises() {
		return exercises;
	}
	

	private Exercise loadExerciseObjectFromFile(Exercise currentExercise) {

		String fileName = "Exercise_" + Integer.toString(currentExercise.getExerciseId()) + ".dat";
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
			currentExercise = (Exercise) inputStream.readObject();
		} catch (EOFException e) {
			System.out.println("The slected exercise's file is empty or corrupted.");
		} catch (FileNotFoundException e) {
			System.out.println("Selected exercise's data archive file not found.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return currentExercise;
	}

	/*
	 * This method generating components for exercises to show them on the screen
	 */
	public void listExercises(GridPane gridPane, Stage primaryStage) {
		int row = 1;

		for (Exercise currentExercise : this.exercises) {
			Label exerciseLabel = new Label("Exercise " + currentExercise.getExerciseId() + "("
					+ currentExercise.getMinValue() + " - " + currentExercise.getMaxValue() + ")");
			Button startButton = new Button("Start");
			startButton.setOnAction(event -> startExercise(this.currentUser, currentExercise, primaryStage));

			Button highScoresButton = new Button("High Scores");
			highScoresButton.setOnAction(event -> toHighScoresPage(primaryStage, currentExercise));

			// Start button here navigates to Practice Page to start to solve selected
			// exercise .And the High Scores button navigates to High Scores page of
			// regarded exercise.

			gridPane.add(exerciseLabel, 0, row);
			gridPane.add(startButton, 1, row);
			gridPane.add(highScoresButton, 2, row);

			row++;
		}
	}

	public void showExerciseList(Stage primaryStage) {
		// It loads generated exercises(if there is) from file
		this.exercises = loadExercisesFromFile();

		primaryStage.setTitle("Exercise Selection");

		Button backToWelcome = new Button("Back ");
		backToWelcome.setOnAction(event -> backToWelcome(this.currentUser, primaryStage));

		// If the user is admin(parent) can see this Create Exercise Buttton and
		// can navigate that page.
		Button createExerciseButton = null;
		if (this.currentUser.isAdmin()) {
			createExerciseButton = new Button("Create Exercise");
			createExerciseButton.setOnAction(event -> toCreateExercisePage(primaryStage));
		}

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10));
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.add(backToWelcome, 0, 0);
		if (createExerciseButton != null) {
			gridPane.add(createExerciseButton, 3, 0);

		}

		listExercises(gridPane, primaryStage);

		ColumnConstraints col1 = new ColumnConstraints();
		ColumnConstraints col2 = new ColumnConstraints();
		ColumnConstraints col3 = new ColumnConstraints();

		col1.setPercentWidth(30);
		col2.setPercentWidth(15);
		col3.setPercentWidth(25);

		gridPane.getColumnConstraints().addAll(col1, col2, col3);

		Scene scene = new Scene(gridPane, 400, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void startExercise(User user, Exercise currentExercise, Stage primaryStage) {
		Exercise exerciseData = loadExerciseObjectFromFile(currentExercise);
		PracticePage practicePage = new PracticePage(currentUser, exerciseData);
		practicePage.initializeAndStart(primaryStage);
	}

	private void backToWelcome(User currentUser, Stage primaryStage) {
		WelcomePage welcomePage = new WelcomePage(currentUser);
		welcomePage.mainPage(primaryStage);
	}

	private void toHighScoresPage(Stage primaryStage, Exercise currentExercise) {
		HighScorePage highScorePage = new HighScorePage(currentUser);
		highScorePage.showHighScores(primaryStage, currentExercise);
	}

	private void toCreateExercisePage(Stage primaryStage) {
		//this.exercises = loadExercisesFromFile();

		CreateExercisePage createExercisePage = new CreateExercisePage(currentUser, this.exercises);
		createExercisePage.showInputForm(primaryStage);
	}
}
