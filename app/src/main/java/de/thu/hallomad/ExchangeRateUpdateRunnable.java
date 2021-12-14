package de.thu.hallomad;

import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class ExchangeRateUpdateRunnable implements Runnable {
    private ExchangeRateDatabase data;
    private MainActivity activity;

    public ExchangeRateUpdateRunnable(MainActivity activity, ExchangeRateDatabase data) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public void run() {
        Log.i("RunnableTime", "Start" + new Date().toString());
        updateCurrencies();
        Log.i("RunnableTime", "Stop" + new Date().toString());
        updateUI();
    }

    synchronized private void updateCurrencies() {
        String dataBaseAPI = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
        try {
            URL url = new URL(dataBaseAPI);
            URLConnection urlConnection = url.openConnection();
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(urlConnection.getInputStream(), urlConnection.getContentEncoding());

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equals("Cube")) {
                        if (parser.getAttributeCount() == 2) {
                            String currency = parser.getAttributeValue(null, "currency");
                            double exchangeRate = Double.parseDouble(parser.getAttributeValue(null, "rate"));
                            data.setExchangeRate(currency, exchangeRate);
                        }
                    }
                }
                eventType = parser.next();
//                Thread.sleep(100); to test when another threat is started while another is running
            }

        } catch (Exception e) {
            Log.e("DatabaseAccess", "Error accessing www.ecb.europa.eu " + e.toString());
            e.printStackTrace();
        }
    }


    synchronized private void updateUI() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.getAdapter().notifyDataSetChanged();
                Toast toast = Toast.makeText(activity, "Currencies Update finished!", Toast.LENGTH_LONG);
                toast.show();

            }
        });

    }
}
