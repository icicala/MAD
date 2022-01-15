package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView categoryCardView = findViewById(R.id.id_categoryCardView);
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
}