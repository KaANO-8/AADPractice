package com.kaano8.androidsamples.ui.paging2.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kaano8.androidsamples.models.gift.Gift

class GiftPagedListAdapter: PagedListAdapter<Gift, GiftViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: GiftViewHolder, position: Int) = holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiftViewHolder =
        GiftViewHolder.create(parent)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Gift>() {
            override fun areItemsTheSame(oldConcert: Gift, newConcert: Gift) = oldConcert.id == newConcert.id

            override fun areContentsTheSame(oldConcert: Gift, newConcert: Gift) = oldConcert == newConcert
        }
    }

}