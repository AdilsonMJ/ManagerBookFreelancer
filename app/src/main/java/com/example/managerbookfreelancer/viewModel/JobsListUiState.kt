package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.LiveData
import com.example.managerbookfreelancer.model.JobEntity

data class JobsListUiState(
    val jobsList: List<JobEntity>
)