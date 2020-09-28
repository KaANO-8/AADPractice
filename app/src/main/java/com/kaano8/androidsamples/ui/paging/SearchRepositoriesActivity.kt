/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kaano8.androidsamples.ui.paging

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.kaano8.androidsamples.databinding.ActivitySearchRepositoriesBinding
import com.kaano8.androidsamples.di.Injection
import com.kaano8.androidsamples.ui.paging.adapter.list.ReposAdapter
import com.kaano8.androidsamples.ui.paging.adapter.state.ReposLoadStateAdapter
import com.kaano8.androidsamples.util.extensions.showToast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class SearchRepositoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchRepositoriesBinding
    private lateinit var viewModel: SearchRepositoriesViewModel
    private val adapter = ReposAdapter()

    private var searchJob: Job? = null

    private fun search(query: String) {
        // Make sure we cancel an existing search job in case a new search job is triggered
        searchJob?.cancel()

        searchJob = lifecycleScope.launch {
            viewModel.searchRepo(query).collectLatest {
                adapter.submitData(pagingData = it)
            }
        }
    }

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchRepositoriesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // get the view model
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(this))
            .get(SearchRepositoriesViewModel::class.java)

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)

        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        search(query)
        initSearch(query)

        binding.retryButton.setOnClickListener { adapter.retry() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.searchRepo.text.trim().toString())
    }

    private fun initAdapter() {
        binding.list.adapter =
            adapter.withLoadStateHeaderAndFooter(
                header = ReposLoadStateAdapter { adapter.retry() },
                footer = ReposLoadStateAdapter { adapter.retry() }
            )

        adapter.addLoadStateListener { combinedLoadStates ->
            binding.list.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading

            binding.progressBar.isVisible = combinedLoadStates.source.refresh is LoadState.Loading

            binding.retryButton.isVisible = combinedLoadStates.source.refresh is LoadState.Error


            with(combinedLoadStates) {
                val errorState = source.append as? LoadState.Error
                    ?: source.prepend as? LoadState.Error
                    ?: append as? LoadState.Error
                    ?: prepend as? LoadState.Error

                errorState?.let { showToast("\uD83D\uDE28 Wooops ${it.error}") }
            }

        }
    }

    private fun initSearch(query: String) {
        binding.searchRepo.setText(query)

        binding.searchRepo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        binding.searchRepo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState changes.
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.list.scrollToPosition(0) }
        }
    }

    private fun updateRepoListFromInput() {
        binding.searchRepo.text.trim().let {
            if (it.isNotEmpty()) {
                search(query = it.toString())
            }
        }
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }
}
