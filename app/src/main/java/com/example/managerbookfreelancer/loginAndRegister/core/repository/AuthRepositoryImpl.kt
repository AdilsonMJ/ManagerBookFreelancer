package com.example.managerbookfreelancer.loginAndRegister.core.repository

import com.example.managerbookfreelancer.loginAndRegister.core.Model.CreateUserModel
import com.example.managerbookfreelancer.loginAndRegister.core.MotherClass.CreateUser
import com.example.managerbookfreelancer.loginAndRegister.core.Model.LoginModel
import com.example.managerbookfreelancer.loginAndRegister.core.MotherClass.AuthUser
import com.example.managerbookfreelancer.loginAndRegister.core.UserResult
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class AuthRepositoryImpl @Inject constructor(
    private val createUser: CreateUser,
    private val authUser: AuthUser,
) : AuthRepository {

    override suspend fun createUser(createUserModel: CreateUserModel): UserResult {
        return suspendCoroutine { cont ->
            createUser.createUser(
                email = createUserModel.email,
                pass = createUserModel.password
            ) { result ->
                cont.resume(result)
            }

        }
    }


    override suspend fun login(loginModel: LoginModel): UserResult {

        return suspendCoroutine { cont ->
            authUser.login(loginModel) { result ->
                cont.resume(result)
            }
        }

    }
}