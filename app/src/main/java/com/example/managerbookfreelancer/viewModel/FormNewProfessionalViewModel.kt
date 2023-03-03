package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managerbookfreelancer.core.entity.ClientEntity
import com.example.managerbookfreelancer.core.model.ClientModelItem
import com.example.managerbookfreelancer.core.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormNewProfessionalViewModel @Inject constructor(
    private val repository: ClientRepository
) : ViewModel() {

    fun insert(clientEntity: ClientEntity) {
        viewModelScope.launch {
            repository.insert(clientEntity = clientEntity)
        }
    }

    suspend fun getClientById(cliendID: Long): ClientModelItem{
        return repository.getClientByID(cliendID)

    }

}