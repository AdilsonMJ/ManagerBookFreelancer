package com.example.managerbookfreelancer.ui.screens.viewjobs

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.adapter.AdapterListJobs
import com.example.managerbookfreelancer.adapter.OnButtonClickListener
import com.example.managerbookfreelancer.core.model.JobModelItem
import com.example.managerbookfreelancer.databinding.FragmentRecyclerViewJobsBinding
import com.example.managerbookfreelancer.utils.Extensions.Companion.setActionBarTitle
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
        setDialogActions()
        viewModel = ViewModelProvider(this)[JobsViewModel::class.java]
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
            setActionBarTitle("Jobs")
        }

        _binding = FragmentRecyclerViewJobsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolBar()
        setAdapter()
        observeJobs()
    }

    private fun observeJobs() {
        viewModel.getAllJobs(showOldItens).observe(viewLifecycleOwner){

            if(it.isNotEmpty()){
                hideEmptyListLayout()
               adapter.upDateJobs(it)
           } else{
                showEmptyListLayout()
           }
        }
    }

    private fun showEmptyListLayout() {
        binding.constraintLayoutRCJobProfessionalEmpty.visibility = View.VISIBLE
    }

    private fun hideEmptyListLayout() {
        binding.constraintLayoutRCJobProfessionalEmpty.visibility = View.GONE
    }

    private fun setAdapter() {
        recyclerView = binding.RCFragmentListJobs
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.BTNRecyclerViewNewJob.setOnClickListener(navigationToNewJob())
        registerForContextMenu(recyclerView)
    }

    private fun navigationToNewJob() =
        Navigation.createNavigateOnClickListener(R.id.action_recyclerViewJobsFragment_to_formNewJobFragment)

    private fun setUpToolBar() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
               menuInflater.inflate(R.menu.menu_recyclerview_jobs, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.showOldJobs -> {
                        menuItem.isChecked = !menuItem.isChecked
                        showOldItens = !showOldItens
                        observeJobs()
                        return true
                    }

                    android.R.id.home -> {
                        findNavController().navigateUp()
                        return true
                    }
                }
                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    private fun setDialogActions() {
        adapter = AdapterListJobs(object : OnButtonClickListener {
            override fun onButtonClick(item: Any) {
                setupDialogOptions(item as JobModelItem)
            }

        })
    }

    private fun setupDialogOptions(job: JobModelItem){
        AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setTitle("Do you want to delete or edite this job")
            .setMessage("Client: ${job.clientName} - Date: ${job.date}")
            .setPositiveButton("Edit") { _, _ ->
                navigationToEditJob(job)
            }.setNeutralButton("Cancel") { _, _ ->
            }
            .setNegativeButton("Delete") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.delete(id = job.idJob)
                }
            }.create().show()

    }

    private fun navigationToEditJob(job: JobModelItem) {
        val action =
            RecyclerViewJobsFragmentDirections.actionRecyclerViewJobsFragmentToFormNewJobFragment(
                job.idJob
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