package com.kaano8.androidsamples.ui.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.database.AppDatabase
import com.kaano8.androidsamples.repository.NoteRepository
import kotlinx.android.synthetic.main.fragment_add_note.*

class AddNoteFragment : Fragment() {

    private lateinit var addNoteViewModel: AddNoteViewModel

    private val args: AddNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()
        val root = inflater.inflate(R.layout.fragment_add_note, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submitButton?.setOnClickListener {
            val recipient = recipientEditText?.text?.toString() ?: EMPTY_STRING
            val sender = senderEditText?.text?.toString() ?: EMPTY_STRING
            val note = noteEditText?.text?.toString() ?: EMPTY_STRING

            addNoteViewModel.insertOrUpdateNote(args.selectedNoteId, recipient, sender, note)
        }

        with(addNoteViewModel) {
            blankFieldError.observe(viewLifecycleOwner, Observer { error ->
                if (error)
                    Snackbar.make(view, "Error: Some fields are left blank", Snackbar.LENGTH_LONG)
                        .show()
            })

            insertionSuccess.observe(viewLifecycleOwner, Observer { success ->
                if (success)
                    Snackbar.make(view, "Success: Note saved successfully", Snackbar.LENGTH_LONG)
                        .show()
            })

            navigateToHome.observe(viewLifecycleOwner, Observer { navigate ->
                if (navigate == true) {
                    this@AddNoteFragment.findNavController()
                        .navigate(AddNoteFragmentDirections.actionNavAddNoteToNavHome())
                    doneNavigateToHome()
                }
            })

            getNoteById(args.selectedNoteId).observe(viewLifecycleOwner, Observer { note ->
                recipientEditText?.setText(note.recipientName)
                senderEditText?.setText(note.senderName)
                noteEditText?.setText(note.note)

            })
        }
    }

    private fun setupViewModel() {
        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).noteDatabaseDao
        val noteRepository = NoteRepository(dataSource)
        val viewModelFactory = AddNoteViewModelFactory(noteRepository)
        addNoteViewModel =
            ViewModelProvider(this, viewModelFactory).get(AddNoteViewModel::class.java)
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}