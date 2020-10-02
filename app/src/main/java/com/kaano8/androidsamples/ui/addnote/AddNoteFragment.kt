package com.kaano8.androidsamples.ui.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.database.AppDatabase
import com.kaano8.androidsamples.models.gift.Gift
import com.kaano8.androidsamples.repository.note.NoteRepository
import kotlinx.android.synthetic.main.fragment_add_note.*

class AddNoteFragment : Fragment() {

    private lateinit var addNoteViewModel: AddNoteViewModel

    private val args: AddNoteFragmentArgs by navArgs()

    private lateinit var gifts: List<Gift>

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

        addNoteViewModel.gifts.observe(viewLifecycleOwner, Observer { giftList -> populateSpinner(giftList) })

        submitButton?.setOnClickListener {
            val recipient = recipientEditText?.text?.toString() ?: EMPTY_STRING
            val sender = senderEditText?.text?.toString() ?: EMPTY_STRING
            val note = noteEditText?.text?.toString() ?: EMPTY_STRING

            /**
             * if spinner is not setup correctly then it wil return position as -1,
             * so be careful of index out of bound exception
             */
            val gift = gifts[giftSpinner?.selectedItemPosition ?: 0]

            addNoteViewModel.insertOrUpdateNote(args.selectedNoteId, recipient, sender, note, gift)
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

            if (args.selectedNoteId != -1L) {
                getNoteById(args.selectedNoteId).observe(viewLifecycleOwner, Observer { note ->
                    recipientEditText?.setText(note.recipientName)
                    senderEditText?.setText(note.senderName)
                    noteEditText?.setText(note.note)
                })
            }
        }
    }

    private fun setupViewModel() {
        val application = requireNotNull(this.activity).application
        val noteDataSource = AppDatabase.getInstance(application).noteDatabaseDao
        val giftDataSource = AppDatabase.getInstance(application).giftDao
        val noteRepository = NoteRepository(noteDataSource, giftDataSource)
        val viewModelFactory = AddNoteViewModelFactory(noteRepository)
        addNoteViewModel =
            ViewModelProvider(this, viewModelFactory).get(AddNoteViewModel::class.java)
    }

    private fun populateSpinner(giftList: List<Gift>) {
        gifts = giftList
        val spinnerList = giftList.map { it.name }
        val adapter = ArrayAdapter<String>(requireNotNull(activity?.baseContext), R.layout.support_simple_spinner_dropdown_item, spinnerList)
        giftSpinner.adapter = adapter
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}