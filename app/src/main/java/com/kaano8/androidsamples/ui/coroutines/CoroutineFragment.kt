package com.kaano8.androidsamples.ui.coroutines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.database.coroutines.getDatabase
import com.kaano8.androidsamples.repository.coroutines.TitleRepository
import com.kaano8.androidsamples.workmanager.getNetworkService
import kotlinx.android.synthetic.main.coroutine_fragment.*

class CoroutineFragment : Fragment() {

    private lateinit var repository: TitleRepository
    private val viewModel by viewModels<CoroutineViewModel> { CoroutineViewModelFactory(repository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.coroutine_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = getDatabase(requireActivity().applicationContext)
        repository = TitleRepository(getNetworkService(), database.titleDao)
        // When rootLayout is clicked call onMainViewClicked in ViewModel
        rootLayout?.setOnClickListener {
            viewModel.onMainViewClicked()
        }

        // update the title when the [MainViewModel.title] changes
        viewModel.title.observe(viewLifecycleOwner) { value ->
            value?.let {
                title.text = it
            }
        }

        viewModel.taps.observe(viewLifecycleOwner) { value ->
            taps.text = value
        }

        // show the spinner when [MainViewModel.spinner] is true
        viewModel.spinner.observe(viewLifecycleOwner) { value ->
            value.let { show ->
                spinner.visibility = if (show) View.VISIBLE else View.GONE
            }
        }

        // Show a snackbar whenever the [ViewModel.snackbar] is updated a non-null value
        viewModel.snackbar.observe(viewLifecycleOwner) { text ->
            text?.let {
                Snackbar.make(rootLayout, text, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarShown()
            }
        }
    }
}