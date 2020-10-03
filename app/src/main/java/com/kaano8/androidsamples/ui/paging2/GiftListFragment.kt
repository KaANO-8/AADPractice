package com.kaano8.androidsamples.ui.paging2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.database.AppDatabase
import com.kaano8.androidsamples.ui.paging2.adapter.GiftPagedListAdapter
import kotlinx.android.synthetic.main.gift_list_fragment.*

class GiftListFragment : Fragment() {

    private val viewModel by viewModels<GiftListViewModel> { GiftListViewModelFactory(AppDatabase.getInstance(requireContext().applicationContext).giftDao) }

    private val adapter: GiftPagedListAdapter by lazy { GiftPagedListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.gift_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        giftRecyclerView?.adapter = adapter
        viewModel.giftList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

}