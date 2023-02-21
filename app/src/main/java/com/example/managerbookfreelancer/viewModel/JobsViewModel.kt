package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.*
import com.example.managerbookfreelancer.core.repository.JobsRepository
import com.example.managerbookfreelancer.core.model.JobEntity
import kotlinx.coroutines.launch

class JobsViewModel(
    private val repository: JobsRepository
) : ViewModel() {



    fun getAllJobs(currentDay: Long, showOlditens: Boolean): LiveData<List<JobEntity>> {
        return repository.fetchJobs(currentDay = currentDay, showOlditens = showOlditens).asLiveData()
    }


    fun delete(jobEntity: JobEntity) {

        viewModelScope.launch {
            repository.delete(jobEntity)
        }
    }


    class Factory(private val repository: JobsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return JobsViewModel(repository) as T
        }
    }

}
