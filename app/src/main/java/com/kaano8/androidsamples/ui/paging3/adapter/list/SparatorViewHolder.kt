package com.kaano8.androidsamples.ui.paging3.adapter.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.databinding.SeparatorViewItemBinding

class SeparatorViewHolder(private val binding: SeparatorViewItemBinding) :  RecyclerView.ViewHolder(binding.root) {

    fun bind(separatorText: String) {
        binding.separatorDescription.text = separatorText
    }

    companion object {
        fun create(parent: ViewGroup): SeparatorViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.separator_view_item, parent, false)
            val binding = SeparatorViewItemBinding.bind(view)
            return SeparatorViewHolder(binding)
        }
    }
}