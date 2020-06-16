package com.kaano8.androidsamples.ui.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kaano8.androidsamples.R
import kotlinx.android.synthetic.main.fragment_add_note.*

class AddNoteFragment : Fragment() {

    private lateinit var addNoteViewModel: AddNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addNoteViewModel = ViewModelProvider(this).get(AddNoteViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_note, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submitButton?.setOnClickListener {
            addNoteViewModel.submitNote(
                recipientEditText?.text?.toString(),
                senderEditText?.text?.toString(),
                noteEditText?.text?.toString()
            )
        }

    }
}