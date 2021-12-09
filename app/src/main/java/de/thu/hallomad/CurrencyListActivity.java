package de.thu.hallomad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class CurrencyListActivity extends AppCompatActivity {
    private ExchangeRateDatabase data;
    private ListView listView;
    private boolean editMode;
    private CurrencyItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        data = new ExchangeRateDatabase();
        adapter = new CurrencyItemAdapter(data);

        listView = findViewById(R.id.id_currency_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editMode = (boolean) getIntent().getSerializableExtra("EditMode");

                if (!editMode) {
                    String currencyName = (String) adapter.getItem(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0`?q=" + data.getCapital(currencyName)));
                    startActivity(intent);
                } else {
                    String currencyName = (String) adapter.getItem(position);
                    String flag = "flag_" + currencyName.toLowerCase();
                    double rate = data.getExchangeRate(currencyName);
                    Intent intent = new Intent(CurrencyListActivity.this, EditCurrencyActivity.class);
                    intent.putExtra("Flag", flag);
                    intent.putExtra("Currency", currencyName);
                    intent.putExtra("Rate", rate);
                    startActivityForResult(intent, position);
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        double newRate = Double.parseDouble(data.getStringExtra("newExchangeRate"));
        String currency = (String) adapter.getItem(requestCode);
        this.data.setExchangeRate(currency, newRate);
        adapter.notifyDataSetChanged();


    }
}