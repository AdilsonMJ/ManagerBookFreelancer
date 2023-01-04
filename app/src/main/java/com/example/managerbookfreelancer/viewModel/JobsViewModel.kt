package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.*
import com.example.managerbookfreelancer.core.JobsRepository

class JobsViewModel(
    private val repository: JobsRepository
) : ViewModel() {

    private val uiState: MutableLiveData<JobsListUiState> by lazy{
        MutableLiveData<JobsListUiState>(JobsListUiState(jobsList = repository.fetchHabits()))
    }


    fun stateOnceAndStream() : LiveData<JobsListUiState> = uiState



    fun insert(jobModel: JobModel) {
        repository.addNewJob(jobModel)
        refreshUiState()
    }

    fun delet(jobModel: JobModel){
        repository.removeJob(jobModel)
        refreshUiState()
    }

    private fun refreshUiState(){
        uiState.value?.let { currentUiState ->
            uiState.value = currentUiState.copy(

            )

        }
    }

    class Factory(private val repository: JobsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return JobsViewModel(repository) as T

        }
    }

}
