package com.example.managerbookfreelancer.ui.screens.jobform

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.core.entity.JobEntity
import com.example.managerbookfreelancer.core.model.ClientModelItem
import com.example.managerbookfreelancer.databinding.FragmentFormNewJobBinding
import com.example.managerbookfreelancer.utils.Constants.Companion.IDTOEMPTYOBJECT
import com.example.managerbookfreelancer.utils.Constants.Companion.STANDARDTIME
import com.example.managerbookfreelancer.utils.Utils
import com.example.managerbookfreelancer.viewModel.FormNewJobViewModel
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

    private var weddingTimePickup: String = STANDARDTIME
    private var weddingDatePickup: Long? = null
    private var professionalEntity: ClientModelItem? = null


    private val args: FormNewJobFragmentArgs by navArgs()
    private val viewModel by viewModels<FormNewJobViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormNewJobBinding.inflate(inflater, container, false)

        // Show action bar
        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.supportActionBar?.show()
        }
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        spinner(idClient = null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.datePickerButton.setOnClickListener { getDate() }
        getTime()
        setDateOnFields()
        listenerButtonSave()
    }

    private fun listenerButtonSave() {
        binding.BTNSave.setOnClickListener {

            val customers = binding.editTextCoupleName.requireText()
            val location = binding.editTextLocation.requireText()

            if (professionalEntity?.contact == IDTOEMPTYOBJECT.toString()) {
                binding.spinnerProfessional.requestFocus()
                toastEmptyFillds("Select or create new professional")
                return@setOnClickListener
            }

            if (weddingDatePickup == null) {
                binding.datePickerButton.requestFocus()
                toastEmptyFillds("Select an data")
                getDate()
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

    private fun toastEmptyFillds(msg: String) {
        Toast.makeText(
            requireContext(),
            msg,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun EditText.requireText(): String {
        val text = this.text.toString().trim()
        if (text.isEmpty()) {
            this.error = "Please fill this field"
            this.requestFocus()
        }
        return text
    }

    private fun setDateOnFields() {
        if (args.jobId != 0L) {
            viewLifecycleOwner.lifecycleScope.launch {
                val job = viewModel.getJobById(args.jobId)

                binding.editTextCoupleName.setText(job.customerEndUser)
                binding.editTextLocation.setText(job.locationOfEvent)
                binding.timePickerButton.text = job.timeOfEvent
                weddingTimePickup = job.timeOfEvent.toString()
                binding.datePickerButton.text = Utils.formatDate(job.dateOfEvent)
                spinner(idClient = job.idClient)
            }
        }
    }

    private fun spinner(idClient: Long?) {

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
                    ClientModelItem(name = "Create a new User.", contact = "0", email = "0")
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
                    Utils.getFormattedTime(hours = picker.hour, minutes = picker.minute)
                binding.timePickerButton.text = weddingTimePickup
            }
        }
    }

    private fun getDate() {

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
    
    override fun onDestroy() {
        super.onDestroy()
        _binding = null

        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.supportActionBar?.hide()
        }
    }
}