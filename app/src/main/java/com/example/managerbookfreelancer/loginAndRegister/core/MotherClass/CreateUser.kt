package com.example.managerbookfreelancer.loginAndRegister.core.MotherClass

import com.example.managerbookfreelancer.loginAndRegister.core.UserResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException


class CreateUser {
    private val auth = FirebaseAuth.getInstance()

    fun createUser(email: String, pass: String, callback: (UserResult) -> Unit){

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { register ->

                if (register.isSuccessful){
                    callback(UserResult.SUCCESS)
                } else{
                    val exception = register.exception
                    if (exception is FirebaseAuthUserCollisionException){
                        callback(UserResult.USUARIOEXIST)
                    } else {
                        callback(UserResult.ERROR)
                    }
                }

            }

    }
}