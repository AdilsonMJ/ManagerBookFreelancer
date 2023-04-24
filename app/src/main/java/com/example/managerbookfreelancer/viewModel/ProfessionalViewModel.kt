package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.managerbookfreelancer.core.model.ClientModelItem
import com.example.managerbookfreelancer.core.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfessionalViewModel @Inject constructor(private val repository: ClientRepository) : ViewModel(){


    fun getAllClients() : LiveData<List<ClientModelItem>> {
        return repository.fetchClient().asLiveData()
    }

    fun delete(id: Long){
        viewModelScope.launch {
            repository.delete(id)
        }
    }

}