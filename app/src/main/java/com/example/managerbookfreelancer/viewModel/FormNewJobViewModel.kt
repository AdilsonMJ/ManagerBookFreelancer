package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.*
import com.example.managerbookfreelancer.core.repository.JobsRepository
import com.example.managerbookfreelancer.core.model.JobEntity
import com.example.managerbookfreelancer.core.model.ProfessionalEntity
import com.example.managerbookfreelancer.core.repository.ProfessionalRepository
import kotlinx.coroutines.launch

class FormNewJobViewModel(
    private val repository: JobsRepository,
    private val repositoryProfessionalRepository: ProfessionalRepository
) : ViewModel(){


    val allProfessional: LiveData<List<ProfessionalEntity>> = repositoryProfessionalRepository.fetchProfessional().asLiveData()

    fun insert(jobEntity: JobEntity) {

        viewModelScope.launch {
            repository.insert(jobEntity)
        }

    }


    class Factory(private val repository: JobsRepository, private val repositoryProfessionalRepository: ProfessionalRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FormNewJobViewModel(repository, repositoryProfessionalRepository) as T
        }
    }

}