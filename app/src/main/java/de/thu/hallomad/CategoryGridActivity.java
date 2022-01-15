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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_grid);
        CategoryDatabase categoryDatabase = new CategoryDatabase();
        adapter = new CategoryGridAdapter(categoryDatabase);
        GridView gridView = findViewById(R.id.id_category_grid);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String) adapter.getItem(position);
                url = "https://quizapi.io/api/v1/questions?apiKey=RX3MHGRVCWjCiTlFTMTeIfG1tfaU1jVo1I5Lpf64&tags=" + selectedItem + "&limit=20";
                Log.d("Clicked", selectedItem);
                new GetQuestions(view.getContext()).execute(url);
            }
        });
    }

    private class GetQuestions extends AsyncTask<String, Void, ArrayList<QuestionAnswers>> {

        private final ProgressDialog progressDialog;

        public GetQuestions(Context context) {
            this.progressDialog = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading...");
            progressDialog.setTitle("Getting Quiz");

        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected ArrayList<QuestionAnswers> doInBackground(String... urlParams) {

            return ParseJson.dataParse(urlParams[0]);
        }

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