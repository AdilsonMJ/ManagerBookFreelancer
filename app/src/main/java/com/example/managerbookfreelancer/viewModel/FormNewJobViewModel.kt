package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.*
import com.example.managerbookfreelancer.core.entity.ClientEntity
import com.example.managerbookfreelancer.core.entity.JobEntity
import com.example.managerbookfreelancer.core.repository.ClientRepository
import com.example.managerbookfreelancer.core.repository.JobsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormNewJobViewModel @Inject constructor(
    private val repository: JobsRepository,
    repositoryClient: ClientRepository
) : ViewModel() {

    val getAllClients: LiveData<List<ClientEntity>> = repositoryClient.fetchClient().asLiveData()

    suspend fun getJobById(idJob: Long): JobEntity = repository.getJobById(idJob)


    fun insert(jobEntity: JobEntity) {

        viewModelScope.launch {
            repository.insert(jobEntity)
        }

    }
}