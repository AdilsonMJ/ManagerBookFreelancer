package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managerbookfreelancer.core.model.JobModelItem
import com.example.managerbookfreelancer.core.repository.JobsRepository
import com.example.managerbookfreelancer.core.useCase.GetJobsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
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

}
