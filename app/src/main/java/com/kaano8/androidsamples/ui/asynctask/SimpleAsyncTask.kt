package com.kaano8.androidsamples.ui.asynctask

import android.os.AsyncTask
import android.widget.TextView

class SimpleAsyncTask(private val textView: TextView?) : AsyncTask<Unit, Int, String>() {
    override fun doInBackground(vararg params: Unit?): String {
        // Construct a random number
        val randomNumber = (1..10).shuffled().first()

        for (i in randomNumber downTo 1) {
            // check for isCancelled() here
            publishProgress(i)
            Thread.sleep(1000)
        }
        return "Your app slept for $randomNumber secs.."
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        textView?.text = "Waking app in ${values[0]} secs."
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        textView?.text = result
    }
}