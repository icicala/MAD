package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CurrencyListActivity extends AppCompatActivity {
    private ExchangeRateDatabase data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        data = new ExchangeRateDatabase();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_view_item, R.id.id_text_view, data.getCurrencies());

        ListView listView = findViewById(R.id.id_currency_list_view);
        listView.setAdapter(adapter);

    }
}