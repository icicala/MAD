package de.thu.hallomad;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseJson {

    /**
     * Parse the result from Quiz API into Quiz's question, answers, correct answer and the hint
     *
     * @param urlParam - url to be send to Quiz Api
     * @return the list of questions ready to be display to activity
     */
    public static ArrayList<QuestionAnswers> dataParse(String urlParam) {
        ArrayList<QuestionAnswers> data = new ArrayList<>();
        FetchDataFromQuizAPI fetchDataFromQuizAPI = new FetchDataFromQuizAPI();
        String jsonString = fetchDataFromQuizAPI.fetchDataFromQuizApi(urlParam);
        if (jsonString != null) {
            try {
                JSONArray questions = new JSONArray(jsonString);
                for (int i = 0; i < questions.length(); i++) {
                    JSONObject question = questions.getJSONObject(i);
                    String questionData = question.getString("question");
                    JSONObject answers = question.getJSONObject("answers");
                    List<AnswerEntry> answerEntries = new ArrayList<>();
                    for (int j = 0; j < answers.length(); j++) {
                        String answer = answers.getString("answer_" + (char) ('a' + j));
                        if (!answer.equals("null")) {
                            AnswerEntry answerEntry = new AnswerEntry((char) ('a' + j), answer);
                            answerEntries.add(answerEntry);
                        }
                    }
                    char correctAnswer = ' ';
                    JSONObject correctAnswers = question.getJSONObject("correct_answers");
                    for (int l = 0; l < correctAnswers.length(); l++) {
                        String correctAnswerCheck = correctAnswers.getString("answer_" + (char) ('a' + l) + "_correct");
                        if (correctAnswerCheck.equals("true")) {
                            correctAnswer = (char) ('a' + l);
                        }

                    }
                    String explanationCheck = question.getString("explanation");
                    String explanation = "";
                    if (!explanationCheck.equals("null")) {
                        explanation = explanationCheck;
                    }
                    QuestionAnswers questionAnswers = new QuestionAnswers(questionData, answerEntries, correctAnswer, explanation);
                    data.add(questionAnswers);
                }


            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
            }


        }
        return data;
    }
}
