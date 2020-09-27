package com.kaano8.androidsamples.ui.studentmanagement.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.ui.studentmanagement.adapter.OnStudentItemClickListener
import com.kaano8.androidsamples.ui.studentmanagement.adapter.StudentListAdapter
import com.kaano8.androidsamples.util.extensions.Database
import kotlinx.android.synthetic.main.fragment_student_list.*

class StudentListFragment : Fragment() {

    private lateinit var studentListViewModel: StudentListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setupViewModel()
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        addNewStudentFab?.setOnClickListener {
            findNavController().navigate(StudentListFragmentDirections.actionNavStudentListToNavAddStudent())
        }
    }

    private fun setupRecyclerView() {
        val studentListAdapter = StudentListAdapter(object : OnStudentItemClickListener {
            override fun onEditDetailsClicked(studentId: Long) {
                findNavController().navigate(StudentListFragmentDirections.actionNavStudentListToNavAddStudentDetails(studentId))
            }

            override fun onViewDetailsClicked(studentId: Long) {
                findNavController().navigate(StudentListFragmentDirections.actionNavStudentListToNavViewStudentDetails(studentId))
            }
        })
        studentRecyclerView?.adapter = studentListAdapter

        studentListViewModel.studentsList.observe(viewLifecycleOwner, Observer { studentList ->
            studentListAdapter.submitList(studentList)
        })
    }

    private fun setupViewModel() {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = StudentListViewModelFactory(Database.getStudentRepository(application))
        studentListViewModel = ViewModelProvider(this, viewModelFactory).get(StudentListViewModel::class.java)
    }
}