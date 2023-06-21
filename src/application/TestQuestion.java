package application;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

public class TestQuestion {

	@Test
	public void testQuestionCreation() {
		int minValue = 1;
		int maxValue = 10;
		Question question = new Question(minValue, maxValue);

		int firstValue = question.getFirstValue();
		int secondValue = question.getSecondValue();
		int result = question.getResult();

		Assertions.assertTrue(firstValue >= minValue && firstValue <= maxValue);
		Assertions.assertTrue(secondValue >= minValue && secondValue <= maxValue);
		Assertions.assertEquals(firstValue * secondValue, result);
	}
}
