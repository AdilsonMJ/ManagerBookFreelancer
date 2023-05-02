package com.example.managerbookfreelancer.loginAndRegister.core.MotherClass

import com.example.managerbookfreelancer.loginAndRegister.core.UserResult
import com.example.managerbookfreelancer.loginAndRegister.core.Model.LoginModel
import com.google.firebase.auth.FirebaseAuth

class AuthUser {
    private val auth = FirebaseAuth.getInstance()

    fun login(loginModel: LoginModel, callback: (UserResult) -> Unit){
        auth.signInWithEmailAndPassword(loginModel.email, loginModel.password).addOnCompleteListener{ login ->

            if (login.isSuccessful) callback(UserResult.SUCCESS) else callback(UserResult.ERROR)
        }
    }

}