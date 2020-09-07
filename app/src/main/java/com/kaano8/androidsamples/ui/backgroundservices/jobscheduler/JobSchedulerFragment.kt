package com.kaano8.androidsamples.ui.backgroundservices.jobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context.JOB_SCHEDULER_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.util.extensions.showToast
import kotlinx.android.synthetic.main.fragment_job_scheduler.*

class JobSchedulerFragment : Fragment() {

    private var jobScheduler: JobScheduler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_scheduler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scheduleJobButton?.setOnClickListener { scheduleJob() }
        cancelJobButton?.setOnClickListener { cancelJob() }
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                overrideValueSubtitleTextView?.text = if (progress > 0)
                    "$progress secs"
                else
                    "Not set"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Not required
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Not required
            }
        })
    }

    private fun scheduleJob() {

        jobScheduler = activity?.getSystemService(JOB_SCHEDULER_SERVICE) as? JobScheduler

        val networkOption = when (networkOptions?.checkedRadioButtonId) {
            R.id.noNetwork -> JobInfo.NETWORK_TYPE_NONE
            R.id.anyNetwork -> JobInfo.NETWORK_TYPE_ANY
            R.id.wifiNetwork -> JobInfo.NETWORK_TYPE_UNMETERED
            else -> JobInfo.NETWORK_TYPE_NONE
        }

        val deadlineTimeout = seekBar.progress * 1000L

        val jobBuilder = JobInfo.Builder(
            JOB_ID,
            ComponentName(requireActivity().packageName, NotificationJobService::class.java.name)
        )
            .setRequiredNetworkType(networkOption)
            .setRequiresDeviceIdle(idleSwitch?.isChecked == true)
            .setRequiresCharging(chargingSwitch?.isChecked == true)
            .setOverrideDeadline(deadlineTimeout)



        val constraintSet =
            networkOption != JobInfo.NETWORK_TYPE_NONE || idleSwitch?.isChecked == true || chargingSwitch?.isChecked == true || seekBar.progress > 0

        if (constraintSet)
            jobScheduler?.schedule(jobBuilder.build())
        else
            requireActivity().showToast("Please set at least one constraint")
    }

    private fun cancelJob() {
        jobScheduler?.cancelAll()
        jobScheduler = null
        requireActivity().showToast("Jobs cancelled")
    }

    companion object {
        private const val JOB_ID = 0
    }
}