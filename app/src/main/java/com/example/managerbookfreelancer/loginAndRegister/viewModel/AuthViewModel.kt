package com.example.managerbookfreelancer.loginAndRegister.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managerbookfreelancer.loginAndRegister.core.Model.CreateUserModel
import com.example.managerbookfreelancer.loginAndRegister.core.Model.LoginModel
import com.example.managerbookfreelancer.loginAndRegister.core.UserResult
import com.example.managerbookfreelancer.loginAndRegister.core.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel(){

    private val _OperationResult = MutableLiveData<UserResult?>()
    val userResult: LiveData<UserResult?> = _OperationResult


    fun createUser(createUserModel: CreateUserModel) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                authRepository.createUser(createUserModel)
            }
            _OperationResult.value = result
        }
    }

     suspend fun login(loginModel: LoginModel): UserResult {
           return authRepository.login(loginModel)
    }
}