package application;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;

public class Login {

	private List<User> users;
	private String filePath;
	private User currentUser;

	public Login(String filePath) {
		users = new ArrayList<>();
		loadCredentialsFromFile(filePath);
		saveUsersToFile();
	}

	public List<User> getUsers() {
		return users;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	private void saveUserToFile(User selectedUser) {

		String fileName = selectedUser.getUsername() + ".dat";
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
			outputStream.writeObject(selectedUser);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveUsersToFile() {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
			outputStream.writeObject(users);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadCredentialsFromFile(String filePath) {
		try {
			File file = new File(filePath);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] credentials = line.split(",");
				String username = credentials[0];
				String password = credentials[1];
				boolean isAdmin = Boolean.parseBoolean(credentials[2]);
				User user = new User(username, password, isAdmin);
				users.add(user);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private User loadUserObjectFromFile(User selectedUser) {
		try (ObjectInputStream inputStream = new ObjectInputStream(
				new FileInputStream(selectedUser.getUsername() + ".dat"))) {
			selectedUser = (User) inputStream.readObject();
		} catch (EOFException e) {
			System.out.println("The user file is empty or corrupted.");
		}catch (FileNotFoundException e) {
			saveUserToFile(selectedUser);
			System.out.println("User's previous data archive file not found.");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return selectedUser;
	}

	/*
	 * If one of the credentials in the prepared txt  match with the entered credentials
	 * it returns that users saved object if exist.If user object file does not exist it creates a new one by saveUserToFile method
	 * If the none of the credential are not matching with the entered one it returns null
	 */
	public User authenticate(String username, String password) {
		for (User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return loadUserObjectFromFile(user);
			}
		}
		return null; // User not found or invalid credentials
	}

	/*
	 * For getting login credentials
	 */
	public void loginPage(Stage primaryStage) {
		primaryStage.setTitle("Login Page");

		Label usernameLabel = new Label("Username:");
		TextField usernameField = new TextField();

		Label passwordLabel = new Label("Password:");
		PasswordField passwordField = new PasswordField();

		Label messageLabel = new Label();

		Button loginButton = new Button("Login");
		loginButton.setOnAction(event -> {
			// Perform login authentication here
			String username = usernameField.getText();
			String password = passwordField.getText();
			User user = authenticate(username, password);

			if (user != null) {
				// Navigate to the exercise selection page
				this.currentUser = user;
				openWelcomePage(primaryStage, this.users);

			} else {
				messageLabel.setText("Invalid credentials. Please try again.");
				System.out.println("Invalid credentials. Please try again.");
			}
		});

		// Create layout and add components
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10));
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.add(usernameLabel, 0, 0);
		gridPane.add(usernameField, 1, 0);
		gridPane.add(passwordLabel, 0, 1);
		gridPane.add(passwordField, 1, 1);
		gridPane.add(loginButton, 2, 2);
		gridPane.add(messageLabel, 0, 3);

		ColumnConstraints col1 = new ColumnConstraints();
		ColumnConstraints col2 = new ColumnConstraints();
		ColumnConstraints col3 = new ColumnConstraints();
		col1.setPercentWidth(27);
		col2.setPercentWidth(60);
		col3.setPercentWidth(30);
		gridPane.getColumnConstraints().addAll(col1, col2, col3);
		GridPane.setColumnSpan(messageLabel, 2);

		Scene scene = new Scene(gridPane, 300, 150);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	void openWelcomePage(Stage primaryStage, List<User> userList) {
		WelcomePage welcomePage = new WelcomePage(this.currentUser);
		welcomePage.mainPage(primaryStage);
	}

}
