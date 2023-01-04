package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.LiveData
import com.example.managerbookfreelancer.model.JobModelEntity

data class JobsListUiState(
    val jobsList: LiveData<List<JobModelEntity>>
)