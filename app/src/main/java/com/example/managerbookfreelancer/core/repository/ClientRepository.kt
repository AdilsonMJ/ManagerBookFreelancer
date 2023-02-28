package com.example.managerbookfreelancer.core.repository

import com.example.managerbookfreelancer.core.entity.ClientEntity

interface ClientRepository {

    suspend fun fetchClient(): List<ClientEntity>

    suspend fun insert(professionalEntity: ClientEntity)

    suspend fun delete(professionalEntity: ClientEntity)

}