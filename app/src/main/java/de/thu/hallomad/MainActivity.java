package de.thu.hallomad;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.Serializable;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static int JOB_ID = 101;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private TextView resultView;
    private EditText insertValue;
    private ExchangeRateDatabase data;
    private ShareActionProvider shareActionProvider;
    private CurrencyItemAdapter adapter;
    private ExchangeRateUpdateRunnable exchangeRateUpdateRunnable;
    private static int versionDatabase;
    private ExchangeRateDatabaseHelper exchangeRateDatabaseHelper;

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

        exchangeRateDatabaseHelper = new ExchangeRateDatabaseHelper(this);
        versionDatabase = ExchangeRateDatabaseHelper.DATABASE_VERSION;
        jobScheduleUpdateRate();


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
                versionDatabase = versionDatabase + 1;
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
        editor.putInt("versionDatabase", versionDatabase);
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
        int versionDB = prefs.getInt("versionDatabase", 1);
        spinnerFrom.setSelection(source);
        spinnerTo.setSelection(target);
        insertValue.setText(insertAmount);
        resultView.setText(resultExchange);
        versionDatabase = versionDB;

    }


    private void insertDataSQLite() {
        SQLiteDatabase db = exchangeRateDatabaseHelper.getWritableDatabase();
        exchangeRateDatabaseHelper.onUpgrade(db, ExchangeRateDatabaseHelper.DATABASE_VERSION, versionDatabase);
        if (versionDatabase > 1) {
            ContentValues values = new ContentValues();
            String[] currencies = data.getCurrencies();
            for (String currency : currencies) {
                values.put(ExchangeRateDatabaseHelper.XR_ECB_COL_CURRENCY, currency);
                values.put(ExchangeRateDatabaseHelper.XR_EXB_COL_RATE, data.getExchangeRate(currency));
                long newRowId = db.insert(ExchangeRateDatabaseHelper.XR_ECB_TABLE, null, values);
            }
        }

    }

    private void queryDataSQLite() {
        SQLiteDatabase db = exchangeRateDatabaseHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                ExchangeRateDatabaseHelper.XR_ECB_COL_CURRENCY,
                ExchangeRateDatabaseHelper.XR_EXB_COL_RATE
        };
        Cursor cursor = db.query(ExchangeRateDatabaseHelper.XR_ECB_TABLE, projection, null, null, null, null, null);


        while (cursor.moveToNext()) {
            String currency = cursor.getString(cursor.getColumnIndexOrThrow(ExchangeRateDatabaseHelper.XR_ECB_COL_CURRENCY));
            Double rate = cursor.getDouble(cursor.getColumnIndexOrThrow(ExchangeRateDatabaseHelper.XR_EXB_COL_RATE));
            data.setExchangeRate(currency, rate);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onStart() {
        super.onStart();
        queryDataSQLite();
    }

    @Override
    protected void onStop() {
        super.onStop();
        insertDataSQLite();
    }

    private void jobScheduleUpdateRate() {
        ComponentName serviceName = new ComponentName(this, ExchangeRateUpdateJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, serviceName)
                .setPeriodic(24 * 60 * 1000)
                .setPersisted(true)
                .build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = jobScheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS) {
            Log.d("JobSchedule", "Successfully scheduled!");

        }

    }
}