package de.thu.hallomad;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;

public class TimerJobService extends JobService {
    TimerAsyncTask timerAsyncTask = new TimerAsyncTask(this);

    @Override
    public boolean onStartJob(JobParameters params) {
        //Warning: This is running on the UI tread.
        // true - running in the background
        //false - work is finished
        // neeed to start a new thread
        Log.d("JobService", "onStartJob");
        timerAsyncTask.execute(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // true reschedule asap false: don't care
        return true;
    }

    private static class TimerAsyncTask extends AsyncTask<JobParameters, Void, JobParameters> {

        private final JobService jobService;

        public TimerAsyncTask(JobService jobService) {
            this.jobService = jobService;
        }

        @Override
        protected JobParameters doInBackground(JobParameters... jobParameters) {
            Log.d("CountDownJobService", "Start operation");
            for (int i = 0; i <= 100; i += 10) {
                try {
                    Thread.sleep(1000);
                    Log.d("CountDownJobService", i + " % completed");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d("CountDownJobService", "Ended operation");
            return jobParameters[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            jobService.jobFinished(jobParameters, false);
        }
    }
}
