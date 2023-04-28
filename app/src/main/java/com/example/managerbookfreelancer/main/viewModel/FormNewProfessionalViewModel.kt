package com.example.managerbookfreelancer.main.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managerbookfreelancer.main.core.entity.ClientEntity
import com.example.managerbookfreelancer.main.core.model.ClientModelItem
import com.example.managerbookfreelancer.main.core.repository.ClientRepository
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