package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVExporter {

	public static boolean exportToCSV(Diary selectedDiary, String filePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			writer.write(
					"Question Number;Question;User Answer;Correct Result;Is Correct;Spent Time;Start Time;End Time");
			writer.newLine();

			// Write data rows
			for (AnswerData answerData : selectedDiary.getAnswerDatas()) {
				writer.write(escapeSpecialCharacters(String.valueOf(answerData.getQuestionNumber())));
				writer.write(";");
				writer.write(escapeSpecialCharacters(answerData.getQuestion()));
				writer.write(";");
				writer.write(escapeSpecialCharacters(String.valueOf(answerData.getUserAnswer())));
				writer.write(";");
				writer.write(escapeSpecialCharacters(String.valueOf(answerData.getCorrectResult())));
				writer.write(";");
				writer.write(escapeSpecialCharacters(String.valueOf(answerData.isIsCorrect())));
				writer.write(";");
				writer.write(escapeSpecialCharacters(String.valueOf(answerData.getSpentTime())));
				writer.write(";");
				writer.write(escapeSpecialCharacters(answerData.getQuestionStartTimeFormatted()));
				writer.write(";");
				writer.write(escapeSpecialCharacters(answerData.getQuestionEndTimeFormatted()));
				writer.write(";");

				writer.newLine();
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static String escapeSpecialCharacters(String value) {
		if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
			value = "\"" + value.replace("\"", "\"\"") + "\"";
		}
		return value;
	}
}
