package com.example.managerbookfreelancer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.core.dataBase.JobAppDataBase
import com.example.managerbookfreelancer.core.model.ProfessionalEntity
import com.example.managerbookfreelancer.core.repository.ProfessionalRepositoryImpl
import com.example.managerbookfreelancer.databinding.FragmentNewProfessionalBinding
import com.example.managerbookfreelancer.viewModel.FormNewProfessionalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID


class NewProfessionalFragment : Fragment() {

    private var _binding: FragmentNewProfessionalBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FormNewProfessionalViewModel by activityViewModels (
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
        _binding = FragmentNewProfessionalBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnSave.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            if (name.isEmpty()){
                binding.editTextName.error = "Please insert a Name"
                binding.editTextName.requestFocus()
                return@setOnClickListener
            }

            val cellphone = binding.editTextCellphone.text.toString().trim()
            if (cellphone.isEmpty()){
                binding.editTextCellphone.error = "Please insert cellphone"
                binding.editTextCellphone.requestFocus()
                return@setOnClickListener
            }

            val email = binding.editTextEmail.text.toString().trim()
            if (email.isEmpty()){
                binding.editTextEmail.error = "please insert Email"
                binding.editTextEmail.requestFocus()
                return@setOnClickListener
            }

            val professional = ProfessionalEntity(
                id = UUID.randomUUID().toString(),
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}