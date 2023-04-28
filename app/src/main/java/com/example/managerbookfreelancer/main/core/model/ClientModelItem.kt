package com.example.managerbookfreelancer.main.core.model

import com.example.managerbookfreelancer.main.utils.Constants.Companion.IDTOEMPTYOBJECT

data class ClientModelItem (
    val idClient: Long? = IDTOEMPTYOBJECT.toLong(),
    val name: String,
    val contact: String? = null,
    val email: String
){
    override fun toString(): String {
        return name
    }
}