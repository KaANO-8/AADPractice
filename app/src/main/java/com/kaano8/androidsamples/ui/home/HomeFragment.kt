package com.kaano8.androidsamples.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.models.note.Note
import com.kaano8.androidsamples.database.AppDatabase
import com.kaano8.androidsamples.repository.note.NoteRepository
import com.kaano8.androidsamples.ui.home.adapter.HomeListAdapter
import com.kaano8.androidsamples.ui.home.adapter.NoteListItemClickListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        homeViewModel.clearDatabaseSnackBarEvent.observe(
            viewLifecycleOwner,
            Observer { didEventOccur ->
                if (didEventOccur) {
                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.cleared_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    homeViewModel.doneShowingSnackbar()
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_clear_database -> {
                homeViewModel.clearDatabase()
            }
            R.id.action_settings -> {
                findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavSettings())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        val application = requireNotNull(activity).application
        val noteDataSource = AppDatabase.getInstance(application).noteDatabaseDao
        val giftDataSource = AppDatabase.getInstance(application).giftDao
        val repository = NoteRepository(noteDataSource, giftDataSource)
        val viewModelFactory = HomeViewModelFactory(noteRepository = repository)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private fun setupRecyclerView() {
        // As simple as that
        val adapter = HomeListAdapter(object : NoteListItemClickListener {
            override fun onDeleteItem(note: Note) {
                homeViewModel.deleteNote(note)
            }

            override fun onEditItem(note: Note) {
                findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavAddNote(note.noteId))
            }
        })
        homeRecyclerView?.adapter = adapter
        // Observe data from viewModel
        homeViewModel.notes.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}
