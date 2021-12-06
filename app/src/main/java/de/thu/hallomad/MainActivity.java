package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ColorEntry[] colors = {
                new ColorEntry("Black", Color.BLACK), new ColorEntry("Red", Color.RED),
                new ColorEntry("Blue", Color.BLUE), new ColorEntry("Yellow", Color.YELLOW),
                new ColorEntry("Gray", Color.GRAY), new ColorEntry("Cyan", Color.CYAN),
                new ColorEntry("Green", Color.GREEN), new ColorEntry("White", Color.WHITE),
                new ColorEntry("Black", Color.BLACK), new ColorEntry("Red", Color.RED),
                new ColorEntry("Blue", Color.BLUE), new ColorEntry("Yellow", Color.YELLOW),
                new ColorEntry("Gray", Color.GRAY), new ColorEntry("Cyan", Color.CYAN),
                new ColorEntry("Green", Color.GREEN), new ColorEntry("White", Color.WHITE),
                new ColorEntry("Black", Color.BLACK), new ColorEntry("Red", Color.RED),
                new ColorEntry("Blue", Color.BLUE), new ColorEntry("Yellow", Color.YELLOW),
                new ColorEntry("Gray", Color.GRAY), new ColorEntry("Cyan", Color.CYAN),
                new ColorEntry("Green", Color.GREEN), new ColorEntry("White", Color.WHITE)

        };

        ListView listView = findViewById(R.id.list_view_id);

        ColorListAdapter adapter = new ColorListAdapter(colors);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ColorDetailsActivity.class);

                ColorEntry entry = (ColorEntry) adapter.getItem(position);
                intent.putExtra("colorName", entry.name);
                intent.putExtra("colorValue", entry.color);
                startActivity(intent);
            }
        });
    }
}