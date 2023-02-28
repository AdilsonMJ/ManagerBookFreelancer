package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val repositoryClient: ClientRepository
) : ViewModel() {

    fun getAllClients(): MutableLiveData<List<ClientEntity>> {

        val resultLiveData = MutableLiveData<List<ClientEntity>>()
        viewModelScope.launch {
            resultLiveData.postValue(repositoryClient.fetchClient())
        }

        return resultLiveData
    }

    suspend fun getJobById(idJob: Long): JobEntity = repository.getJobById(idJob)


    fun insert(jobEntity: JobEntity) {

        viewModelScope.launch {
            repository.insert(jobEntity)
        }

    }
}