package com.example.managerbookfreelancer.model

data class OwnerJob(
    val owner_name : String,
    val cellphone : String,
    val email: String ? = null,
    val street: String? = null,
    val district: String? = null,
    val freelancerPrice: Double? = null
)