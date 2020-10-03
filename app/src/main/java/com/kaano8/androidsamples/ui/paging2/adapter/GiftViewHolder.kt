package com.kaano8.androidsamples.ui.paging2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.databinding.CourseListItemBinding
import com.kaano8.androidsamples.models.gift.Gift

class GiftViewHolder(private val binding: CourseListItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Gift?) {
        binding.courseCheckBox.text = item?.name
    }

    companion object {
        fun create(parent: ViewGroup): GiftViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.course_list_item, parent, false)
            val binding = CourseListItemBinding.bind(view)
            return GiftViewHolder(binding)
        }
    }
}