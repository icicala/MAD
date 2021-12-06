package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class ColorDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_details);

        Intent intent = getIntent();
        String colorName = (String) intent.getSerializableExtra("colorName");
        int colorValue = (int) intent.getSerializableExtra("colorValue");

        EditText textView = findViewById(R.id.id_colorDetails);
        textView.setText(colorName);
        textView.setBackgroundColor(colorValue);

        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("colorNewName", textView.getText().toString());
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
                return false;
            }
        });
    }
}