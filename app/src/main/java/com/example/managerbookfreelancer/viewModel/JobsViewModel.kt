package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.managerbookfreelancer.core.model.JobModelItem
import com.example.managerbookfreelancer.core.repository.JobsRepository
import com.example.managerbookfreelancer.core.useCase.GetJobsUseCase
import kotlinx.coroutines.launch

class JobsViewModel(
    private val repository: JobsRepository,
    private val getJobsUseCase: GetJobsUseCase
) : ViewModel() {


    suspend fun getAllJobs(showOlditens: Boolean): List<JobModelItem> {
        return getJobsUseCase.invoke( showOlditens = showOlditens)
    }

    suspend fun getNextEvent(): JobModelItem = getJobsUseCase.invokeNexEvent()


    fun delete(id: Long) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }


    class Factory(private val repository: JobsRepository, private val getJobsUseCase: GetJobsUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return JobsViewModel(repository, getJobsUseCase) as T
        }
    }

}
