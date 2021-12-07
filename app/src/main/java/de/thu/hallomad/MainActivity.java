package de.thu.hallomad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private ExchangeRateDatabase data;
    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar initializing
        Toolbar toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        data = new ExchangeRateDatabase();
        CurrencyItemAdapter adapter = new CurrencyItemAdapter(data);

        /**
         * Spinner Source
         */
        spinnerFrom = findViewById(R.id.id_spinner_from);
        spinnerFrom.setAdapter(adapter);

        /**
         * Spinner Target
         */
        spinnerTo = findViewById(R.id.id_spinner_to);
        spinnerTo.setAdapter(adapter);
    }


    public void onClicked(View view) {
        TextView resultView = findViewById(R.id.id_result_view);
        EditText insertValue = findViewById(R.id.id_insert_value);
        String source = (String) spinnerFrom.getSelectedItem();
        String target = (String) spinnerTo.getSelectedItem();
        if (String.valueOf(insertValue.getText()).equals("")) {
            insertValue.setText(R.string.DefaultValue);
        }
        double valueInserted = Double.parseDouble(String.valueOf(insertValue.getText()));
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        double finalResult = data.convert(valueInserted, source, target);
        resultView.setText(decimalFormat.format(finalResult));
        String messageShare = "Currency Converter says: " +
                valueInserted + " " + source + " are " + decimalFormat.format(finalResult) + " " + target;
        setShareText(messageShare);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem shareItem = menu.findItem(R.id.id_share);
        shareActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(shareItem);
        setShareText(null);
        return true;


    }

    private void setShareText(String text) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        if (text != null) {
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        }
        shareActionProvider.setShareIntent(shareIntent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_currency_list_menu:
                Intent intent = new Intent(MainActivity.this, CurrencyListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}