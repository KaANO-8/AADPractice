package com.kaano8.androidsamples.ui.asynctask

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.kaano8.androidsamples.R
import kotlinx.android.synthetic.main.fragment_async_task.*

class AsyncTaskFragment : Fragment(), ProgressBarCallbacks {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_async_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        asyncTaskButton?.setOnClickListener {
            startTask()
        }
    }

    private fun startTask() {
        SimpleAsyncTask(textView = asyncTaskTextView, progressBarCallbacks = this).execute()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun updateProgress(progress: Int) {
        determinateBar?.setProgress(progress, true)
    }
}
