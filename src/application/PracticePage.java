package application;

import java.io.FileOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class PracticePage {

	private User currentUser;
	private Exercise currentExercise;
	private int currentQuestionIndex;
	private int correctAnswerCounter;
	private Label timerLabel;
	private long startTime;
	private AnimationTimer timer;

	public PracticePage(User currentUser, Exercise currentExercise) {
		this.currentUser = currentUser;
		this.currentExercise = currentExercise;
		this.currentQuestionIndex = 0;
		this.correctAnswerCounter = 0;
		timerLabel = new Label();
		startTime = System.currentTimeMillis();
		startTimer();
	}

	public Label getTimerLabel() {
		return timerLabel;
	}

	public AnimationTimer getTimer() {
		return timer;
	}

	private void saveExerciseToFile() {
		String fileName = "Exercise_" + Integer.toString(currentExercise.getExerciseId()) + ".dat";
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
			outputStream.writeObject(currentExercise);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * When the user start an exercise program creates a new diary(exerciseDiary)
	 * and diary's first page(diaryEntry) which contain infos about user's
	 * answer,answer time question etc. then adds this page to this diary. and
	 * navigate to showExercise method which shows questions in the selected
	 * exercise one by one
	 * 
	 */
	public void initializeAndStart(Stage primaryStage) {
		Diary exerciseDiary = new Diary(this.currentUser, currentExercise);
		AnswerData diaryEntry = new AnswerData(0);
		exerciseDiary.addAnswerData(diaryEntry);
		diaryEntry.setQuestionStartTime();
		showExercise(primaryStage, exerciseDiary);
	}

	private void saveUserToFile() {

		String fileName = this.currentUser.getUsername() + ".dat";
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
			outputStream.writeObject(this.currentUser);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startTimer() {
		this.timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				long elapsedTime = System.currentTimeMillis() - startTime;
				updateTimerLabel(elapsedTime);
			}
		};
		timer.start();
	}

	public void updateTimerLabel(long elapsedTime) {
		long hours = (elapsedTime / (1000 * 60 * 60)) % 24;
		long minutes = (elapsedTime / (1000 * 60)) % 60;
		long seconds = (elapsedTime / 1000) % 60;

		String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		timerLabel.setText("Time: " + formattedTime);
	}

	/*
	 * Control for input type . It does not accept input types other than integer
	 */
	public boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean isCorrect(int result, int answer) {
		return result == answer;
	}

	public void showScorePage(Stage primaryStage, Score score) {
		primaryStage.setTitle("Score");

		Label messageLabel = new Label();
		messageLabel.setText("Score :  " + score.getScore());
		messageLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

		Button highScoresButton = new Button("High Scores for This Exercise");
		highScoresButton.setOnAction(event -> toHighScoresPage(primaryStage, currentExercise));

		Button toExercisesPageButton = new Button("Exercise List");
		toExercisesPageButton.setOnAction(event -> toExercisesPage(primaryStage));
		
		Button backToWelcome = new Button("Main Page ");
		backToWelcome.setOnAction(event -> backToWelcome(this.currentUser, primaryStage));

		VBox vbox = new VBox(15);
		vbox.setAlignment(Pos.CENTER);

		// Add the elements to the VBox
		vbox.getChildren().addAll(messageLabel, timerLabel, highScoresButton, toExercisesPageButton,backToWelcome);

		// Create the scene and set it on the stage
		Scene scene = new Scene(vbox, 400, 200);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Score");
		primaryStage.show();

	}

	/*
	 * This method shows questions in the selected exercise one by one till the
	 * currentQuestionIndex is equal to number of total questions in the
	 * exercise.After each answer it adds the user's answer data (answer, time
	 * question etc) to the created diary. When there is no more exercise it adds
	 * the created diary to the user's diary list and save user object. Then
	 * navigates to score page.
	 */
	public void showExercise(Stage primaryStage, Diary exerciseDiary) {
		primaryStage.setTitle("Question " + (currentQuestionIndex + 1));

		// Exercise exercise = currentExercise.get(currentExerciseIndex);
		Question currentQuestion = this.currentExercise.getQuestions().get(currentQuestionIndex);
		Label questionLabel = new Label("Question " + (currentQuestionIndex + 1) + ":   "
				+ currentQuestion.getFirstValue() + "  *  " + currentQuestion.getSecondValue() + "  =  ? ");
		TextField answerField = new TextField();

		Label messageLabel = new Label();

		Button nextButton = new Button("Next");
		nextButton.setOnAction(event -> {
			String answer = answerField.getText();
			if (isInteger(answer)) {
				int userAnswer = Integer.parseInt(answer);
				int correctResult = currentQuestion.getResult();
				boolean bool = isCorrect(correctResult, userAnswer);
				if (bool) {
					correctAnswerCounter++;
				}

				String questionAsString = Integer.toString(currentQuestion.getFirstValue()) + "*" + Integer.toString(currentQuestion.getSecondValue());
				AnswerData data = exerciseDiary.getAnswerDatas().get(currentQuestionIndex);
				data.setIsCorrect(bool);
				data.setQuestionEndTime();
				data.setCorrectResult(correctResult);
				data.setUserAnswer(userAnswer);
				data.setQuestion(questionAsString);
			


				answerField.clear();

				// Move to the next question
				currentQuestionIndex++;
				if (currentQuestionIndex < currentExercise.getNumOfQuestions()) {
					AnswerData nextDiaryEntry = new AnswerData(currentQuestionIndex);
					nextDiaryEntry.setQuestionStartTime();
					exerciseDiary.addAnswerData(nextDiaryEntry);


					showExercise(primaryStage, exerciseDiary); // Show the next question
				} else {
					
					// No more questions, save score to the high score table and the answers etc to diary

					long endTime = System.currentTimeMillis();
					this.timer.stop();
					
					int totalTime = (int) (endTime - startTime) / 1000;

					Score score = new Score(this.currentUser.getUsername(), this.correctAnswerCounter, totalTime);
					exerciseDiary.setScore(score.getScore());
					exerciseDiary.setEndTime();
					this.currentExercise.addHighScore(score);
					this.currentUser.addDiary(exerciseDiary);
					saveUserToFile();
					saveExerciseToFile();

					showScorePage(primaryStage, score);

					
				}
			} else {
				messageLabel.setText("Answer must be an integer !");
			}
		});

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10));
		gridPane.setVgap(10);
		gridPane.setHgap(10);

		gridPane.add(timerLabel, 2, 0);
		gridPane.add(questionLabel, 1, 1);
		gridPane.add(answerField, 1, 2);
		gridPane.add(nextButton, 1, 3);
		gridPane.add(messageLabel, 1, 4);

		ColumnConstraints col1 = new ColumnConstraints();
		ColumnConstraints col2 = new ColumnConstraints();
		ColumnConstraints col3 = new ColumnConstraints();
		col1.setPercentWidth(35);
		col2.setPercentWidth(60);
		col3.setPercentWidth(35);
		gridPane.getColumnConstraints().addAll(col1, col2, col3);
		GridPane.setColumnSpan(questionLabel, 2);

		GridPane.setHalignment(nextButton, HPos.CENTER);
		GridPane.setValignment(nextButton, VPos.CENTER);

		// Create scene and set it on the stage
		Scene scene = new Scene(gridPane, 400, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void toHighScoresPage(Stage primaryStage, Exercise currentExercise) {
		HighScorePage highScorePage = new HighScorePage(currentUser);
		highScorePage.showHighScores(primaryStage, currentExercise);
	}

	private void toExercisesPage(Stage primaryStage) {
		ExercisesPage exercises = new ExercisesPage(this.currentUser);
		exercises.showExerciseList(primaryStage);
	}
	
	private void backToWelcome(User currentUser, Stage primaryStage) {
		WelcomePage welcomePage = new WelcomePage(currentUser);
		welcomePage.mainPage(primaryStage);
	}

}
