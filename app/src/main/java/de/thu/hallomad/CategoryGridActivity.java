package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
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

    /**
     * Category Activity making use of customer adapter display the image and title of category in the grid layout
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_grid);
        CategoryDatabase categoryDatabase = new CategoryDatabase();
        adapter = new CategoryGridAdapter(categoryDatabase);
        GridView gridView = findViewById(R.id.id_category_grid);
        gridView.setAdapter(adapter);

        /**
         * According the user selection the title of category is read from the cardView and is concatenated to the
         * url query that is send to the Quiz Api. The default number of question in the Category quiz is set to 20 questions;
         */
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String) adapter.getItem(position);
                url = "https://quizapi.io/api/v1/questions?apiKey=RX3MHGRVCWjCiTlFTMTeIfG1tfaU1jVo1I5Lpf64&tags=" + selectedItem + "&limit=20";
                new GetQuestions(view.getContext()).execute(url);
            }
        });
    }

    private class GetQuestions extends AsyncTask<String, Void, ArrayList<QuestionAnswers>> {

        private final ProgressDialog progressDialog;

        /**
         * AsyncTask is used to get data from Quiz Api in another thread
         * While the data is downloading Quiz Api a loading dialog is popping up until the task is completed
         *
         * @param context
         */
        public GetQuestions(Context context) {
            this.progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading...");
            progressDialog.setTitle("Getting Quiz");

        }

        /**
         * Before requiring data from API an loading dialog is popping up
         * Informing the user that the Quiz is in progress of loading
         */
        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        /**
         * Anather thread is created to load data from Quiz Api
         *
         * @param urlParams - the url is passed from Category activity
         * @return JSON Array that holds the questions
         */
        @Override
        protected ArrayList<QuestionAnswers> doInBackground(String... urlParams) {

            return ParseJson.dataParse(urlParams[0]);
        }

        /**
         * After the data was successfully loaded the Play Quiz activity is opening
         * The Quiz's questions a passed to Quiz Play activity and the category selected by the user
         *
         * @param data - the parsed data from JsonArray is givven as parameter for being delivered to the Quiz Play activity
         */
        @Override
        protected void onPostExecute(ArrayList<QuestionAnswers> data) {
            Intent playQuizIntent = new Intent(CategoryGridActivity.this, QuizPlayActivity.class);
            playQuizIntent.putExtra("selectedCategory", selectedItem);
            playQuizIntent.putExtra("QuizApiData", data);
            progressDialog.dismiss();
            startActivity(playQuizIntent);


        }

    }
}