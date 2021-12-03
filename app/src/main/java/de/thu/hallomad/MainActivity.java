package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExchangeRateDatabase data = new ExchangeRateDatabase();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_view, R.id.id_spinner_item, data.getCurrencies());

        Spinner spinnerFrom = findViewById(R.id.id_spinner_from);
        spinnerFrom.setAdapter(adapter);

        Spinner spinnerTo = findViewById(R.id.id_spinner_to);
        spinnerTo.setAdapter(adapter);
    }
}