package de.thu.hallomad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int secretNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            secretNumber = savedInstanceState.getInt("SecretNUmber");
            findViewById(R.id.button).setBackgroundColor(savedInstanceState.getInt("ButtonCollor"));
        } else {
            Random random = new Random();
            secretNumber = random.nextInt(10) + 1;
        }
    }

    public void onClickButton(View view) {
        EditText editText = findViewById(R.id.editText);
        int guess = Integer.parseInt(editText.getText().toString());
        if (guess == secretNumber) {
            view.setBackgroundColor(Color.GREEN);
        } else {
            view.setBackgroundColor(Color.RED);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("SecretNUmber", secretNumber);


        outState.putInt("ButtonCollor", Color.GREEN);
    }
}