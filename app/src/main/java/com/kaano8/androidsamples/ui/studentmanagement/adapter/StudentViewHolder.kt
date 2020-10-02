package com.kaano8.androidsamples.ui.studentmanagement.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.database.student.Student
import com.kaano8.androidsamples.databinding.StudentListItemBinding

class StudentViewHolder(
    private val binding: StudentListItemBinding,
    private val clickListener: OnStudentItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Student) {
        binding.apply {
            studentIdValue.text = item.studentId.toString()
            firstNameValue.text = item.firstName
            lastNameValue.text = item.lastName
            classValue.text = item.currentClass
            editDetailsButton.setOnClickListener { clickListener.onEditDetailsClicked(item.studentId) }
            viewDetailsButton.apply {
                try {
                    isEnabled = item.currentClass.toLong() == 0L
                } catch (e: Exception) {
                    Log.d("ParsingException", e.message.toString())
                    isEnabled = true
                }
                setOnClickListener { clickListener.onViewDetailsClicked(item.studentId) }
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            clickListener: OnStudentItemClickListener
        ): StudentViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.student_list_item, parent, false)
            val binding = StudentListItemBinding.bind(view)
            return StudentViewHolder(binding, clickListener)
        }
    }
}