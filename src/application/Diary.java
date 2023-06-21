package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Diary implements Serializable {

	private static final long serialVersionUID = -4614776462572186855L;
	private User user;
	private Exercise exercise;
	private long startTime;
	private long endTime;
	private List<AnswerData> answerDatas;
	private int score;

	public Diary(User user, Exercise exercise) {
		this.user = user;
		this.exercise = exercise;
		this.startTime = System.currentTimeMillis();
		this.answerDatas = new ArrayList<>();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void addAnswerData(AnswerData answerData) {
		answerDatas.add(answerData);
	}

	public void setEndTime() {
		this.endTime = System.currentTimeMillis();
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public User getUser() {
		return user;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public List<AnswerData> getAnswerDatas() {
		return answerDatas;
	}

}
