package com.kaano8.androidsamples.ui.studentmanagement.adapter

interface OnStudentItemClickListener {
    fun onEditDetailsClicked(studentId: Long)
    fun onViewDetailsClicked(studentId: Long)
}