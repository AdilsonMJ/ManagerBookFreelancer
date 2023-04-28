package com.example.managerbookfreelancer.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.managerbookfreelancer.main.core.model.JobModelItem
import com.example.managerbookfreelancer.main.core.repository.JobsRepository
import com.example.managerbookfreelancer.main.core.useCase.GetJobsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    private val repository: JobsRepository,
    private val getJobsUseCase: GetJobsUseCase
) : ViewModel() {


    fun getAllJobs(showOlditens: Boolean): LiveData<List<JobModelItem>> {
        return getJobsUseCase.invoke(showOlditens = showOlditens).asLiveData()
    }

    suspend fun getNextEvent(): JobModelItem = getJobsUseCase.invokeNexEvent()

    fun delete(id: Long) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }

}
