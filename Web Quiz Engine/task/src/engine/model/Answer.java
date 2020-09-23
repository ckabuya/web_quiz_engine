package engine.model;

import java.util.Arrays;

public class Answer {
    private int [] answer;

    public Answer() {
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] options) {
        this.answer = options;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answer=" + Arrays.toString(answer) +
                '}';
    }
}
