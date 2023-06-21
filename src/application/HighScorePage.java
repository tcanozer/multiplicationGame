package application;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HighScorePage {
	
	private User currentUser;
	
	
	public HighScorePage(User currentUser) {
		this.currentUser = currentUser;
	}
	
	private Exercise loadExerciseObjectFromFile(Exercise currentExercise) {
		
		String fileName = "Exercise_"+ Integer.toString(currentExercise.getExerciseId()) + ".dat";
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
			currentExercise = (Exercise) inputStream.readObject();
		} catch (EOFException e) {
			System.out.println("The exercises file is empty or corrupted.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return currentExercise;
	}
	
	private void backToExercisesPage(Stage primaryStage) {
		ExercisesPage exercises = new ExercisesPage(this.currentUser);
		exercises.showExerciseList(primaryStage);
	}
	
	/*
	 * Shows regarded exercise's high scores in a table view
	 */
	public void showHighScores(Stage primaryStage,Exercise ex) {
		primaryStage.setTitle("Exercise "+ex.getExerciseId()+" High Scores");
		
		Exercise currentExerciseData = loadExerciseObjectFromFile(ex);

		TableView<Score> tableView = new TableView<>();
		TableColumn<Score, String> userCol = new TableColumn<>("Username");
		userCol.setCellValueFactory(new PropertyValueFactory<>("username"));

		TableColumn<Score, Integer> scoreCol = new TableColumn<>("Score");
		scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

		TableColumn<Score, Integer> totalTimeSpentCol = new TableColumn<>("Total Time Spent(s)");
		totalTimeSpentCol.setCellValueFactory(new PropertyValueFactory<>("totalTimeSpent"));

		TableColumn<Score, Integer> numOfCorrectAnswersCol = new TableColumn<>("Total Correct Answer");
		numOfCorrectAnswersCol.setCellValueFactory(new PropertyValueFactory<>("numOfCorrectAnswers"));


		tableView.getColumns().addAll(userCol, scoreCol, totalTimeSpentCol, numOfCorrectAnswersCol);
		tableView.setItems(FXCollections.observableArrayList(currentExerciseData.getHighScores()));

		VBox vbox = new VBox();
		
		Button backtoExercisesButton = new Button("Back");
		backtoExercisesButton.setOnAction(event -> backToExercisesPage(primaryStage));

	    
	    VBox.setMargin(backtoExercisesButton, new Insets(10));
	    VBox.setMargin(tableView, new Insets(10));

	    
	    vbox.getChildren().addAll(backtoExercisesButton, tableView);


		Scene scene = new Scene(vbox, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
