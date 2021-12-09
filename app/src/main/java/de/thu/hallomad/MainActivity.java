package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ArrayAdapter<Movie> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 0. Create ArrayAdapter for ListView
        ListView listView = findViewById(R.id.resultsList);

        adapter = new ArrayAdapter<>(this, R.layout.list_entry, R.id.list_entry_text);

        listView.setAdapter(adapter);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void searchClicked(View view) {


        // 1. Get text from Edittex
        TextView textView = findViewById(R.id.editText);
        String query = textView.getText().toString();
        List<Movie> movies = queryOmdb(query);
        adapter.clear();
        adapter.addAll(movies);

    }

    public List<Movie> queryOmdb(String searchTerm) {
        // 2. Send request to Omdb API
        String queryString = "https://api.p-graf.de/omdb/?r=xml&s=" + searchTerm;

        List<Movie> movieList = new ArrayList<>();
        try {
            URL url = new URL(queryString);
            URLConnection connetion = url.openConnection();
            InputStream stream = connetion.getInputStream();
            String encoding = connetion.getContentEncoding();
            // 3. Getting and analyzing XML response
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(stream, encoding);
            // 4. Build a list<Movie>
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if ("result".equals(parser.getName())) {
                        String title = parser.getAttributeValue(null, "title");
                        String imdbid = parser.getAttributeValue(null, "imdbID");
                        String year = parser.getAttributeValue(null, "year");
                        String type = parser.getAttributeValue(null, "type");

                        Movie m = new Movie(title, year, imdbid, type);
                        movieList.add(m);

                    }
                }
                eventType = parser.next();
            }
            // 5. Give this list to the array adapter(to be created!) of ListView resultList
        } catch (Exception e) {
            Log.i("MovieList:", "Can't query database!" + e.toString());
            e.printStackTrace();
        }


        return movieList;
    }
}