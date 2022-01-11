package de.thu.hallomad;

import java.io.Serializable;
import java.util.List;

public class QuestionAnswers implements Serializable {
    private String question;
    private List<AnswerEntry> answers;
    private char correctAnswer;
    private String explanation;

    public QuestionAnswers(String question, List<AnswerEntry> answers, char correctAnswer, String explanation) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

    public String getQuestion() {
        return question;
    }

    public List<AnswerEntry> getAnswers() {
        return answers;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }


}
