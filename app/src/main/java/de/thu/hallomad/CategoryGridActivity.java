package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

public class CategoryGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_grid);
        CategoryDatabase categoryDatabase = new CategoryDatabase();
        CategoryGridAdapter adapter = new CategoryGridAdapter(categoryDatabase);
        GridView gridView = findViewById(R.id.id_category_grid);
        gridView.setAdapter(adapter);
    }
}