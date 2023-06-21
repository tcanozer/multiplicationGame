package application;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AnswerData implements Serializable {

	private static final long serialVersionUID = 7988030954969207983L;
	private int questionNumber;
	private long spentTime;
	private LocalTime questionStartTime;
	private LocalTime questionEndTime;
	private int userAnswer;
	private int correctResult;
	private boolean isCorrect;
	private String question;

	public AnswerData(int questionNumber) {
		this.questionNumber = questionNumber;
		this.questionStartTime = null;
		this.questionEndTime = null;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public int getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(int userAnswer) {
		this.userAnswer = userAnswer;
	}

	public int getCorrectResult() {
		return correctResult;
	}

	public void setCorrectResult(int correctResult) {
		this.correctResult = correctResult;
	}

	public boolean isIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public long getSpentTime() {
		return spentTime;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setQuestionStartTime() {
		this.questionStartTime = LocalTime.now();
	}

	public void setQuestionEndTime() {
		this.questionEndTime = LocalTime.now();
		this.spentTime = calculateSpentTimeInSeconds();
	}

	public String getQuestionStartTimeFormatted() {
		if (questionStartTime != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			return questionStartTime.format(formatter);
		}
		return "";
	}

	public String getQuestionEndTimeFormatted() {
		if (questionEndTime != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			return questionEndTime.format(formatter);
		}
		return "";
	}

	public long calculateSpentTimeInSeconds() {
		long elapsedSeconds = 0;
		if (questionStartTime != null && questionEndTime != null) {
			elapsedSeconds = questionStartTime.until(questionEndTime, java.time.temporal.ChronoUnit.SECONDS);
		}
		return elapsedSeconds;
	}

}
