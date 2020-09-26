package com.kaano8.androidsamples.models

import com.kaano8.androidsamples.database.student.course.Course

data class CourseListItem(
    val courseId: Long,
    val courseName: String,
    var isSelected: Boolean = false
)

fun Course.toCourseListItem(): CourseListItem = CourseListItem(courseId = this.courseId, courseName = this.courseName)

fun CourseListItem.toCourseItem(): Course = Course(courseId = this.courseId, courseName = this.courseName)