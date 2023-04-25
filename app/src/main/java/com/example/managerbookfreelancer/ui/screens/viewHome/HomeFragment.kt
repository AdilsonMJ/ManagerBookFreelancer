package com.example.managerbookfreelancer.ui.screens.viewHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.core.model.JobModelItem
import com.example.managerbookfreelancer.databinding.FragmentHomeBinding
import com.example.managerbookfreelancer.utils.Constants.Companion.IDTOEMPTYOBJECT
import com.example.managerbookfreelancer.viewModel.JobsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: JobsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[JobsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // hider a action bar
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
            val nextEvent = viewModel.getNextEvent()
            upDateUi(nextEvent)
        }
        setUpNavigation()
    }

    private fun upDateUi(nextEvent: JobModelItem) = if (nextEvent.idJob == IDTOEMPTYOBJECT.toLong()) {
        binding.CardViewHomeNextJob.visibility = View.GONE
        binding.tvNextJobTitle.visibility = View.GONE
    } else {
        binding.tvHomeCardViewNextJobName.text = nextEvent.clientName
        binding.tvHomeCardViewNextJobTime.text = nextEvent.time
        binding.tvHomeCardViewNextJobDate.text = nextEvent.date
    }

    private fun setUpNavigation() {
        binding.cardViewHomeBTNShowJobs.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_recyclerViewJobsFragment)
        )

        binding.cardViewHomeBTNShowProfessional.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_recyclerViewProfessionalFragment)
        )

        binding.cardViewHomeBTNNewJob.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_formNewJobFragment)
        )

        binding.cardViewHomeBTNNewProfessional.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_newProfessional)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

        // show action bar
        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.supportActionBar?.show()
        }

    }

}