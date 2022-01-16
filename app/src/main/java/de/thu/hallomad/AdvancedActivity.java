package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class AdvancedActivity extends AppCompatActivity {
    private String url;
    private String tagsSelection;
    private Spinner categorySpinner;
    private Spinner difficultySpinner;
    private Spinner numberQuestionSpinner;
    private Spinner tagsSpinner;

    /**
     * Advanced selection activity is created with spinner that gives the user possibility to choose
     * Category of the Quiz
     * Difficulty of the quiz: easy, medium or hard
     * Number of questions per quiz
     * Tags of the quiz
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
        String[] category = {"Linux", "Bash", "Uncategorized", "Docker", "SQL", "CMS", "Code", "DevOps"};
        Arrays.sort(category);
        String[] difficulty = {"Easy", "Medium", "Hard"};
        String[] numberQuestion = {"5", "10", "15", "20"};
        String[] tags = new CategoryDatabase().getCATEGORIES();

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, R.layout.advanced_spinner_entry, R.id.id_spinner_entry, category);
        categorySpinner = findViewById(R.id.id_spinner_category);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, R.layout.advanced_spinner_entry, R.id.id_spinner_entry, difficulty);
        difficultySpinner = findViewById(R.id.id_spinner_difficulty);
        difficultySpinner.setAdapter(difficultyAdapter);

        ArrayAdapter<String> numberQuestionAdapter = new ArrayAdapter<>(this, R.layout.advanced_spinner_entry, R.id.id_spinner_entry, numberQuestion);
        numberQuestionSpinner = findViewById(R.id.id_spinner_num_questions);
        numberQuestionSpinner.setAdapter(numberQuestionAdapter);

        ArrayAdapter<String> tagsAdapter = new ArrayAdapter<>(this, R.layout.advanced_spinner_entry, R.id.id_spinner_entry, tags);
        tagsSpinner = findViewById(R.id.id_spinner_tags);
        tagsSpinner.setAdapter(tagsAdapter);


    }

    /**
     * On Button Click the user option from spinner are read and passed to the url before beeing sed to the Quiz Api
     *
     * @param view
     */
    public void onClickQuizTime(View view) {
        try {
            String categorySelection = (String) categorySpinner.getSelectedItem();
            String difficultySelection = (String) difficultySpinner.getSelectedItem();
            String numberQuestionSelection = (String) numberQuestionSpinner.getSelectedItem();
            tagsSelection = (String) tagsSpinner.getSelectedItem();

            url = "https://quizapi.io/api/v1/questions?apiKey=RX3MHGRVCWjCiTlFTMTeIfG1tfaU1jVo1I5Lpf64" +
                    "&category=" + categorySelection +
                    "&difficulty=" + difficultySelection +
                    "&limit=" + numberQuestionSelection +
                    "&tags=" + tagsSelection;
            new GetQuestions(this).execute(url);
        } catch (Exception e) {
            Log.e("Error", e.getMessage() + e.getCause());
        }
    }


    private class GetQuestions extends AsyncTask<String, Void, ArrayList<QuestionAnswers>> {

        private final ProgressDialog progressDialog;

        /**
         * A new thread is created using AsynTask to get the Quiz from Api
         *
         * @param context
         */
        public GetQuestions(Context context) {
            this.progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading...");
            progressDialog.setTitle("Getting Quiz");

        }

        /**
         * Before accessing the Api a loading dialog is shown to the user
         */
        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        /**
         * A thread is created and a task of retrieving data from Api is given
         *
         * @param urlParams - string that holds url Api with options of users
         * @return Quiz with questions ready to be display in the Quiz Play activity
         */
        @Override
        protected ArrayList<QuestionAnswers> doInBackground(String... urlParams) {
            return ParseJson.dataParse(urlParams[0]);
        }

        /**
         * After the data was get in the new thread in the Ui thread is shown a toast if no question match the user selection
         * Or Play Quiz Activity is opened to start the play quiz
         *
         * @param data that represent the questions of quiz is send to play quiz activity
         */
        @Override
        protected void onPostExecute(ArrayList<QuestionAnswers> data) {

            if (!data.isEmpty()) {
                Intent playQuizIntent = new Intent(AdvancedActivity.this, QuizPlayActivity.class);
                playQuizIntent.putExtra("selectedCategory", tagsSelection);
                playQuizIntent.putExtra("QuizApiData", data);
                progressDialog.dismiss();
                startActivity(playQuizIntent);
            } else {
                Toast error = Toast.makeText(AdvancedActivity.this, "No questions found", Toast.LENGTH_LONG);
                error.show();
                progressDialog.dismiss();
            }


        }

    }
}