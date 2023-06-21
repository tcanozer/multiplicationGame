package application;

import java.io.Serializable;
import java.util.Random;

public class Question implements Serializable {
    private static final long serialVersionUID = 4237899205586389210L;
	private int firstValue;
    private int secondValue;
    private int result;

    public Question(int minValue, int maxValue) {
        generateQuestion(minValue, maxValue);
    }

    private void generateQuestion(int minValue, int maxValue) {
        Random random = new Random();
        this.firstValue = random.nextInt(maxValue - minValue + 1) + minValue;
        this.secondValue = random.nextInt(maxValue - minValue + 1) + minValue;
        this.result = firstValue * secondValue;
    }

    public int getFirstValue() {
        return firstValue;
    }

    public int getSecondValue() {
        return secondValue;
    }

    public int getResult() {
        return result;
    }

	

    
    
}

