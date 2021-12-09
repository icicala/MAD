package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        String imdbId = getIntent().getStringExtra("imdbId");
        String html = readDetailsasHtml(imdbId);
        WebView webView = findViewById(R.id.web_view);
        webView.loadData(html, "text/html", null);
    }

    private String readDetailsasHtml(String imdbid) {
        String queryString = "https://api.p-graf.de/omdb/?r=xml&i=" + imdbid;
        String htmlText = "";

        try {
            URL url = new URL(queryString);
            URLConnection urlConnection = url.openConnection();

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(urlConnection.getInputStream(), urlConnection.getContentEncoding());

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if ("movie".equals(parser.getName())) {
                        for (int i = 0; i <= parser.getAttributeCount(); i++) {
                            String name = parser.getAttributeName(i);
                            String value = parser.getAttributeValue(i);
                            if ("poster".equals(name)) {
                                htmlText += "<h2>" + name + "</h2> <img src='" + value + "'/>";
                            } else {
                                htmlText += "<h2>" + name + "</h2>" + value;
                            }

                        }
                    }
                }
                eventType = parser.next();
            }

        } catch (Exception e) {
            Log.e("OMDBDetails", "Can't query database!" + e.toString());
            e.printStackTrace();
        }
        return htmlText;
    }
}