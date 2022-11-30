package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.managerbookfreelancer.adapter.core.JobsRepository

class JobsViewModel(repository: JobsRepository) : ViewModel() {


    private val uiState: MutableLiveData<JobsListUiState> = MutableLiveData(
        JobsListUiState(jobsList = repository.fetchListJobs())
    )

    fun stateOnceAndStream() : LiveData<JobsListUiState> = uiState


    class Factory(private val repository: JobsRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return JobsViewModel(repository) as T
        }
    }

}