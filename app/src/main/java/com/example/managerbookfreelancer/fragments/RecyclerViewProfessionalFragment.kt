package com.example.managerbookfreelancer.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.managerbookfreelancer.adapter.AdapterListProfessional
import com.example.managerbookfreelancer.core.dataBase.JobAppDataBase
import com.example.managerbookfreelancer.core.repository.ClientRepositoryImpl
import com.example.managerbookfreelancer.databinding.FragmentRecyclerViewProfessionalBinding
import com.example.managerbookfreelancer.viewModel.ProfessionalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RecyclerViewProfessionalFragment : Fragment() {

    private var _binding: FragmentRecyclerViewProfessionalBinding? =null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterListProfessional


    private val viewModel: ProfessionalViewModel by activityViewModels(
        factoryProducer = {
            val dataBase = JobAppDataBase.getInstance(requireContext())
            ProfessionalViewModel.Factory(repository = ClientRepositoryImpl(dataBase.ClientDAO()))
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = AdapterListProfessional(onclick = {
            AlertDialog.Builder(requireContext())
                .setCancelable(true)
                .setTitle("Do you want delete this Client? ")
                .setMessage("Client: ${it.name}")
                .setPositiveButton("Yes"){_,_ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.delete(it)
                    }
                }
                .setNegativeButton("No") {_,_ ->}
                .create()
                .show()

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewProfessionalBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.RCFragmentListProfessional.layoutManager = LinearLayoutManager(requireContext())
        binding.RCFragmentListProfessional.adapter = adapter
        
        viewModel.getAllClients().observe(viewLifecycleOwner){state ->
            adapter.upDateProfessional(state)
        }

    }


}