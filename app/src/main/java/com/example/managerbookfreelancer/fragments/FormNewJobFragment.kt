package com.example.managerbookfreelancer.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

            if (professionalEntity == null || professionalEntity!!.idProfessional == "-1" ){
                binding.spinnerProfessional.requestFocus()
                Toast.makeText(requireContext(), "Select or creat a professional", Toast.LENGTH_LONG).show()
                return@setOnClickListener

            }

            val jobModel = JobEntity(
                idJob = UUID.randomUUID().toString(),
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

    private fun setSpinner() {

        val listSppiner = ArrayList<ProfessionalEntity>()
        val spinnerAdapter = ArrayAdapter<ProfessionalEntity>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listSppiner
        )

        viewModel.allProfessional.observe(
            viewLifecycleOwner
        ) { p ->
            if (p.isEmpty()){
                listSppiner.add(index = 0, ProfessionalEntity("-1", name = "Create a new Usuario.", contact = "-1", email = "-1"))
            }
            for (prof in p) {
                listSppiner.add(prof)
            }

            spinnerAdapter.notifyDataSetChanged()
        }

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
                        idProfessional = listSppiner[position].idProfessional,
                        name = listSppiner[position].name,
                        contact = listSppiner[position].contact,
                        email = listSppiner[position].email
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

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