package com.example.managerbookfreelancer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!


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

        binding.btnShowEvents.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_recyclerViewJobsFragment)
        )

        binding.btnShowProfessional.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_recyclerViewProfessionalFragment)
        )

        binding.btnNewJob.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_formNewJobFragment)
        )

        binding.btnNewProfessional.setOnClickListener(
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