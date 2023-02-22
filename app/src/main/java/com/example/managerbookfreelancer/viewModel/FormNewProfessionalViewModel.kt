package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.managerbookfreelancer.core.entity.ProfessionalEntity
import com.example.managerbookfreelancer.core.repository.ProfessionalRepository
import kotlinx.coroutines.launch

class FormNewProfessionalViewModel(
    private val repository: ProfessionalRepository
) : ViewModel() {

    fun insert(professionalEntity: ProfessionalEntity){
        viewModelScope.launch {
            repository.insert(professionalEntity = professionalEntity)
        }
    }

    class Factory(private val repository: ProfessionalRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FormNewProfessionalViewModel(repository) as T
        }
    }

}