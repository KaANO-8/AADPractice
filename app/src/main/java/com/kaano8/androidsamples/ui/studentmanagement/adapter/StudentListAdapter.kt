package com.kaano8.androidsamples.ui.studentmanagement.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kaano8.androidsamples.database.student.Student

class StudentListAdapter(private val onStudentItemClickListener: OnStudentItemClickListener) :
    ListAdapter<Student, RecyclerView.ViewHolder>(StudentListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StudentViewHolder.create(parent, onStudentItemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as? StudentViewHolder)?.bind(item)
    }

    class StudentListDiffUtil : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean =
            oldItem.studentId == newItem.studentId

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean =
            oldItem == newItem
    }
}