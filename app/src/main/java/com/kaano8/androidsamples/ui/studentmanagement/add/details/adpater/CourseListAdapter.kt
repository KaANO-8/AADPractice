package com.kaano8.androidsamples.ui.studentmanagement.add.details.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.database.student.course.Course
import com.kaano8.androidsamples.models.CourseListItem


class CourseListAdapter(private val dataSource: List<CourseListItem>) : BaseAdapter() {

    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): Any = dataSource[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (convertView != null) {
            setTextOnTitleTextView(convertView, position)
            return convertView
        } else {
            val newConvertView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.course_list_item, parent, false)
            setTextOnTitleTextView(newConvertView, position)
            return newConvertView
        }

    }

    private fun setTextOnTitleTextView(convertView: View, position: Int) {
        val titleTextView = convertView.findViewById<CheckBox>(R.id.courseCheckBox)
        titleTextView.text = (getItem(position) as? CourseListItem)?.courseName
        titleTextView?.setOnCheckedChangeListener { _ , isChecked ->
            (getItem(position) as? CourseListItem)?.isSelected = isChecked
        }
    }

    fun getDataSource() = dataSource
}