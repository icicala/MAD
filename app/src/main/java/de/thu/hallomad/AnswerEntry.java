package de.thu.hallomad;

import java.io.Serializable;

public class AnswerEntry implements Serializable {
    private char order;
    private String answer;

    public AnswerEntry(char order, String answer) {
        this.order = order;
        this.answer = answer;
    }

    public char getOrder() {
        return order;
    }


    public String getAnswer() {
        return answer;
    }


}
