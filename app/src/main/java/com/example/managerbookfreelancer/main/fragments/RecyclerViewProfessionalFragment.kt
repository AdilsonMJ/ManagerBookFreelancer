package com.example.managerbookfreelancer.main.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.databinding.FragmentRecyclerViewProfessionalBinding
import com.example.managerbookfreelancer.main.adapter.AdapterListProfessional
import com.example.managerbookfreelancer.main.adapter.OnButtonClickListener
import com.example.managerbookfreelancer.main.core.model.ClientModelItem
import com.example.managerbookfreelancer.main.viewModel.ProfessionalViewModel
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
        setAlertDialog()

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

        observeProfessional()

    }

    private fun observeProfessional() {
        viewModel.getAllClients().observe(viewLifecycleOwner) { state ->

            if (state.isNullOrEmpty()){
                binding.constraintLayoutRCProfessionalEmptList.visibility = View.VISIBLE
                binding.BTNRecyclerViewNewProfessional.setOnClickListener (
                    Navigation.createNavigateOnClickListener(R.id.action_recyclerViewProfessionalFragment_to_newProfessional)
                    )
            } else{
                binding.constraintLayoutRCProfessionalEmptList.visibility = View.GONE
                adapter.upDateProfessional(state)
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun setAlertDialog() {

        adapter = AdapterListProfessional(object : OnButtonClickListener {
            override fun onButtonClick(item: Any) {

                val client = item as ClientModelItem

                AlertDialog.Builder(requireContext())
                    .setCancelable(true)
                    .setTitle("Do you want delete this Client? ")
                    .setMessage("Client: ${client.name}")
                    .setPositiveButton("Edit") { _, _ ->
                        val action =
                            RecyclerViewProfessionalFragmentDirections.actionRecyclerViewProfessionalFragmentToNewProfessional(
                                clientID = client.idClient!!
                            )
                        findNavController().navigate(action)
                    }
                    .setNegativeButton("Delete") { _, _ ->
                        if (client.idClient != 0L){
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.delete(client.idClient!!)
                            }
                        }
                    }
                    .setNeutralButton("Cancel"){_,_ ->}
                    .create()
                    .show()

            }

        })


    }

}