package application;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class TestExercise {

    private Exercise exercise;

    @BeforeEach
    public void setUp() {
        exercise = new Exercise(1, 1, 10, 5);
    }

    @Test
    public void testGenerateQuestions() {
        ArrayList<Question> questions = exercise.getQuestions();

        Assertions.assertEquals(5, questions.size());

        for (Question question : questions) {
            int value1 = question.getFirstValue();
            int value2 = question.getFirstValue();

            Assertions.assertTrue(value1 >= 1 && value1 <= 10);
            Assertions.assertTrue(value2 >= 1 && value2 <= 10);

        }
    }

    @Test
    public void testAddHighScore() {
        Score score = new Score("Can",5, 100);
        exercise.addHighScore(score);

        ArrayList<Score> highScores = exercise.getHighScores();

        Assertions.assertTrue(highScores.contains(score));
    }

    @Test
    public void testSetExerciseId() {
        exercise.setExerciseId(2);
        int exerciseId = exercise.getExerciseId();

        Assertions.assertEquals(2, exerciseId);
    }

    @Test
    public void testSetMinValue() {
        exercise.setMinValue(5);
        int minValue = exercise.getMinValue();

        Assertions.assertEquals(5, minValue);
    }

    @Test
    public void testSetMaxValue() {
        exercise.setMaxValue(20);
        int maxValue = exercise.getMaxValue();

        Assertions.assertEquals(20, maxValue);
    }

    @Test
    public void testSetNumOfQuestions() {
        exercise.setNumOfQuestions(10);
        int numOfQuestions = exercise.getNumOfQuestions();

        Assertions.assertEquals(10, numOfQuestions);
    }
}

