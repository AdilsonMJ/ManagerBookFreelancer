package com.example.managerbookfreelancer.loginAndRegister

import com.google.firebase.auth.FirebaseAuth


class CreateUser {
    private val auth = FirebaseAuth.getInstance()

    fun createUser(email: String, pass: String, callback: (Boolean) -> Unit){

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener{register ->
                callback(register.isSuccessful)
        }.addOnFailureListener{
           callback(false)
        }

    }
}