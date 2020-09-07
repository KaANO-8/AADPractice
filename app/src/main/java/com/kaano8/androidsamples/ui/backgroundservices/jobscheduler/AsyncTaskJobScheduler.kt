package com.kaano8.androidsamples.ui.backgroundservices.jobscheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.AsyncTask
import com.kaano8.androidsamples.util.extensions.showToast

class AsyncTaskJobScheduler: JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        baseContext?.showToast("Sleeping app for 5 secs")
        SleepAsyncTask(this, params).execute()
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

    private class SleepAsyncTask(private val jobService: JobService, private val params: JobParameters?): AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            Thread.sleep(5000)
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            jobService.apply {
                baseContext?.showToast("Completed")
                jobFinished(params, true)
            }
        }
    }
}