package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView smiley = findViewById(R.id.id_smiley_view);
        View container = findViewById(R.id.id_activity_main);

        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
                    float x = event.getX();
                    float y = event.getY();

                    smiley.setX(x - smiley.getWidth() / 2);
                    smiley.setY(y - smiley.getHeight() / 2);
                }
                return true;
            }
        });

    }
}