package com.kaano8.androidsamples.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kaano8.androidsamples.R

class AddNoteFragment : Fragment() {

    private lateinit var addNoteViewModel: AddNoteViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        addNoteViewModel = ViewModelProvider(this).get(AddNoteViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_note, container, false)
        //val textView: TextView = root.findViewById(R.id.cietext_gallery)
        addNoteViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })
        return root
    }
}