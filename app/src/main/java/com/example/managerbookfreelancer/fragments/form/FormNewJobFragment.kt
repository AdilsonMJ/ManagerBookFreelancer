package com.example.managerbookfreelancer.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.core.dataBase.JobAppDataBase
import com.example.managerbookfreelancer.core.model.JobEntity
import com.example.managerbookfreelancer.core.model.ProfessionalEntity
import com.example.managerbookfreelancer.core.repository.JobsRepositoryImpl
import com.example.managerbookfreelancer.core.repository.ProfessionalRepositoryImpl
import com.example.managerbookfreelancer.databinding.FragmentFormNewJobBinding
import com.example.managerbookfreelancer.viewModel.FormNewJobViewModel
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
    private var time: String? = null
    private var date: String? = null
    private var professionalEntity: ProfessionalEntity? = null

    private val viewModel: FormNewJobViewModel by activityViewModels(
        factoryProducer = {
            val database = JobAppDataBase.getInstance(requireContext())

            FormNewJobViewModel.Factory(
                repository = JobsRepositoryImpl(database.JobDAO()),
                repositoryProfessionalRepository = ProfessionalRepositoryImpl(database.ProfessionalDAO())
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


    override fun onResume() {
        super.onResume()
        setSpinner()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getDate()
        getTime()


        binding.BTNSave.setOnClickListener {

            val coupleName = binding.editTextBrideName.requireText()
            val weedingLocation = binding.editTextLocation.requireText()

            if (professionalEntity?.contact == "-1") {
                binding.spinnerProfessional.requestFocus()
                Toast.makeText(
                    requireContext(),
                    "Select or create a professional",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val jobModel = JobEntity(
                engaged = coupleName,
                ownerName = professionalEntity!!.name,
                weedingDay = date,
                weedingTime = time,
                weedingCity = weedingLocation,
                professional = professionalEntity!!
            )

            CoroutineScope(Dispatchers.IO).launch {
                viewModel.insert(jobEntity = jobModel)
            }
            findNavController().navigate(R.id.action_formNewJobFragment_to_recyclerViewFragment)


        }

    }

    private fun EditText.requireText(): String {

        val text = this.text.toString().trim()
        if (text.isEmpty()) {
            this.error = "Please fill this field"
            this.requestFocus()
        }

        return text
    }

    private fun setSpinner() {

        val listSpinner = ArrayList<ProfessionalEntity>()

        val spinnerAdapter = ArrayAdapter<ProfessionalEntity>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listSpinner
        )



        binding.spinnerProfessional.adapter = spinnerAdapter
        binding.spinnerProfessional.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    professionalEntity = ProfessionalEntity(
                        idProfessional = listSpinner[position].idProfessional,
                        name = listSpinner[position].name,
                        contact = listSpinner[position].contact,
                        email = listSpinner[position].email
                    )
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        viewModel.allProfessional.observe(
            viewLifecycleOwner
        ) { professional ->
            if (professional.isNotEmpty()) {
                listSpinner.addAll(professional)
            } else {
                val message = ProfessionalEntity( name = "Create a new Usuario.", contact = "-1", email = "-1")
                listSpinner.add(message)
            }

            spinnerAdapter.notifyDataSetChanged()
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

                this.time = getFormattedTime(hours = picker.hour, minutes = picker.minute)
                binding.timePickerButton.text = time
            }
        }


    }

    private fun getFormattedTime(hours: Int, minutes: Int): String {
        val hourString = if (hours < 10) "0$hours" else hours.toString()
        val minuteString = if (minutes < 10) "0$minutes" else minutes.toString()
        return "$hourString:$minuteString"
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