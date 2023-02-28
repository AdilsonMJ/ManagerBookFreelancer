package com.example.managerbookfreelancer.core.model

data class ClientModelItem (
    val idClient: Long? = -1,
    val name: String,
    val contact: String? = null,
    val email: String
){
    override fun toString(): String {
        return name
    }
}