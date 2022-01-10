package de.thu.hallomad;

import java.util.List;

public class QuestionAnswers {
    private String question;
    private List<AnswerEntry> answers;
    private char correctAnswer;

    public QuestionAnswers(String question, List<AnswerEntry> answers, char correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnswerEntry> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerEntry> answers) {
        this.answers = answers;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(char correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
