package com.example.managerbookfreelancer.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.managerbookfreelancer.main.core.entity.JobEntity
import com.example.managerbookfreelancer.main.core.model.ClientModelItem
import com.example.managerbookfreelancer.main.core.repository.ClientRepository
import com.example.managerbookfreelancer.main.core.repository.JobsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormNewJobViewModel @Inject constructor(
    private val repository: JobsRepository,
    repositoryClient: ClientRepository
) : ViewModel() {

    val getAllClients: LiveData<List<ClientModelItem>> = repositoryClient.fetchClient().asLiveData()

    suspend fun getJobById(idJob: Long): JobEntity = repository.getJobById(idJob)


    fun insert(jobEntity: JobEntity) {

        viewModelScope.launch {
            repository.insert(jobEntity)
        }

    }
}