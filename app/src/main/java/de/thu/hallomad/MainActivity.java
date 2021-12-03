package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private ExchangeRateDatabase data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new ExchangeRateDatabase();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_view, R.id.id_spinner_item, data.getCurrencies());

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


    public void onClick(View view) {
        TextView resultView = findViewById(R.id.id_result_view);
        EditText insertValue = findViewById(R.id.id_insert_value);
        String source = (String) spinnerFrom.getSelectedItem();
        String target = (String) spinnerTo.getSelectedItem();
        if (String.valueOf(insertValue.getText()).equals("")) {
            insertValue.setText("1.00");
        }
        double valueInserted = Double.parseDouble(String.valueOf(insertValue.getText()));
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        double finalResult = data.convert(valueInserted, source, target);
        resultView.setText(decimalFormat.format(finalResult));
    }
}