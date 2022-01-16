package de.thu.hallomad;

import java.io.Serializable;
import java.util.List;

public class QuestionAnswers implements Serializable {
    private String question;
    private List<AnswerEntry> answers;
    private char correctAnswer;
    private String explanation;

    /**
     * POJO of Question with list of Answers
     *
     * @param question
     * @param answers
     * @param correctAnswer
     * @param explanation
     */
    public QuestionAnswers(String question, List<AnswerEntry> answers, char correctAnswer, String explanation) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

    /**
     * Getter of the Question
     *
     * @return question of type string
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Getter of the answers
     *
     * @return answers list
     */
    public List<AnswerEntry> getAnswers() {
        return answers;
    }

    /**
     * Getter of correct answer
     *
     * @return correct answer order of type char
     */
    public char getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Getter for hint of the question
     *
     * @return explanation of question of type string
     */
    public String getExplanation() {
        return explanation;
    }


}
