package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.*
import com.example.managerbookfreelancer.core.JobsRepository
import com.example.managerbookfreelancer.model.JobEntity
import kotlinx.coroutines.launch

class JobsViewModel(
    private val repository: JobsRepository
) : ViewModel() {


//    private val uiState: MutableLiveData<JobsListUiState> by lazy {
//        MutableLiveData<JobsListUiState>(JobsListUiState(repository.fetchHabits()))
//    }


//    fun stateOnceAndStream(): LiveData<JobsListUiState> = uiState

    val allJobs: LiveData<List<JobEntity>> = repository.fetchHabits().asLiveData()

    fun insert(jobEntity: JobEntity) {

        viewModelScope.launch {
            repository.insert(jobEntity)
//              refreshUiState()
        }


    }

    fun delet(jobEntity: JobEntity) {

        viewModelScope.launch {
            repository.delete(jobEntity)
//              refreshUiState()
        }
    }

//    private fun refreshUiState() {
//        uiState.value?.let { currentUiState ->
//            uiState.value = currentUiState.copy(
//                repository.fetchHabits()
//            )
//        }
//
//    }

    class Factory(private val repository: JobsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return JobsViewModel(repository) as T

        }
    }

}
