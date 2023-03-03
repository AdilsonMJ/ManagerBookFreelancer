package com.example.managerbookfreelancer.core.model


/**
 * representing on item in a listView
 */

data class JobModelItem(
    val idJob: Long,
    val clientName: String? = null,
    val customerEndUser: String? = null,
    val date: String,
    val time: String ? = null,
    val city: String? = null
)
