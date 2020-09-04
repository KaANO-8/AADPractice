package com.kaano8.androidsamples.ui.backgroundservices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kaano8.androidsamples.R
import kotlinx.android.synthetic.main.fragment_backgorund_services.*

/**
 * A simple [Fragment] subclass.
 * Use the [BackgroundServicesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BackgroundServicesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_backgorund_services, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        asyncTaskButton?.setOnClickListener {
            findNavController().navigate(BackgroundServicesFragmentDirections.actionNavBackgroundServicesToNavAsyncTask())
        }

        broadcastReceiverButton?.setOnClickListener {
            // navigate to broadcast receiver
        }

        notificationButton?.setOnClickListener {
            findNavController().navigate(BackgroundServicesFragmentDirections.actionNavBackgroundServicesToNavNotifications())
        }

        alarmManagerButton?.setOnClickListener {
            findNavController().navigate(BackgroundServicesFragmentDirections.actionNavBackgroundServicesToNavAlarmManager())
        }
    }
}