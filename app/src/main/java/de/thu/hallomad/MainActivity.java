package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String pin = editText.getText().toString();
        editor.putString("Pin", pin);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        String pin = preferences.getString("Pin", "");
        editText.setText(pin);
    }
}