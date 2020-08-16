package com.kaano8.androidsamples.ui.asynctask

import android.os.AsyncTask
import android.widget.TextView

class SimpleAsyncTask(private val textView: TextView?, private val progressBarCallbacks: ProgressBarCallbacks) : AsyncTask<Unit, Int, String>() {
    override fun doInBackground(vararg params: Unit?): String {
        // Construct a random number
        val randomNumber = (1..10).shuffled().first()

        for (i in 0..randomNumber) {
            // check for isCancelled() here
            publishProgress(((i.toFloat()/randomNumber)*100).toInt())
            Thread.sleep(1000)
        }
        return "Your app slept for $randomNumber secs.."
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        values[0]?.let {
            progressBarCallbacks.updateProgress(it)
        }
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        textView?.text = result
    }
}