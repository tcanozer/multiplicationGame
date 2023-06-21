package application;

import java.io.Serializable;
import java.util.ArrayList;

public class Exercise implements Serializable {

	private static final long serialVersionUID = 5247474819762658047L;
	private int exerciseId;
	private int minValue;
	private int maxValue;
	private int numOfQuestions;
	private ArrayList<Question> questions;
	private ArrayList<Score> highScores;

	public Exercise(int exerciseId, int minValue, int maxValue, int numOfQuestions) {
		this.exerciseId = exerciseId;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.numOfQuestions = numOfQuestions;
		this.questions = new ArrayList<Question>();
		this.questions = generateQuestions(this.numOfQuestions);
		this.highScores = new ArrayList<Score>();
	}

	private ArrayList<Question> generateQuestions(int numOfQuestions) {
		ArrayList<Question> generatedQuestions = new ArrayList<>();

		for (int i = 0; i < numOfQuestions; i++) {
			Question x = new Question(this.minValue, this.maxValue);
			generatedQuestions.add(x);
		}
		return generatedQuestions;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void addHighScore(Score score) {
		this.highScores.add(score);
	}

	public ArrayList<Score> getHighScores() {
		return highScores;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getNumOfQuestions() {
		return numOfQuestions;
	}

	public void setNumOfQuestions(int numOfQuestions) {
		this.numOfQuestions = numOfQuestions;
	}

}
