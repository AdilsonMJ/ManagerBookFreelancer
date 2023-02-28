package com.example.managerbookfreelancer.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.adapter.AdapterListJobs
import com.example.managerbookfreelancer.databinding.FragmentRecyclerViewJobsBinding
import com.example.managerbookfreelancer.viewModel.JobsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecyclerViewJobsFragment : Fragment() {

    private var _binding: FragmentRecyclerViewJobsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterListJobs
    private lateinit var recyclerView: RecyclerView
    var showOldItens: Boolean = false

    private lateinit var viewModel: JobsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDialogDelete()
        viewModel = ViewModelProvider(this)[JobsViewModel::class.java]
    }

    private fun setDialogDelete() {
        adapter = AdapterListJobs(onClick = {
            val dialog = AlertDialog.Builder(requireContext())
                .setCancelable(true)
                .setTitle("Do you want to delete or edite this job")
                .setMessage("Client: ${it.clientName} - Date: ${it.date}")
                .setPositiveButton("Delete") { _, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.delete(id = it.idJob)
                    }
                }.setNeutralButton("Cancel") { _, _ ->
                }
                .setNegativeButton("Edite") { _, _ ->
                    val action =
                        RecyclerViewJobsFragmentDirections.actionRecyclerViewJobsFragmentToFormNewJobFragment(
                            it.idJob
                        )
                    findNavController().navigate(action)
                }.create()

            dialog.show()

        })
    }

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

        _binding = FragmentRecyclerViewJobsBinding.inflate(inflater, container, false)
        recyclerView = binding.RCFragmentListJobs
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Register the Context menu on RecyclerView
        registerForContextMenu(recyclerView)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolBar()
        observeJobs()


    }

    private fun observeJobs() {
        viewModel.getAllJobs(showOldItens).observe(viewLifecycleOwner){
            adapter.upDateJobs(it)
        }
    }


    private fun setUpToolBar() {
        val activity = requireActivity()
        activity.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_recyclerview_jobs, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.showOlditens -> {
                        showOldItens = !menuItem.isChecked
                        menuItem.isChecked = !menuItem.isChecked
                        observeJobs()
                    }
                }
                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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