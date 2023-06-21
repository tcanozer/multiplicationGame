package application;

import java.io.EOFException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class DiariesPage {

	// private List<User> users;
	private User currentUser;
	private List<User> userList;

	public DiariesPage(User user) {
		this.currentUser = user;
		this.userList = loadUserListFromFile();
	}

	public void initPage() {

	}

	private List<User> loadUserListFromFile() {
		List<User> users = new ArrayList<>();
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("users.dat"))) {
			users = (List<User>) inputStream.readObject();
		} catch (EOFException e) {
			System.out.println("The exercises file is empty or corrupted.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return users;
	}

	private User loadUserObjectFromFile(User selectedUser) {
		try (ObjectInputStream inputStream = new ObjectInputStream(
				new FileInputStream(selectedUser.getUsername() + ".dat"))) {
			selectedUser = (User) inputStream.readObject();
		} catch (EOFException e) {
			System.out.println("The exercises file is empty or corrupted.");
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("There is no saved diary file for this User");
		}
		return selectedUser;
	}

	public void resultPage(Stage primaryStage, Diary exerciseDiary, User selectedUser) {
		primaryStage.setTitle("Result Page");

		TableView<AnswerData> tableView = new TableView<>();
		TableColumn<AnswerData, Integer> questionNumberCol = new TableColumn<>("Question Number");
		questionNumberCol.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));

		TableColumn<AnswerData, String> question = new TableColumn<>("Question");
		question.setCellValueFactory(new PropertyValueFactory<>("question"));

		TableColumn<AnswerData, String> startTimeCol = new TableColumn<>("Start Time");
		startTimeCol.setCellValueFactory(new PropertyValueFactory<>("questionStartTimeFormatted"));

		TableColumn<AnswerData, String> endTimeCol = new TableColumn<>("End Time");
		endTimeCol.setCellValueFactory(new PropertyValueFactory<>("questionEndTimeFormatted"));

		TableColumn<AnswerData, Long> spentTime = new TableColumn<>("Time Spent");
		spentTime.setCellValueFactory(new PropertyValueFactory<>("spentTime"));

		TableColumn<AnswerData, Integer> userAnswerCol = new TableColumn<>("User Answer");
		userAnswerCol.setCellValueFactory(new PropertyValueFactory<>("userAnswer"));

		TableColumn<AnswerData, Integer> correctResultCol = new TableColumn<>("Correct Result");
		correctResultCol.setCellValueFactory(new PropertyValueFactory<>("correctResult"));

		TableColumn<AnswerData, Boolean> isCorrectCol = new TableColumn<>("Is Correct");
		isCorrectCol.setCellValueFactory(new PropertyValueFactory<>("isCorrect"));

		tableView.getColumns().addAll(questionNumberCol, question, userAnswerCol, correctResultCol, isCorrectCol,
				spentTime, startTimeCol, endTimeCol);
		tableView.setItems(FXCollections.observableArrayList(exerciseDiary.getAnswerDatas()));

		Button backtoExercisesButton = new Button("Back");
		backtoExercisesButton.setOnAction(event -> selectDiaryPage(primaryStage, selectedUser));

		Button saveAsCSVButton = new Button("Save As CSV");
		saveAsCSVButton.setOnAction(event -> saveAsCSV(exerciseDiary));

		// Label = messageLabel

		HBox buttonsContainer = new HBox(backtoExercisesButton, saveAsCSVButton);
		buttonsContainer.setAlignment(Pos.CENTER);
		buttonsContainer.setSpacing(10);
		buttonsContainer.setPadding(new Insets(10));

		VBox vbox = new VBox(buttonsContainer, tableView);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(10));

		Scene scene = new Scene(vbox, 800, 500);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void listDiaries(GridPane gridPane, Stage primaryStage, User selectedUser) {
		int row = 1;
		User u = loadUserObjectFromFile(selectedUser);
		if (u.getDiaries().size() == 0) {
			System.out.println("No Diarries");
		} else {
			// User selectedUser = userList.
			System.out.println(u.getUsername());
			for (Diary diary : u.getDiaries()) {
				Label diaryLabel = new Label(
						"Exercise " + diary.getExercise().getExerciseId() + ":  Score: " + diary.getScore());
				Button diaryButton = new Button("Diary");
				diaryButton.setOnAction(event -> resultPage(primaryStage, diary, selectedUser));

				gridPane.add(diaryLabel, 0, row);
				gridPane.add(diaryButton, 1, row);

				row++;
			}
		}
	}

	public void listUsers(GridPane gridPane, Stage primaryStage) {
		int row = 2;

		for (User user : userList) {
			Label userLabel = new Label(user.getUsername());
			Button userDiaryButton = new Button("Show Diaries");
			userDiaryButton.setOnAction(event -> selectDiaryPage(primaryStage, user));
			// startExercise(this.currentUser,currentExercise,exercises.indexOf(currentExercise),primaryStage));

			gridPane.add(userLabel, 0, row);
			gridPane.add(userDiaryButton, 1, row);

			row++;
		}
	}

	public void selectDiaryPage(Stage primaryStage, User selectedUser) {
		primaryStage.setTitle(selectedUser.getUsername() + "'s Diaries");

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10));
		gridPane.setVgap(10);
		gridPane.setHgap(10);

		listDiaries(gridPane, primaryStage, selectedUser);

		
		Button backToSelectUserPage = new Button("Back ");
		if(currentUser.isAdmin()) {
			backToSelectUserPage.setOnAction(event -> backToSelectUser(primaryStage));

		}else {
			backToSelectUserPage.setOnAction(event -> backToWelcome(currentUser,primaryStage));

		}

		gridPane.add(backToSelectUserPage, 0, 0);

		ColumnConstraints col1 = new ColumnConstraints();
		ColumnConstraints col2 = new ColumnConstraints();
		ColumnConstraints col3 = new ColumnConstraints();
		col1.setPercentWidth(55);
		col2.setPercentWidth(60);
		col3.setPercentWidth(35);
		gridPane.getColumnConstraints().addAll(col1, col2, col3);
		// GridPane.setColumnSpan(, 2);

		// Create scene and set it on the stage
		Scene scene = new Scene(gridPane, 400, 300);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void selectUserPage(Stage primaryStage) {
		primaryStage.setTitle("Users");
		
		Label messageLabel = new Label();
		messageLabel.setText("Select the person whose diaries you want to view  ");

		Button backToWelcome = new Button("Back ");
		backToWelcome.setOnAction(event -> backToWelcome(this.currentUser, primaryStage));

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10));
		gridPane.setVgap(10);
		gridPane.setHgap(10);

		listUsers(gridPane, primaryStage);

		gridPane.add(backToWelcome, 0, 0);
		gridPane.add(messageLabel, 0, 1);
		

		ColumnConstraints col1 = new ColumnConstraints();
		ColumnConstraints col2 = new ColumnConstraints();
		ColumnConstraints col3 = new ColumnConstraints();
		col1.setPercentWidth(35);
		col2.setPercentWidth(60);
		col3.setPercentWidth(35);
		gridPane.getColumnConstraints().addAll(col1, col2, col3);
		 GridPane.setColumnSpan(messageLabel, 2);

		// Create scene and set it on the stage
		Scene scene = new Scene(gridPane, 400, 300);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private static void backToWelcome(User currentUser, Stage primaryStage) {
		WelcomePage welcomePage = new WelcomePage(currentUser);
		welcomePage.mainPage(primaryStage);
	}

	private void backToSelectUser(Stage primaryStage) {
		selectUserPage(primaryStage);
	}

	// save selected diary as CSV file to open it in excel
	private void saveAsCSV(Diary selectedDiary) {
		String fileName = selectedDiary.getUser().getUsername() + "_ex" + selectedDiary.getExercise().getExerciseId()
				+ "_s" + selectedDiary.getScore();
		String filePath = "src/application/" + fileName + " .csv";
		boolean success = CSVExporter.exportToCSV(selectedDiary, filePath);
		if (success) {
			showAlert(Alert.AlertType.INFORMATION, "CSV Export", "CSV file successfully generated.");
		}
	}
	
	 private void showAlert(Alert.AlertType alertType, String title, String message) {
	        Alert alert = new Alert(alertType);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }

}
