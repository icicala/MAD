package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CategoryGridActivity extends AppCompatActivity {
    private String selectedItem;
    private String url;
    private CategoryGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_grid);
        CategoryDatabase categoryDatabase = new CategoryDatabase();
        CategoryGridAdapter adapter = new CategoryGridAdapter(categoryDatabase);
        GridView gridView = findViewById(R.id.id_category_grid);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String) adapter.getItem(position);
                if (selectedItem.equals("HTML") || selectedItem.equals("JavaScript") || selectedItem.equals("PHP")) {
                    selectedItem = "Code";
                }
                if (selectedItem.equals("Kubernetes")) {
                    selectedItem = "CMS";
                }
                url = "https://quizapi.io/api/v1/questions?apiKey=RX3MHGRVCWjCiTlFTMTeIfG1tfaU1jVo1I5Lpf64&category=" + selectedItem + "&limit=20";
                Log.d("Clicked", selectedItem);
                new GetQuestions().execute(url);
            }
        });
    }

    private class GetQuestions extends AsyncTask<String, Void, ArrayList<QuestionAnswers>> {


        @Override
        protected ArrayList<QuestionAnswers> doInBackground(String... params) {
            ArrayList<QuestionAnswers> data = new ArrayList<>();
            FetchDataFromQuizAPI fetchDataFromQuizAPI = new FetchDataFromQuizAPI();
            String url = params[0];
            String jsonString = fetchDataFromQuizAPI.fetchDataFromQuizApi(url);
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
                    e.printStackTrace();
                }


            }
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<QuestionAnswers> data) {
            Intent playQuizIntent = new Intent(CategoryGridActivity.this, QuizPlayActivity.class);
            playQuizIntent.putExtra("selectedCategory", selectedItem);
            playQuizIntent.putExtra("QuizApiData", data);
            startActivity(playQuizIntent);


        }

    }
}