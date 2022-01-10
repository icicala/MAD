package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.Arrays;

public class QuizPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_play);

        AnswerEntry[] answers = {new AnswerEntry('A', "Java char to String Example:"), new AnswerEntry('B', "Question 2"), new AnswerEntry('C', "Question 3")};
        AnswerListAdapter adapter = new AnswerListAdapter(Arrays.asList(answers));
        ListView listView = findViewById(R.id.id_listView_answer);
        listView.setAdapter(adapter);

        new GetQuestions().execute();
    }

    private class GetQuestions extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... args) {
            FetchDataFromQuizAPI fetchDataFromQuizAPI = new FetchDataFromQuizAPI();
            String url = "https://quizapi.io/api/v1/questions?apiKey=RX3MHGRVCWjCiTlFTMTeIfG1tfaU1jVo1I5Lpf64&category=linux";
            String jsonString = fetchDataFromQuizAPI.fetchDataFromQuizApi(url);

            Log.d("Json", "Response from url:" + jsonString);
            return null;
        }
    }
}