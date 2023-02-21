package com.example.managerbookfreelancer.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.managerbookfreelancer.adapter.AdapterListJobs
import com.example.managerbookfreelancer.core.dataBase.JobAppDataBase
import com.example.managerbookfreelancer.core.repository.JobsRepositoryImpl
import com.example.managerbookfreelancer.databinding.FragmentRecyclerViewJobsBinding
import com.example.managerbookfreelancer.resource.Resoucers
import com.example.managerbookfreelancer.viewModel.JobsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar


class RecyclerViewJobsFragment : Fragment() {

    private var _binding: FragmentRecyclerViewJobsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterListJobs

    private val viewModel: JobsViewModel by activityViewModels(
        factoryProducer = {
            val database = JobAppDataBase.getInstance(requireContext())

            JobsViewModel.Factory(
                repository = JobsRepositoryImpl(database.JobDAO())
            )
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = AdapterListJobs(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.delete(jobEntity = it)
            }
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var showOldItens: Boolean = false

        binding.RCFragmentListJobs.layoutManager = LinearLayoutManager(requireContext())
        binding.RCFragmentListJobs.adapter = adapter
        viewModel.getAllJobs(currentDay = Resoucers.getDateInMillesWithoutTime(), showOlditens = showOldItens)
            .observe(viewLifecycleOwner) { state ->
                adapter.upDateJobs(state)
            }


    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}