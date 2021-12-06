package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClicked(View view) {
        // View web page
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://thu.de"));
        // open app of category
//        Intent intent = IntentCompat.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_CALENDAR);
        // open dialing app
//        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+49/731-5028533"));

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+49 733 455555555"));
        startActivity(intent);
    }
}