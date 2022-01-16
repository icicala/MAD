package de.thu.hallomad;

import java.io.Serializable;

public class AnswerEntry implements Serializable {
    private char order;
    private String answer;

    /**
     * POJO for holding the anwer and the order in the quiz
     *
     * @param order
     * @param answer
     */
    public AnswerEntry(char order, String answer) {
        this.order = order;
        this.answer = answer;
    }

    /**
     * Getter the order of answer
     *
     * @return a value of type char that is a, b, c, d, etc
     */
    public char getOrder() {
        return order;
    }

    /**
     * Getter the answer
     *
     * @return a value of type string that represent an answer either correct or wrong for specific question
     */
    public String getAnswer() {
        return answer;
    }


}
