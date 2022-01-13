package de.thu.hallomad;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizPlayActivity extends AppCompatActivity {
    private static int positionQuiz;
    private ArrayList<QuestionAnswers> data;
    private ListView listViewAnswers;
    private TextView questionView;
    private AnswerListAdapter answerListAdapter;
    private SeekBar progressBar;
    private int totalPoints;
    private int marginPoints;
    private String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_play);
        positionQuiz = 0;
        totalPoints = 0;

        selectedCategory = (String) getIntent().getSerializableExtra("selectedCategory");
        data = (ArrayList<QuestionAnswers>) getIntent().getSerializableExtra("QuizApiData");
        marginPoints = 100 / (data.size() - 1);
        TextView textViewCategoryQuiz = findViewById(R.id.id_categoryQuiz);
        textViewCategoryQuiz.setText(selectedCategory + " Quiz");
        Log.d("ErrorLaInceput", data.size() + " ");
        progressBar = findViewById(R.id.id_QuestionPositionBar);
        progressBar.setMax(data.size() - 1);
        displayQuiz(positionQuiz);
        Log.d("ErrorMaring", String.valueOf(marginPoints));


        listViewAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                char selectedAnswer = ((AnswerEntry) answerListAdapter.getItem(position)).getOrder();
                if (selectedAnswer != data.get(positionQuiz).getCorrectAnswer()) {
                    if (!data.get(positionQuiz).getExplanation().equals("")) {
                        Toast onfalseAnswer = Toast.makeText(parent.getContext(), data.get(positionQuiz).getExplanation(), Toast.LENGTH_SHORT);
                        onfalseAnswer.show();
                    }
                    listViewAnswers.getChildAt(position).setBackgroundColor(Color.RED);
                    if (listViewAnswers.getChildAt(position).isEnabled()) {
                        if (totalPoints >= marginPoints) {
                            totalPoints = totalPoints - marginPoints;
                        }
                        Log.d("ErrorMinus", String.valueOf(totalPoints));
                    }
                    listViewAnswers.getChildAt(position).setEnabled(false);

                } else {

                    if (positionQuiz < data.size() - 1) {
                        totalPoints = totalPoints + marginPoints;
                        positionQuiz++;
                        Log.d("ErrorPLus", String.valueOf(totalPoints));
                        displayQuiz(positionQuiz);

                    } else {
                        Intent result = new Intent(QuizPlayActivity.this, ResultsActivity.class);
                        result.putExtra("selectedCategory", selectedCategory);
                        result.putExtra("QuizApiData", data);
                        result.putExtra("score", totalPoints);
                        startActivity(result);

                    }

                }
            }
        });

        // keep the progressbar not to be able to be change by user
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    progressBar.setProgress(positionQuiz);
                } else {
                    progressBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void displayQuiz(int index) {
        progressBar.setProgress(positionQuiz);
        questionView = findViewById(R.id.id_question);
        questionView.setText(data.get(index).getQuestion());
        answerListAdapter = new AnswerListAdapter(data.get(index).getAnswers());
        listViewAnswers = findViewById(R.id.id_listView_answer);
        listViewAnswers.setAdapter(answerListAdapter);

    }


}