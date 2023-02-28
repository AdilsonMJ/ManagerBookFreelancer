package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.*
import com.example.managerbookfreelancer.core.entity.ClientEntity
import com.example.managerbookfreelancer.core.repository.ClientRepository
import kotlinx.coroutines.launch

class ProfessionalViewModel(private val repository: ClientRepository) : ViewModel(){


    fun getAllClients() : MutableLiveData<List<ClientEntity>> {

        val resultLiveData = MutableLiveData<List<ClientEntity>>()
        viewModelScope.launch {
            resultLiveData.postValue(repository.fetchClient())
        }

        return resultLiveData
    }


    fun delete(professionalEntity: ClientEntity){
        viewModelScope.launch {
            repository.delete(professionalEntity)
        }
    }

    class Factory(private val repository: ClientRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfessionalViewModel(repository) as T
        }
    }

}