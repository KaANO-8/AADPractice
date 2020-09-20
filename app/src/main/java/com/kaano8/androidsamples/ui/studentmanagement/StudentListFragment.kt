package com.kaano8.androidsamples.ui.studentmanagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.ui.studentmanagement.adapter.StudentListAdapter
import kotlinx.android.synthetic.main.fragment_student_list.*

class StudentListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        studentRecyclerView?.adapter = StudentListAdapter()
    }
}