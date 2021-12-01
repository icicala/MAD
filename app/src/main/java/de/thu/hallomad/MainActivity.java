package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private final QuestionRepository questionBank = new QuestionRepository();
    private Question question;
    private Button ans1;
    private Button ans2;
    private Button ans3;
    private Button ans4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        question = questionBank.randomQuestion();
        updateQuestions(question);

    }

    private void updateQuestions(Question question) {
        TextView questionView = findViewById(R.id.id_question);
        questionView.setText(question.getQuestion());

        ans1 = findViewById(R.id.id_answer1);
        ans1.setText(question.getAnswer()[0]);

        ans2 = findViewById(R.id.id_answer2);
        ans2.setText(question.getAnswer()[1]);

        ans3 = findViewById(R.id.id_answer3);
        ans3.setText(question.getAnswer()[2]);

        ans4 = findViewById(R.id.id_answer4);
        ans4.setText(question.getAnswer()[3]);
        setEnablesButtons(ans1, ans2, ans3, ans4);
    }


    public void onClicked(View view) {
        switch (Integer.parseInt(view.getTag().toString())) {
            case 0: {
                if (question.getCorrectAnswer() == 0) {
                    question = questionBank.randomQuestion();
                    updateQuestions(question);
                } else {
                    ans1.setEnabled(false);
                }
                break;
            }
            case 1: {
                if (question.getCorrectAnswer() == 1) {
                    question = questionBank.randomQuestion();
                    updateQuestions(question);
                } else {
                    ans2.setEnabled(false);
                }

                break;
            }
            case 2: {
                if (question.getCorrectAnswer() == 2) {
                    question = questionBank.randomQuestion();
                    updateQuestions(question);
                } else {
                    ans3.setEnabled(false);
                }

                break;
            }
            case 3: {
                if (question.getCorrectAnswer() == 3) {
                    question = questionBank.randomQuestion();
                    updateQuestions(question);
                } else {
                    ans4.setEnabled(false);
                }

                break;
            }

        }


    }

    private void setEnablesButtons(Button ans1, Button ans2, Button ans3, Button ans4) {
        ans1.setEnabled(true);
        ans2.setEnabled(true);
        ans3.setEnabled(true);
        ans4.setEnabled(true);
    }
}