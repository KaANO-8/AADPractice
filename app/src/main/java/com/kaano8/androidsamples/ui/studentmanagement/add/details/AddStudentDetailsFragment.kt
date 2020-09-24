package com.kaano8.androidsamples.ui.studentmanagement.add.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.util.extensions.Database.getStudentRepository
import kotlinx.android.synthetic.main.fragment_add_student_details.*


class AddStudentDetailsFragment : Fragment() {

    private lateinit var addStudentDetailsViewModel: AddStudentDetailsViewModel

    private val args: AddStudentDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setupViewModel()
        return inflater.inflate(R.layout.fragment_add_student_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addStudentDetailsButton?.setOnClickListener {
            val address = addressEditText?.text.toString()
            val phoneNumber = phoneNumberEditText?.text.toString()

            addStudentDetailsViewModel.addStudentDeatils(args.selectedStudentId, address, phoneNumber)
        }

        addStudentDetailsViewModel.navigateToStudentList.observe(viewLifecycleOwner,
            { isNavigationRequired ->
                isNavigationRequired?.let {
                    findNavController().navigate(AddStudentDetailsFragmentDirections.actionNavAddStudentDetailsToNavStudentList())
                    addStudentDetailsViewModel.doneNavigation()
                }
            })
    }

    private fun setupViewModel() {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = AddStudentDetailsViewModelFactory(getStudentRepository(application))
        addStudentDetailsViewModel = ViewModelProvider(this, viewModelFactory).get(AddStudentDetailsViewModel::class.java)
    }
}