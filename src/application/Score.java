package application;

import java.io.Serializable;

public class Score implements Serializable {
	
	private static final long serialVersionUID = -2898288448870990501L;
	private String username;
	private int score;
	private int totalTimeSpent;
	private int numOfCorrectAnswers;

	public Score(String username, int numOfCorrectAnswers , int totalTimeSpent) {
		this.username = username;
		this.numOfCorrectAnswers = numOfCorrectAnswers;
		this.totalTimeSpent = totalTimeSpent;
		this.score = (numOfCorrectAnswers*1000)/(totalTimeSpent);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getTotalTimeSpent() {
		return totalTimeSpent;
	}

	public void setTotalTimeSpent(int totalTimeSpent) {
		this.totalTimeSpent = totalTimeSpent;
	}

	public int getNumOfCorrectAnswers() {
		return numOfCorrectAnswers;
	}

	public void setNumOfCorrectAnswers(int numOfCorrectAnswers) {
		this.numOfCorrectAnswers = numOfCorrectAnswers;
	}
	
	
		

}
