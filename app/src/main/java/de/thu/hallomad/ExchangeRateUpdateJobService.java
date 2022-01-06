package de.thu.hallomad;

import android.app.AsyncNotedAppOp;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExchangeRateUpdateJobService extends JobService {
    ExchangeRateDatabase data = new ExchangeRateDatabase();
    ExchangeRateUpdateAsyncTask exchangeRateUpdateAsyncTask = new ExchangeRateUpdateAsyncTask(this, data);


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("JobService", "onStartJob");
        exchangeRateUpdateAsyncTask.execute(params);
        synchronized (params) {
            params.notifyAll();
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    private static class ExchangeRateUpdateAsyncTask extends AsyncTask<JobParameters, Void, JobParameters> {
        private final JobService jobService;
        private final ExchangeRateDatabase data;

        public ExchangeRateUpdateAsyncTask(JobService jobService, ExchangeRateDatabase data) {
            this.jobService = jobService;
            this.data = data;
        }

        @Override
        protected JobParameters doInBackground(JobParameters... jobParameters) {

            Log.d("JobService", "doinBackground from JobService");
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
                }

            } catch (Exception e) {
                Log.e("DatabaseAccess", "Error accessing www.ecb.europa.eu " + e.toString());
                e.printStackTrace();
            }
            return jobParameters[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            jobService.jobFinished(jobParameters, true);
            Log.d("JobService", "Update exchange Rate Finished");


        }
    }
}
