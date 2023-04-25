package com.example.managerbookfreelancer.ui.screens.viewProfessional

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.adapter.AdapterListProfessional
import com.example.managerbookfreelancer.adapter.OnButtonClickListener
import com.example.managerbookfreelancer.core.model.ClientModelItem
import com.example.managerbookfreelancer.databinding.FragmentRecyclerViewProfessionalBinding
import com.example.managerbookfreelancer.viewModel.ProfessionalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecyclerViewProfessionalFragment : Fragment() {

    private var _binding: FragmentRecyclerViewProfessionalBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfessionalViewModel>()
    private lateinit var adapter: AdapterListProfessional

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Show action bar
        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.supportActionBar?.show()
        }

        _binding = FragmentRecyclerViewProfessionalBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeProfessional()
    }


    private fun setupRecyclerView() {
        adapter = AdapterListProfessional(object : OnButtonClickListener{
            override fun onButtonClick(item: Any) {
                val client = item as ClientModelItem
                showDeleteOrEditDialog(client)
            }
        })
        binding.RCFragmentListProfessional.layoutManager = LinearLayoutManager(requireContext())
        binding.RCFragmentListProfessional.adapter = adapter
        binding.BTNRecyclerViewNewProfessional.setOnClickListener{navigateToNewProfessional()}
    }

    private fun observeProfessional() {
        viewModel.getAllClients().observe(viewLifecycleOwner) { state ->
            if (state.isNullOrEmpty()) {
                showEmptyListLayout()
            } else {
                hideEmptyListLayout()
                adapter.upDateProfessional(state)
            }

        }
    }

    private fun hideEmptyListLayout() {
        binding.constraintLayoutRCProfessionalEmptList.visibility = View.GONE
    }

    private fun showEmptyListLayout() {
        binding.constraintLayoutRCProfessionalEmptList.visibility = View.VISIBLE
    }

    private fun navigateToNewProfessional() {
        findNavController().navigate(R.id.action_recyclerViewProfessionalFragment_to_newProfessional)
    }

    private fun showDeleteOrEditDialog(client: ClientModelItem) {

                AlertDialog.Builder(requireContext())
                    .setCancelable(true)
                    .setTitle("Do you want delete this Client? ")
                    .setMessage("Client: ${client.name}")
                    .setPositiveButton("Edit") { _, _ ->
                        navigateToEditProfessional(client)
                    }
                    .setNegativeButton("Delete") { _, _ ->
                        deleteProfessional(client)
                    }
                    .setNeutralButton("Cancel") { _, _ -> }
                    .create()
                    .show()
    }

    private fun deleteProfessional(client: ClientModelItem) {
        if (client.idClient != 0L) {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.delete(client.idClient!!)
            }
        }
    }

    private fun navigateToEditProfessional(client: ClientModelItem) {
        val action =
            RecyclerViewProfessionalFragmentDirections.actionRecyclerViewProfessionalFragmentToNewProfessional(
                clientID = client.idClient!!
            )
        findNavController().navigate(action)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null

        // hider action bar
        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.supportActionBar?.hide()
        }

    }
}