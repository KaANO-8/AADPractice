package com.kaano8.androidsamples.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.database.NoteDatabase
import com.kaano8.androidsamples.repository.NoteRepository

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application

        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao

        val repository = NoteRepository(dataSource)

        val viewModelFactory = HomeViewModelFactory(noteRepository = repository)

        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.notes.observe(viewLifecycleOwner, Observer {
            it.forEach {
                textView.text = "${textView.text} \n ${it.note}"
            }
        })
        return root
    }
}