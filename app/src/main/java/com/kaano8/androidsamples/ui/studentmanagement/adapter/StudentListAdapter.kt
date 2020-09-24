package com.kaano8.androidsamples.ui.studentmanagement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.database.student.Student

class StudentListAdapter(private val onStudentItemClickListener: OnStudentItemClickListener): ListAdapter<Student, RecyclerView.ViewHolder>(StudentListDiffUtil())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val row = inflater.inflate(R.layout.student_list_item, parent, false)
        return StudentViewHolder(row)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder as StudentViewHolder) {
            studentIdText.text = item.studentId.toString()
            firstNameText.text = item.firstName
            lastNameText.text = item.lastName
            classText.text = item.currentClass
            editButton.setOnClickListener { }
            editDetailsButton.setOnClickListener { onStudentItemClickListener.onEditDetailsClicked(item.studentId) }
            viewDetailsButton.setOnClickListener { onStudentItemClickListener.onViewDetailsClicked(item.studentId) }
        }
    }

    class StudentViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val studentIdText: TextView = view.findViewById(R.id.studentIdValue)
        val firstNameText: TextView = view.findViewById(R.id.firstNameValue)
        val lastNameText: TextView = view.findViewById(R.id.lastNameValue)
        val classText: TextView = view.findViewById(R.id.classValue)
        val editButton: Button = view.findViewById(R.id.editButton)
        val editDetailsButton: Button = view.findViewById(R.id.editDetailsButton)
        val viewDetailsButton: Button = view.findViewById(R.id.viewDetailsButton)
    }

    class StudentListDiffUtil: DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean = oldItem.studentId == newItem.studentId

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean = oldItem == newItem
    }
}