package com.example.managerbookfreelancer.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.core.JobsDbDataSource
import com.example.managerbookfreelancer.dataBase.JobAppDataBase
import com.example.managerbookfreelancer.databinding.FragmentFormNewJobBinding
import com.example.managerbookfreelancer.model.JobEntity
import com.example.managerbookfreelancer.viewModel.JobsViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FormNewJobFragment : Fragment() {

    private var _binding: FragmentFormNewJobBinding? = null
    private val binding get() = _binding!!
    private  var time: String? = null
    private  var date: String? = null

    private val viewModel: JobsViewModel by activityViewModels(
        factoryProducer = {
            val database = JobAppDataBase.getInstance(requireContext())

            JobsViewModel.Factory(
                repository = JobsDbDataSource(database.JobDAO())
            )
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormNewJobBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getDate()
        getTime()

        binding.BTNSave.setOnClickListener {

            val ownerJob = binding.editTextOwner.text.toString().trim()
            if (ownerJob.isEmpty()) {
                binding.editTextOwner.error = "Please put a name"
                binding.editTextOwner.requestFocus()
                return@setOnClickListener
            }

            val coupleName = binding.editTextBrideName.text.toString().trim()
            if (coupleName.isEmpty()) {
                binding.editTextBrideName.error = "please insert the couple names"
                binding.editTextBrideName.requestFocus()
                return@setOnClickListener
            }

            val weedingLocation = binding.editTextLocation.text.toString().trim()
            if (weedingLocation.isEmpty()) {
                binding.FildEditTextLocation.error = "Please insert the City"
                binding.FildEditTextLocation.requestFocus()
                return@setOnClickListener
            }

            val jobModel = JobEntity(
                engaged = coupleName,
                ownerName = ownerJob,
                weedingDay = date,
                weedingTime = time,
                weedingCity = weedingLocation)

            CoroutineScope(Dispatchers.IO).launch {
                viewModel.insert(jobEntity = jobModel)
            }
           findNavController().navigate(R.id.action_formNewJobFragment_to_recyclerViewFragment)


        }


    }

    private fun getTime() {


        binding.timePickerButton.setOnClickListener {
            val isSystem24Hour = is24HourFormat(context)
            val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setTitleText("Select Time")
                .build()

            picker.show(parentFragmentManager, "TimePicker")

            picker.addOnPositiveButtonClickListener {
                val H = picker.hour
                val M = picker.minute

                this.time = getFormatHours(H, M)
                binding.timePickerButton.text = time
            }
        }

    }

    private fun getFormatHours(hours: Int, min: Int): String {

        val hour: String
        val minutes: String


        if (hours in 0..9) {
            hour = "0$hours"
        } else {
            hour = hours.toString()
        }

        if (min in 0..9) {
            minutes = "0$min"
        } else {
            minutes = min.toString()
        }

        return "$hour : $minutes"


    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate() {

        binding.datePickerButton.setOnClickListener {
            val calendarConstraintBuild = CalendarConstraints.Builder()
            calendarConstraintBuild.setValidator(DateValidatorPointForward.now())


            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setCalendarConstraints(calendarConstraintBuild.build())
                .build()

            datePicker.show(parentFragmentManager, "DatePicker")

            datePicker.addOnPositiveButtonClickListener {
                this.date = SimpleDateFormat("dd/MM/yyyy").format(Date(it))
                binding.datePickerButton.text = date
            }

        }
    }
}