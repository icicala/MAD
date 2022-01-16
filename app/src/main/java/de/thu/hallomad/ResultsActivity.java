package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    /**
     * Result activity display the total amount of pints accumulated and the final decision of Quiz: PASSED or FAILED
     *
     * @param savedInstanceState
     */
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

    /**
     * Calculating the Grade by giving the total amount of points of the Quiz
     *
     * @param score
     * @return
     */
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

    /**
     * The check result activity is opening to be able to check the question with correct answers
     *
     * @param view
     */
    public void checkResults(View view) {
        ArrayList<QuestionAnswers> data = (ArrayList<QuestionAnswers>) getIntent().getSerializableExtra("QuizApiData");
        Intent checkResult = new Intent(ResultsActivity.this, CheckResultsActivity.class);
        checkResult.putExtra("QuizApiData", data);
        startActivity(checkResult);
    }

    /**
     * The starting page is opening after the user finished playing a quiz
     *
     * @param view
     */
    public void onClickFinish(View view) {
        Intent startQuiz = new Intent(ResultsActivity.this, MainActivity.class);
        startActivity(startQuiz);
    }
}