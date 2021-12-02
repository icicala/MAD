package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_view_item, R.id.list_item_text, months);

        ListView listView = findViewById(R.id.list_view_id);
        listView.setAdapter(adapter);
    }
}