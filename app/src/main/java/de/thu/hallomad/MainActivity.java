package de.thu.hallomad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    /**
     * Start Page creates
     * Toolbar: with options - Quiz's Rules and About messages
     * Category CardView
     * Advanced CardView
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Settup the toolbar of the start page
         */
        Toolbar toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        CardView categoryCardView = findViewById(R.id.id_categoryCardView);

        /**
         * cardView listener gives an access point to another activity after the user is selecting one of two options
         */
        categoryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(MainActivity.this, CategoryGridActivity.class);
                startActivity(categoryIntent);
            }
        });
        CardView advancedCardView = findViewById(R.id.id_advancedCardView);
        advancedCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(MainActivity.this, AdvancedActivity.class);
                startActivity(categoryIntent);
            }
        });
    }

    /**
     * Creating a menu with 2 selections: Quiz's Rules and About message.
     *
     * @param menu
     * @return boolean value: true if the menu was created successfully
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_quiz, menu);
        return true;
    }

    /**
     * Selection of item from menu: Quiz's Rules or About
     * According of selection a Dialog message will be shown with OK button that close the dialog and return back to the Start Page
     *
     * @param item
     * @return boolean value: true if the item was successfully selected
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_rules:
                AlertDialog rulesDialog = new AlertDialog.Builder(MainActivity.this).create();
                rulesDialog.setTitle("Rules of the Quiz");
                rulesDialog.setMessage("1. Choose an Einstein Quiz by selecting 'CATEGORY' as a fast play or 'ADVANCED' as a custom setup of the Quiz"
                        + "\n2. 'CATEGORY'- gives a possibility to choose through different IT topics"
                        + "\n3. 'ADVANCED' - gives to You a possibility to set the favorite category, the difficulty level, the number of questions and the tags"
                        + "\n4. 'RESULT'- displays the total point accumulated and the decission of passing or failling the quiz"
                        + "\n5. 'Check results' - gives the user a possibility to review the questions with the correct answers"
                        + "\n\n QUIZ! TIME Rules "
                        + "\n1. For every correct answer you get a certain amount of points"
                        + "\n2. For every wrong answer a certain amount of points is deducted"
                        + "\n3. A toast with a hint message is displayed on the wrong selected answer if it is available otherwise a toast with false message is shown"
                        + "\n4. If the total amount of points reaches more than 50% the Quiz is PASSED otherwise is FAILED"
                        + "\n5. The amount of points for every question is calculated in the following way = 100/(number of questions selected by the user)"
                        + "\nBy default the number of questions are 20."
                        + "\nQuiz Api Source: https://quizapi.io/"
                        + "\n\nBOOST YOUR KNOWLEDGE with Einstein Quiz!");
                rulesDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                rulesDialog.show();
                return true;
            case R.id.id_about:
                AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
                aboutDialog.setTitle("About");
                aboutDialog.setMessage("Einstein Quiz is an application for CTS student to boost the students' knowledge in the free time by combining the useful with pleasant while playing Einstein Quiz"
                        + "\n\nContributor: ©Cicala Ion");
                aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                aboutDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}