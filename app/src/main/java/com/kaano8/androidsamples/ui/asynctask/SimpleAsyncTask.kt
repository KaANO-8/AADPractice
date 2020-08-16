package com.kaano8.androidsamples.ui.asynctask

import android.os.AsyncTask
import android.widget.TextView

class SimpleAsyncTask(private val textView: TextView?): AsyncTask<Unit, Int, String>() {
    override fun doInBackground(vararg params: Unit?): String {
        // Construct a random number
        val randomNumber = (1..10).shuffled().first()

        val timer = randomNumber * 1000L

        try {
            Thread.sleep(timer)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return "Your app slept for $randomNumber secs.."
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        textView?.text = result
    }
}