package com.example.managerbookfreelancer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.managerbookfreelancer.Dummy.MockJobs
import com.example.managerbookfreelancer.adapter.AdapterListJobs
import com.example.managerbookfreelancer.databinding.FragmentRecyclerViewJobsBinding
import com.example.managerbookfreelancer.viewModel.JobsViewModel

class RecyclerViewFragment : Fragment() {

    private var _binding: FragmentRecyclerViewJobsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterListJobs
    private val viewModel: JobsViewModel by activityViewModels {
        JobsViewModel.Factory(MockJobs)
    }
    private var jobModel: JobModel? = null

    private val args : RecyclerViewFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = AdapterListJobs(onClick = {
            viewModel.delet(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerViewJobsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.RCFragmentListJobs.layoutManager = LinearLayoutManager(requireContext())
        binding.RCFragmentListJobs.adapter = adapter

        viewModel.stateOnceAndStream().observe(viewLifecycleOwner) { state ->
            adapter.upDateJobs(state.jobsList)
        }

        val jobModel: JobModel? = args.jobModelObjectToSave
        if (jobModel != null) {
            viewModel.insert(jobModel)
        }
    }

    override fun onPause() {
        super.onPause()
        jobModel = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}