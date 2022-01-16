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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizPlayActivity extends AppCompatActivity {
    private static int positionQuiz;
    private ArrayList<QuestionAnswers> data;
    private List<AnswerEntry> answerEntryList;
    private ListView listViewAnswers;
    private TextView questionView;
    private AnswerListAdapter answerListAdapter;
    private SeekBar progressBar;
    private int totalPoints;
    private int marginPoints;
    private String selectedCategory;
    private Toast hint;
    private Toast onfalseAnswer;

    /**
     * Quiz Play activity that display the question and the answers
     * Calculate the points for each question from 100/(total number of questions)
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_play);
        positionQuiz = 0;
        totalPoints = 0;
        listViewAnswers = findViewById(R.id.id_listView_answer);
        questionView = findViewById(R.id.id_question);
        selectedCategory = (String) getIntent().getSerializableExtra("selectedCategory");
        data = (ArrayList<QuestionAnswers>) getIntent().getSerializableExtra("QuizApiData");
        marginPoints = 100 / (data.size());
        Log.d("Points", marginPoints + "");
        TextView textViewCategoryQuiz = findViewById(R.id.id_categoryQuiz);
        textViewCategoryQuiz.setText(selectedCategory + " Quiz");

        progressBar = findViewById(R.id.id_QuestionPositionBar);
        progressBar.setMax(data.size() - 1);

        displayQuiz(positionQuiz);


        /**
         * On selection of correct answer the activity is displaying the next question and the margin points are added to the totalpoints
         * otherwise the answer is removed and a certain amount of points is deducted from total amount of points
         */
        listViewAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    char selectedAnswer = ((AnswerEntry) answerListAdapter.getItem(position)).getOrder();
                    if (selectedAnswer != data.get(positionQuiz).getCorrectAnswer()) {
                        if (!data.get(positionQuiz).getExplanation().equals("")) {
                            hint = Toast.makeText(parent.getContext(), data.get(positionQuiz).getExplanation(), Toast.LENGTH_LONG);
                            hint.show();
                        } else {
                            onfalseAnswer = Toast.makeText(parent.getContext(), "FALSE", Toast.LENGTH_SHORT);
                            onfalseAnswer.show();
                        }
                        answerEntryList.remove(position);
                        answerListAdapter.notifyDataSetChanged();

                        if (totalPoints >= marginPoints) {
                            totalPoints = totalPoints - marginPoints;
                        }
                        Log.d("ErrorMinus", String.valueOf(totalPoints));


                    } else {
                        totalPoints = totalPoints + marginPoints;
                        if (positionQuiz < data.size() - 1) {

                            positionQuiz++;
                            Log.d("Reseting", view.isEnabled() + "");
//
                            displayQuiz(positionQuiz);
                        } else {
                            if (onfalseAnswer != null) {
                                onfalseAnswer.cancel();
                            }
                            if (hint != null) {
                                hint.cancel();
                            }
                            Intent result = new Intent(QuizPlayActivity.this, ResultsActivity.class);
                            result.putExtra("selectedCategory", selectedCategory);
                            result.putExtra("QuizApiData", data);
                            result.putExtra("score", totalPoints);
//
                            startActivity(result);
                        }

                    }
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }
        });


        /**
         * Keep the progress of the quiz not responding on clicking from user
         */
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

    /**
     * The text field of question and the listview of answers are filled with data from list of questions
     *
     * @param index - represent the position of question in the list of questions
     */
    private void displayQuiz(int index) {
        if (answerListAdapter == null) {
            answerEntryList = new ArrayList<>();
            answerEntryList.addAll(data.get(index).getAnswers());
            answerListAdapter = new AnswerListAdapter(answerEntryList);
            listViewAnswers.setAdapter(answerListAdapter);
        }

        progressBar.setProgress(positionQuiz);
        questionView.setText(data.get(index).getQuestion());
        answerEntryList.clear();
        answerEntryList.addAll(data.get(index).getAnswers());
        answerListAdapter.notifyDataSetChanged();

    }


}