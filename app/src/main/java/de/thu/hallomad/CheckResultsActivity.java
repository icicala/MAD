package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class CheckResultsActivity extends AppCompatActivity {
    private static int questionNumber = 0;
    private TextView questionNumberView;
    private TextView questionView;
    private ArrayList<QuestionAnswers> data;
    private AnswerResultListAdapter answerResultListAdapter;
    private ListView answersListView;

    /**
     * The activity to display the played Quiz where the correct answers is shown
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_results);
        questionNumberView = findViewById(R.id.id_question_number);
        questionView = findViewById(R.id.id_question_text);
        answersListView = findViewById(R.id.id_answers_results);
        data = (ArrayList<QuestionAnswers>) getIntent().getSerializableExtra("QuizApiData");

        displayQuiz(questionNumber);

    }

    /**
     * Filled the text field of question and answers(customer adapter with a picture of correct and false icon and answer text)
     *
     * @param position
     */
    private void displayQuiz(int position) {
        questionNumberView.setText("Question " + (position + 1));
        questionView.setText(data.get(position).getQuestion());
        answerResultListAdapter = new AnswerResultListAdapter(data.get(position).getAnswers(), data.get(position).getCorrectAnswer());
        answersListView.setAdapter(answerResultListAdapter);
    }

    /**
     * Button to navigate to the next question in the quiz
     *
     * @param view
     */
    public void onClickNext(View view) {
        if (questionNumber >= 0 && questionNumber < data.size() - 1) {
            ++questionNumber;
            displayQuiz(questionNumber);
        } else {
            questionNumber = 0;
            displayQuiz(questionNumber);
        }
    }

    /**
     * Button to start the starting page after an quiz was reviewed
     *
     * @param view
     */
    public void onClickFinish(View view) {
        Intent startQuiz = new Intent(CheckResultsActivity.this, MainActivity.class);
        startActivity(startQuiz);
    }


}