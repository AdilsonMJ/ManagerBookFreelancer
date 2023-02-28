package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.*
import com.example.managerbookfreelancer.core.entity.ClientEntity
import com.example.managerbookfreelancer.core.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfessionalViewModel @Inject constructor(private val repository: ClientRepository) : ViewModel(){


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

}