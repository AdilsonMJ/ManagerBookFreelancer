package com.example.managerbookfreelancer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.managerbookfreelancer.adapter.AdapterListProfessional
import com.example.managerbookfreelancer.core.dataBase.JobAppDataBase
import com.example.managerbookfreelancer.core.repository.ProfessionalRepositoryImpl
import com.example.managerbookfreelancer.databinding.FragmentRecyclerViewProfessionalBinding
import com.example.managerbookfreelancer.viewModel.ProfessionalViewModel


class RecyclerViewProfessionalFragment : Fragment() {

    private var _binding: FragmentRecyclerViewProfessionalBinding? =null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterListProfessional


    private val viewModel: ProfessionalViewModel by activityViewModels(
        factoryProducer = {
            val dataBase = JobAppDataBase.getInstance(requireContext())
            ProfessionalViewModel.Factory(repository = ProfessionalRepositoryImpl(dataBase.ProfessionalDAO()))

        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = AdapterListProfessional()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecyclerViewProfessionalBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.RCFragmentListProfessional.layoutManager = LinearLayoutManager(requireContext())
        binding.RCFragmentListProfessional.adapter = adapter
        
        viewModel.allProfessional.observe(viewLifecycleOwner){state ->
            adapter.upDateProfessional(state)
        }

    }


}