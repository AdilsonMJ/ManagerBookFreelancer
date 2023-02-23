package com.example.managerbookfreelancer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.core.dataBase.JobAppDataBase
import com.example.managerbookfreelancer.core.entity.JobEntity
import com.example.managerbookfreelancer.core.repository.JobsRepositoryImpl
import com.example.managerbookfreelancer.databinding.FragmentHomeBinding
import com.example.managerbookfreelancer.resource.Resoucers
import com.example.managerbookfreelancer.viewModel.JobsViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: JobsViewModel by activityViewModels(
        factoryProducer = {
            val database = JobAppDataBase.getInstance(requireContext())
            JobsViewModel.Factory(
                repository = JobsRepositoryImpl(database.JobDAO())
            )
        }
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        // esconde a action bar
        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.supportActionBar?.hide()
        }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch {
            val nextEvent = viewModel.getNextEvent(Resoucers.getDateInMillesWithoutTime())
            upDateUi(nextEvent)
        }

        setUpNavigation()

    }

    private fun upDateUi(nextEvent: JobEntity) = if (nextEvent == null){
        binding.CardViewHomeNextJob.visibility = View.GONE
    } else {
        binding.tvHomeNameNextJob.text = nextEvent.ownerName
        binding.tvHomeDayNextJob.text = Resoucers.fromLongToString(nextEvent.weddingDay)
        binding.tvHomeTimeNextJob.text = nextEvent.weddingTime
    }

    private fun setUpNavigation() {
        binding.cardViewBtnShowJobs.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_recyclerViewJobsFragment)
        )

        binding.cardViewBtnShowProfessional.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_recyclerViewProfessionalFragment)
        )

        binding.cardViewBtnNewJob.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_formNewJobFragment)
        )

        binding.cardViewBtnNewProfessional.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_newProfessional)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

        // mostra a action bar
        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.supportActionBar?.show()
        }

    }

}