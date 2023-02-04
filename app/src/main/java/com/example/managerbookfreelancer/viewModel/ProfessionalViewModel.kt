package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.*
import com.example.managerbookfreelancer.core.model.ProfessionalEntity
import com.example.managerbookfreelancer.core.repository.ProfessionalRepository
import kotlinx.coroutines.launch

class ProfessionalViewModel(private val repository: ProfessionalRepository) : ViewModel(){


    val allProfessional: LiveData<List<ProfessionalEntity>> = repository.fetchProfessional().asLiveData()


    fun delete(professionalEntity: ProfessionalEntity){
        viewModelScope.launch {
            repository.delete(professionalEntity)
        }
    }

    class Factory(private val repository: ProfessionalRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfessionalViewModel(repository) as T
        }
    }

}