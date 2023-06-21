package application;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class TestScore {

    private Score score;

    @BeforeEach
    public void setUp() {
        score = new Score("Can", 5, 10);
    }

    @Test
    public void testConstructorAndGetters() {
        Assertions.assertEquals("Can", score.getUsername());
        Assertions.assertEquals(5, score.getNumOfCorrectAnswers());
        Assertions.assertEquals(10, score.getTotalTimeSpent());
        Assertions.assertEquals(500, score.getScore());
    }

    @Test
    public void testSetUsername() {
        score.setUsername("Tahir");

        Assertions.assertEquals("Tahir", score.getUsername());
    }

    @Test
    public void testSetScore() {
        score.setScore(1000);

        Assertions.assertEquals(1000, score.getScore());
    }

    @Test
    public void testSetTotalTimeSpent() {
        score.setTotalTimeSpent(20);

        Assertions.assertEquals(20, score.getTotalTimeSpent());
    }

    @Test
    public void testSetNumOfCorrectAnswers() {
        score.setNumOfCorrectAnswers(8);

        Assertions.assertEquals(8, score.getNumOfCorrectAnswers());
    }
}

