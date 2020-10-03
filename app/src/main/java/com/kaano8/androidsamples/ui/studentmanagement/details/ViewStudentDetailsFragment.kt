package com.kaano8.androidsamples.ui.studentmanagement.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.di.Database
import kotlinx.android.synthetic.main.fragment_view_student_details.*


class ViewStudentDetailsFragment : Fragment() {

    private lateinit var viewStudentDetailsViewModel: ViewStudentDetailsViewModel

    private val args: ViewStudentDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_student_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewStudentDetailsViewModel.getStudentWithDetails(args.selectedStudentId)
            .observe(viewLifecycleOwner, { studentWithDetails ->
                studentWithDetails?.apply {
                    studentIdTextView?.text = student.studentId.toString()
                    firstNameTextView?.text = student.firstName
                    lastNameTextView?.text = student.lastName
                    classTextView?.text = student.currentClass
                    addressTextView?.text = studentDetails?.address
                    phoneNumberTextView?.text = studentDetails?.phoneNumber
                }
            })

        viewStudentDetailsViewModel.getStudentWIthCourses(args.selectedStudentId)
            .observe(viewLifecycleOwner, { courseList ->
                courseList.courses.forEach { course ->
                    studentDetailsContainer?.addView(TextView(context).also {
                        it.text = course.courseName
                    })
                }
            })

        updateButton?.setOnClickListener {
            viewStudentDetailsViewModel.updateStudentDetails(args.selectedStudentId)
        }
    }


    private fun setupViewModel() {
        val application = requireNotNull(this.activity).application
        val viewModelFactory =
            ViewStudentDetailsViewModelFactory(Database.getStudentRepository(application))
        viewStudentDetailsViewModel =
            ViewModelProvider(this, viewModelFactory).get(ViewStudentDetailsViewModel::class.java)
    }
}