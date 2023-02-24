package com.example.managerbookfreelancer.fragments.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.core.dataBase.JobAppDataBase
import com.example.managerbookfreelancer.core.entity.ProfessionalEntity
import com.example.managerbookfreelancer.core.repository.ProfessionalRepositoryImpl
import com.example.managerbookfreelancer.databinding.FragmentFormNewProfessionalBinding
import com.example.managerbookfreelancer.viewModel.FormNewProfessionalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FormNewProfessionalFragment : Fragment() {

    private var _binding: FragmentFormNewProfessionalBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FormNewProfessionalViewModel by activityViewModels(
        factoryProducer = {
            val database = JobAppDataBase.getInstance(requireContext())

            FormNewProfessionalViewModel.Factory(
                repository = ProfessionalRepositoryImpl(database.ProfessionalDAO())
            )

        })


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormNewProfessionalBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnSave.setOnClickListener {
            val name = binding.editTextName.requireText()
            val cellphone = binding.editTextCellphone.requireText()
            val email = binding.editTextEmail.requireText()


            val professional = ProfessionalEntity(
                name = name,
                contact = cellphone,
                email = email
            )

            CoroutineScope(Dispatchers.IO).launch {
                viewModel.insert(professional)
            }

            findNavController().navigate(R.id.action_newProfessional_to_recyclerViewProfessionalFragment)

        }

    }


    fun EditText.requireText(): String {
        val text = this.text.toString().trim()
        if (text.isEmpty()) {
            this.error = "Please fill this field"
            this.requestFocus()
        }

        return text
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}