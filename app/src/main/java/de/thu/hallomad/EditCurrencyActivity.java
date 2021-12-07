package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditCurrencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_currency);

        String flag = (String) getIntent().getSerializableExtra("Flag");
        int imageId = getResources().getIdentifier(flag, "drawable", getPackageName());
        ImageView imageView = findViewById(R.id.id_flag_editmode);
        imageView.setImageResource(imageId);

        String currencyName = (String) getIntent().getSerializableExtra("Currency");
        TextView textView = findViewById(R.id.id_currency_editMode);
        textView.setText(currencyName);

        double rate = (double) getIntent().getSerializableExtra("Rate");
        EditText editText = findViewById(R.id.id_rate_editMode);
        editText.setText(String.valueOf(rate));

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("newExchangeRate", editText.getText().toString());
                    setResult(RESULT_OK, returnIntent);
                    finish();
                    return true;
                }
                return false;
            }
        });

    }
}