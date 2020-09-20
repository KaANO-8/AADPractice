package com.kaano8.androidsamples.ui.studentmanagement.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.util.extensions.Database.getStudentRepository
import kotlinx.android.synthetic.main.fragment_add_student.*


class AddStudentFragment : Fragment() {

    private lateinit var addStudentViewModel: AddStudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setupViewModel()
        return inflater.inflate(R.layout.fragment_add_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addStudentButton?.setOnClickListener {
            val firstName = firstNameEditText?.text.toString()
            val lastName = lastNameEditText?.text.toString()
            val currentClass = classEditText?.text.toString()

            addStudentViewModel.addStudent(firstName, lastName, currentClass)
        }

        addStudentViewModel.navigateToStudentList.observe(viewLifecycleOwner, Observer { isNavigationRequired ->
            isNavigationRequired?.let {
                findNavController().navigate(AddStudentFragmentDirections.actionNavAddStudentToNavStudentList())
                addStudentViewModel.doneNavigation()
            }
        })
    }

    private fun setupViewModel() {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = AddStudentViewModelFactory(getStudentRepository(application))
        addStudentViewModel = ViewModelProvider(this, viewModelFactory).get(AddStudentViewModel::class.java)
    }
}