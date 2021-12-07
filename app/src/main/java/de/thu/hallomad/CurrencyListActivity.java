package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CurrencyListActivity extends AppCompatActivity {
    private ExchangeRateDatabase data;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        data = new ExchangeRateDatabase();
        CurrencyItemAdapter adapter = new CurrencyItemAdapter(data);

        listView = findViewById(R.id.id_currency_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currencyName = (String) adapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0`?q=" + data.getCapital(currencyName)));
                startActivity(intent);
            }
        });

    }





}