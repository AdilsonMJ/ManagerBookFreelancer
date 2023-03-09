package com.example.managerbookfreelancer.main.fragments.form

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.databinding.FragmentFormNewJobBinding
import com.example.managerbookfreelancer.main.core.entity.JobEntity
import com.example.managerbookfreelancer.main.core.model.ClientModelItem
import com.example.managerbookfreelancer.main.utils.Utils
import com.example.managerbookfreelancer.main.viewModel.FormNewJobViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FormNewJobFragment : Fragment() {

    private var _binding: FragmentFormNewJobBinding? = null
    private val binding get() = _binding!!
    private var weddingTimePickup: String = "00:00 AM"
    private var weddingDatePickup: Long? = null
    private var professionalEntity: ClientModelItem? = null
    private val args: FormNewJobFragmentArgs by navArgs()

    private lateinit var viewModel: FormNewJobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FormNewJobViewModel::class.java]
    }


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
        setSpinner(idClient = null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getDate()
        getTime()
        setDateOnFields()
        saveAction()

    }

    private fun saveAction() {
        binding.BTNSave.setOnClickListener {

            val customers = binding.editTextCoupleName.text.toString()
            val location = binding.editTextLocation.text.toString()

            if (customers.isEmpty()) {
                binding.editTextCoupleName.error  = "Please fill this field"
                binding.editTextCoupleName.requestFocus()

                return@setOnClickListener
            }

            if (weddingDatePickup == null) {
                Toast.makeText(
                    requireContext(),
                    "Select Date",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (professionalEntity?.contact == "-1") {
                binding.spinnerProfessional.requestFocus()
                Toast.makeText(
                    requireContext(),
                    "Select or creat a professional",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val jobModel = JobEntity(
                idJob = args.jobId,
                customerEndUser = customers,
                dateOfEvent = weddingDatePickup!!,
                timeOfEvent = weddingTimePickup,
                locationOfEvent = location,
                idClient = professionalEntity?.idClient!!
            )

            viewModel.insert(jobEntity = jobModel)
            findNavController().navigate(R.id.action_formNewJobFragment_to_recyclerViewFragment)
        }
    }

    private fun setDateOnFields() {
        if (args.jobId != 0L) {
            viewLifecycleOwner.lifecycleScope.launch {
                val job = viewModel.getJobById(args.jobId)

                binding.editTextCoupleName.setText(job.customerEndUser)
                binding.editTextLocation.setText(job.locationOfEvent)
                binding.timePickerButton.text = job.timeOfEvent
                weddingTimePickup = job.timeOfEvent.toString()
                weddingDatePickup = job.dateOfEvent
                binding.datePickerButton.text = Utils.formatDate(job.dateOfEvent)
                setSpinner(idClient = job.idClient)

            }
        }
    }

    private fun setSpinner(idClient: Long?) {

        val listSppiner = ArrayList<ClientModelItem>()
        listSppiner.clear()

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listSppiner
        )

        viewModel.getAllClients.observe(
            viewLifecycleOwner
        ) { p ->
            if (p.isEmpty()) {
                listSppiner.add(
                    index = 0,
                    ClientModelItem(name = "Create a new Usuario.", contact = "-1", email = "-1")
                )
            }

            for (prof in p) {
                listSppiner.add(prof)
            }

            if (idClient != null) {
                val index = listSppiner.indexOfFirst { it.idClient == idClient }
                binding.spinnerProfessional.setSelection(index)
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
                    professionalEntity = ClientModelItem(
                        idClient = listSppiner[position].idClient,
                        name = listSppiner[position].name,
                        contact = listSppiner[position].contact,
                        email = listSppiner[position].email
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        val index = listSppiner.indexOfFirst { it.idClient == idClient }
        binding.spinnerProfessional.setSelection(index)

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

                this.weddingTimePickup =
                    getFormattedTime(hours = picker.hour, minutes = picker.minute)
                binding.timePickerButton.text = weddingTimePickup
            }
        }
    }

    private fun getFormattedTime(hours: Int, minutes: Int): String {
        val hourString = if (hours < 10) "0$hours" else hours.toString()
        val minuteString = if (minutes < 10) "0$minutes" else "$minutes"
        val timeFormatted = "$hourString:$minuteString"
        return if (hours < 12) "$timeFormatted AM" else "$timeFormatted PM"
    }

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
                this.weddingDatePickup = it
                binding.datePickerButton.text = Utils.formatDate(it)
            }

        }
    }
}