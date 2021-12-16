package de.thu.hallomad;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private TextView resultView;
    private EditText insertValue;
    private ExchangeRateDatabase data;
    private ShareActionProvider shareActionProvider;
    private CurrencyItemAdapter adapter;
    private ExchangeRateUpdateRunnable exchangeRateUpdateRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Toolbar initializing
        Toolbar toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        data = new ExchangeRateDatabase();
        adapter = new CurrencyItemAdapter(data);

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

        /**
         * Initialize the Edittext and Result
         */
        resultView = findViewById(R.id.id_result_view);
        insertValue = findViewById(R.id.id_insert_value);
    }


    public void onClicked(View view) {
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
        Intent intent = new Intent(MainActivity.this, CurrencyListActivity.class);
        switch (item.getItemId()) {
            case R.id.id_currency_list_menu:
                intent.putExtra("EditMode", false);
                startActivity(intent);
                return true;
            case R.id.id_edit_currency_list:
                intent.putExtra("EditMode", true);
                startActivity(intent);
                return true;
            case R.id.id_refresh_rate:
                exchangeRateUpdateRunnable = new ExchangeRateUpdateRunnable(this, data);
                new Thread(exchangeRateUpdateRunnable).start();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public CurrencyItemAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        int source = spinnerFrom.getSelectedItemPosition();
        int target = spinnerTo.getSelectedItemPosition();
        editor.putInt("source", source);
        editor.putInt("target", target);
        editor.putString("insertValue", insertValue.getText().toString());
        editor.putString("resultView", resultView.getText().toString());
        editor.apply();

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        int source = prefs.getInt("source", 0);
        int target = prefs.getInt("target", 0);
        String insertAmount = prefs.getString("insertValue", "0");
        String resultExchange = prefs.getString("resultView", "0.0");
        spinnerFrom.setSelection(source);
        spinnerTo.setSelection(target);
        insertValue.setText(insertAmount);
        resultView.setText(resultExchange);


    }
}