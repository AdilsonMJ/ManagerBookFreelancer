package com.example.managerbookfreelancer.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.managerbookfreelancer.adapter.AdapterListProfessional
import com.example.managerbookfreelancer.core.model.ClientModelItem
import com.example.managerbookfreelancer.databinding.FragmentRecyclerViewProfessionalBinding
import com.example.managerbookfreelancer.viewModel.ProfessionalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecyclerViewProfessionalFragment : Fragment() {

    private var _binding: FragmentRecyclerViewProfessionalBinding? =null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterListProfessional


    private lateinit var viewModel: ProfessionalViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ProfessionalViewModel::class.java]
        adapter = AdapterListProfessional(onclick = {
            setAlertDialog(it)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun setAlertDialog(it: ClientModelItem) {
        AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setTitle("Do you want delete this Client? ")
            .setMessage("Client: ${it.name}")
            .setPositiveButton("Yes") { _, _ ->

                if (it.idClient != null){
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.delete(it.idClient)
                    }
                }
            }
            .setNegativeButton("No") { _, _ -> }
            .create()
            .show()
    }

}