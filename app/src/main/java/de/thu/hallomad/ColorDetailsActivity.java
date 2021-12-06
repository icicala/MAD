package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ColorDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_details);

        Intent intent = getIntent();
        String colorName = (String) intent.getSerializableExtra("colorName");
        int colorValue = (int) intent.getSerializableExtra("colorValue");

        TextView textView = findViewById(R.id.id_colorDetails);
        textView.setText(colorName);
        textView.setBackgroundColor(colorValue);
    }
}