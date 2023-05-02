package com.example.managerbookfreelancer.loginAndRegister.core.repository

import com.example.managerbookfreelancer.loginAndRegister.core.Model.CreateUserModel
import com.example.managerbookfreelancer.loginAndRegister.core.Model.LoginModel
import com.example.managerbookfreelancer.loginAndRegister.core.UserResult

interface AuthRepository {

    suspend fun createUser(createUserModel: CreateUserModel): UserResult

    suspend fun login(loginModel: LoginModel): UserResult

}