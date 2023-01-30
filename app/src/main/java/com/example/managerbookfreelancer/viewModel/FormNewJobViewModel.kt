package com.example.managerbookfreelancer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.managerbookfreelancer.core.JobsRepository
import com.example.managerbookfreelancer.core.model.JobEntity
import kotlinx.coroutines.launch

class FormNewJobViewModel(
    private val repository: JobsRepository
) : ViewModel(){

    fun insert(jobEntity: JobEntity) {

        viewModelScope.launch {
            repository.insert(jobEntity)
        }

    }


    class Factory(private val repository: JobsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FormNewJobViewModel(repository) as T
        }
    }

}