package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView score = findViewById(R.id.id_score);
        TextView grade = findViewById(R.id.id_grade);
        TextView topic = findViewById(R.id.id_topic);
        TextView finalResult = findViewById(R.id.id_final);

        String selectedCategory = (String) getIntent().getSerializableExtra("selectedCategory");
        int totalScore = (int) getIntent().getSerializableExtra("score");

        if (totalScore <= 49) {
            score.setTextColor(Color.RED);
            grade.setTextColor(Color.RED);
            topic.setTextColor(Color.RED);
            finalResult.setTextColor(Color.RED);
            finalResult.setText(R.string.FAIL);
        } else {
            score.setTextColor(Color.GREEN);
            grade.setTextColor(Color.GREEN);
            topic.setTextColor(Color.GREEN);
            finalResult.setTextColor(Color.GREEN);
            finalResult.setText(R.string.PASS);
        }
        score.setText(totalScore + " %");
        grade.setText(gradeCalculation(totalScore));
        topic.setText(selectedCategory);


    }

    private String gradeCalculation(int score) {
        String grade = "";
        if (score >= 80 && score <= 100) {
            grade = "A";
        } else if (score >= 65 && score <= 79) {
            grade = "B";
        } else if (score >= 55 && score <= 64) {
            grade = "C";
        } else if (score >= 50 && score <= 54) {
            grade = "D";
        } else {
            grade = "F";
        }
        return grade;
    }


}