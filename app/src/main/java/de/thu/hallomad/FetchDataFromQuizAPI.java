package de.thu.hallomad;

import android.util.Log;
import android.webkit.HttpAuthHandler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FetchDataFromQuizAPI {

    /**
     * Access the Quiz Api with given url
     * Store the result of the Api in InputStream object
     *
     * @param queryApi - url of type string
     * @return raw data from Quiz Api
     */
    public String fetchDataFromQuizApi(String queryApi) {
        String response = null;
        try {

            URL url = new URL(queryApi);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            response = convertStreamToString(inputStream);
        } catch (Exception e) {
            Log.e("Json", e.getMessage());
        }
        return response;
    }

    /**
     * Transform InputStream Object into String of all data from Quiz Api
     *
     * @param inputStream
     * @return
     */
    private String convertStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.d("Failed to close", e.getMessage());
            }
        }

        return stringBuilder.toString();
    }


}
