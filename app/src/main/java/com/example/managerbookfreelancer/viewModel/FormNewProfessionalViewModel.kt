package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.managerbookfreelancer.core.entity.ClientEntity
import com.example.managerbookfreelancer.core.repository.ClientRepository
import kotlinx.coroutines.launch

class FormNewProfessionalViewModel(
    private val repository: ClientRepository
) : ViewModel() {

    fun insert(professionalEntity: ClientEntity){
        viewModelScope.launch {
            repository.insert(professionalEntity = professionalEntity)
        }
    }

    class Factory(private val repository: ClientRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FormNewProfessionalViewModel(repository) as T
        }
    }

}